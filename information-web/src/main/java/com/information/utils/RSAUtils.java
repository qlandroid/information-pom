package com.information.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.RSAPrivateKeySpec;  
import java.security.spec.RSAPublicKeySpec;  
import java.util.Enumeration;
import java.util.HashMap;  









import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;  

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RSAUtils {
	
	private static final Log log = LogFactory.getLog(RSAUtils.class);
	
	/** 
     * RSA解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 256;
    
    /** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
	/** 
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        map.put("public", publicKey);  
        map.put("private", privateKey);  
        return map;  
    }  
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 24;
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes("utf-8")));  
        }  
        return mi;  
    }
    
    /** 
     * 公钥加密ase64
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKeyBase64(String datas, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        ByteArrayOutputStream out = null;
        try {
        	byte[] data = datas.getBytes("utf-8");
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64Utils.byteArrayToBase64(encryptedData);
		} catch (Exception e) {
			log.error("公钥加密ase64异常", e);
		}finally {
			if(null != out){
				out.close();
			}
		}
		return null;
    }
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes("utf-8");  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);  
        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr),"utf-8");  
        }  
        return ming;  
    }
    
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKeyBase64(String data, RSAPrivateKey privateKey)  
			throws Exception {
		ByteArrayOutputStream out = null;
		try {
			byte[] b = Base64Utils.base64ToByteArray(data);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			out = new ByteArrayOutputStream();
			int inputLen = b.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(b, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData, "utf-8");
		} catch (Exception e) {
			log.error("私钥解密失败", e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		return null;
	}
    
    /**
     * 公钥加密
     * @param msg
     * @param certPath
     * @param certPassWord
     * @return
     */
    public static String encrypt(String msg, String certPath ,String certPassWord){
    	try{  
            KeyStore ks = KeyStore.getInstance("PKCS12");  
            FileInputStream fis = new FileInputStream(certPath);  
            //读取证书
            char[] nPassword = null;  
            if ((certPassWord == null) || certPassWord.trim().equals("")){  
                nPassword = null;  
            }else{  
                nPassword = certPassWord.toCharArray();  
            }  
            ks.load(fis, nPassword);  
            fis.close();  
  
            Enumeration<String> aliases = ks.aliases();  
            String keyAlias = null;  
            if (aliases.hasMoreElements()){// we just readin one certificate.  
                keyAlias = (String)aliases.nextElement();  
            }
           
            Certificate cert = ks.getCertificate(keyAlias);
            //证书公钥
            PublicKey pubkey = cert.getPublicKey();  
            //加密后的密文  
            String rsamsg = encryptByPublicKey(msg, (RSAPublicKey)pubkey);  
            return rsamsg;
        }catch (Exception e){  
        	log.error("rsa error:"+msg,e);
            return null;
        }  
    }
    
    /**
     * 公钥加密Base64
     * @param msg
     * @param certPath
     * @param certPassWord
     * @return
     */
    public static String encryptBase64(String msg, String certPath ,String certPassWord){
        try{  
            KeyStore ks = KeyStore.getInstance("PKCS12");  
            FileInputStream fis = new FileInputStream(certPath);  
            //读取证书
            char[] nPassword = null;  
            if ((certPassWord == null) || certPassWord.trim().equals("")){  
                nPassword = null;  
            }else{  
                nPassword = certPassWord.toCharArray();  
            }  
            ks.load(fis, nPassword);  
            fis.close();  
  
            Enumeration<String> aliases = ks.aliases();  
            String keyAlias = null;  
            if (aliases.hasMoreElements()){// we just readin one certificate.  
                keyAlias = (String)aliases.nextElement();  
            }
           
            Certificate cert = ks.getCertificate(keyAlias);
            //证书公钥
            PublicKey pubkey = cert.getPublicKey();  
            //加密后的密文  
            String rsamsg = encryptByPublicKeyBase64(msg, (RSAPublicKey)pubkey);  
            return rsamsg;
        }catch (Exception e){  
            return null;
        }  
    }
    
    /**
     * 私钥解密
     * @param msg
     * @param certPath
     * @param certPassWord
     * @return
     */
    public static String decrypt(String rsamsg, String certPath ,String certPassWord){
    	try{  
            KeyStore ks = KeyStore.getInstance("PKCS12");  
            FileInputStream fis = new FileInputStream(certPath);  
            //读取证书
            char[] nPassword = null;  
            if ((certPassWord == null) || certPassWord.trim().equals("")){  
                nPassword = null;  
            }else{  
                nPassword = certPassWord.toCharArray();  
            }  
            ks.load(fis, nPassword);  
            fis.close();  
  
            Enumeration<String> aliases = ks.aliases();  
            String keyAlias = null;  
            if (aliases.hasMoreElements()){// we just readin one certificate.  
                keyAlias = (String)aliases.nextElement();  
            }
            //证书私钥
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);  
            //解密后的明文  
            String msg = decryptByPrivateKey(rsamsg, (RSAPrivateKey)prikey);  
            return msg;
        }catch (Exception e){  
            e.printStackTrace();  
            return null;
        }  
    }
    
    /**
     * 私钥解密
     * @param msg
     * @param certPath
     * @param certPassWord
     * @return
     */
    public static String decryptBase64(String rsamsg, String certPath ,String certPassWord){
    	try{  
            KeyStore ks = KeyStore.getInstance("PKCS12");  
            FileInputStream fis = new FileInputStream(certPath);  
            //读取证书
            char[] nPassword = null;  
            if ((certPassWord == null) || certPassWord.trim().equals("")){  
                nPassword = null;  
            }else{  
                nPassword = certPassWord.toCharArray();  
            }  
            ks.load(fis, nPassword);  
            fis.close();  
  
            Enumeration<String> aliases = ks.aliases();  
            String keyAlias = null;  
            if (aliases.hasMoreElements()){// we just readin one certificate.  
                keyAlias = (String)aliases.nextElement();  
            }
            //证书私钥
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);  
            //解密后的明文  
            String msg = decryptByPrivateKeyBase64(rsamsg, (RSAPrivateKey)prikey);  
            return msg;
        }catch (Exception e){  
            e.printStackTrace();  
            return null;
        }  
    }
    
    /**
     * 发送post请求前加密map
     * @param map
     * @param key
     * @param certPath
     * @param cerPwd
     * @return
     */
    public static Map<String,String> encryptMap(Map<String,String> map,String key,String certPath,String cerPwd){
		Set set = map.entrySet();
		Iterator iterator=set.iterator();
		StringBuffer sb = new StringBuffer("");
		int i=0;
		//将Map组装成字符串
		while (iterator.hasNext()) {
		    Map.Entry mapentry = (Map.Entry)iterator.next();
		    if(i!=0){
		    	sb.append("&");
		    }
		    sb.append(mapentry.getKey()).append("=").append(mapentry.getValue());
		    i++;
		}
		String data = sb.toString();
		log.error("rsa data:"+data);
		
		//RSA加密
		String rsamsg = encrypt(data, certPath, cerPwd);
		//MD5加签
		String md5msg = Md5Encrypt.md5(rsamsg+key);
		//重组map
		map.clear();
		map.put("rsamsg", rsamsg);
		map.put("md5msg", md5msg);
		map.put("version", "2.0");
		return map;
	}
    /** 
     * ASCII码转BCD码 
     *  
     */  
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  
    public static byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /** 
     * BCD转字符串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
    /** 
     * 拆分字符串 
     */  
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分数组  
     */  
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }  
	
	public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
        HashMap<String, Object> map = RSAUtils.getKeys();  
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
          
        
        
       /* public key :MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSQ9icomTlc4vxwzsWbtCPXVJn1aqs87
        mf2bZaKmy554UKRGrJi2lVga7mr2UtI6JmuRvNMzsdiJz9O1MP02r5HYPFTb+JBFxtmDXe9e740l
        Ehb9UYzvjTHwEp4esdrxK/hB6Ey7pSN9sG4MSSnlDZdYoDveGp3Pi0eAv+KXhE4kZEwHtuoIA6Rs
        tdQES6RXWftNyhtzr15TkSPlnx/JY20ZjdAp5g+XN5anqNh3/rjGQ/ROOSkWPbYPH5Vh3lG3MEwu
        Jk0dQqHUAAdCZ/HBOezn/j7mff0DGR/ZlGNSFxKbKFVUk9hERyfmXnyRjepi3h6cRortAm1LZbPG
        rbm0AQIDAQAB

        private key :MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1JD2JyiZOVzi/HDOxZu0I9dUm
        fVqqzzuZ/ZtloqbLnnhQpEasmLaVWBruavZS0joma5G80zOx2InP07Uw/Tavkdg8VNv4kEXG2YNd
        717vjSUSFv1RjO+NMfASnh6x2vEr+EHoTLulI32wbgxJKeUNl1igO94anc+LR4C/4peETiRkTAe2
        6ggDpGy11ARLpFdZ+03KG3OvXlORI+WfH8ljbRmN0CnmD5c3lqeo2Hf+uMZD9E45KRY9tg8flWHe
        UbcwTC4mTR1CodQAB0Jn8cE57Of+PuZ9/QMZH9mUY1IXEpsoVVST2ERHJ+ZefJGN6mLeHpxGiu0C
        bUtls8atubQBAgMBAAECggEAPFv9HYL8zLIHuLbfCsgvSAMSqaavpac0RxXYyD8wsTz2/ngfDLg+
        bvlS0AtYVTS0J8VEZNVDrolnMpdrEw3tQQeqNEBapDL+7UnhmlV/Hve7WJHMi3YZBMWVJ78En2ND
        ZdFttMiM5nQzF60z7tVE/AhogcwxMFFRrSmAX3BT6MPI9BZAz6lB9yrmzVlVpZ4IcdLb9VZxVD9m
        lzfEjbZ0JU4oi45DZS5+r+iKnO2Eg4vl52jsVPv2VVwjgVkvLT/FMOyS+C3HoZbKAWLXGn1NvMsl
        eWyglmXN3uA2LH12WsvTSO4vI3UU4QaH/c+H/BVf2Tt9rTwdL9BoT0nB3zbAuQKBgQDtBRnVLhDp
        XLcCr/HE/fcWN87pYCcFL44xh2GLbHWd0dQ6xg8/LSV/+i/oRP0BRuJJSI46WkTfnKAB9e6mX5Jg
        yFia/sad2lnpmxlXuq+REFeZ+/whpPa5P9xfkCCoM/V9COVxALnuPytx52YChL/QkRkbcSMjo89k
        NzrHyGbM1wKBgQDDpaKhgKQ1OZdquTE/Gs3Xv4wBSNhiFqzofvVZRCSmhOLdloJCfB8yMoIgLcRs
        acKK6qwoqvTeG4Mn8X3WZ8KFW+c+afv2El9Fnj5OHdCjuTj/RchESOa6VOPapnWxvQNNqUFTXc0Y
        ClZtJW75P3MoviE0fKrJ3N/4fN+IOjZS5wKBgQC2jeA4trNl2fhHRJa1gFpILZHbZCiJsPgXI8u4
        pD/u30dUQWpK4Y3phRxTDJfF3P+7K/wH67DyfeMrv20hjajjjEvajXMS77Oi3sflLy+8Tlb6jf/D
        Nhil1A77N7o/c02YETsPD10lqFfg8jY6qF5I2Qj6fuhuseOxr2xBphOvnwKBgQCSXONhdt9hgggo
        qwqQbvj3IjEW9OwL1XLdK71YXoYcnOUraGFiY4DjCK4YbKS5hhUn34+TjPFLB8ICq8K8FzuKkMpa
        AuefmvbzGgL6ZYukL/jX39iGXRNe6grblBQQN5q1Cp34RAAer/0gAhUXGcSJAlPM9EbAZSCYwLJp
        M0NYmQKBgEN11t9y5wbRTePk5bpeO2bSTtXgb7OkxHOnx2dZYw1OqeYbUu+JLTWFo2ypcQ2lbn+X
        fxxDkixSJbD98X4WYG37pd0XoPkQmuR8dQYO9zigtszQgcgkyictuSzF+ydtcuqibnN+uIL6/LXp
        he+mmbR8dm9QGY7SZXKf91PzxFLT*/
        
        
//        //模  
//        String modulus = publicKey.getModulus().toString();  
//        System.out.println("modulus="+modulus);
//        //公钥指数  
//        String public_exponent = publicKey.getPublicExponent().toString();  
//        System.out.println("public_exponent="+public_exponent);
//        //私钥指数  
//        String private_exponent = privateKey.getPrivateExponent().toString(); 
//        System.out.println("private_exponent="+private_exponent);
//        
//        //使用模和指数生成公钥和私钥  
//        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);  
//        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent); 
        
        //明文  
        String ming = "sign=d5efc1d3b6e9cf4a76343b7abab911a5&partner_id=1002201505273140&data=<?xml version=\"1.0\" encoding=\"UTF-8\"?><pay_interaction><request><head><merdatetime>2017-02-22 10:36:53</merdatetime><servicename>fund_multi_trade</servicename><ver>1.0</ver></head><param><account>test03@ceshi.com</account><out_multi_trade_no>ZFQ04096170222000001</out_multi_trade_no><out_multi_trade_type></out_multi_trade_type><subject>货款支付,合同号码：HT1702220005,品名：电镀锌耐指纹板卷,数量：9.457,单位：吨</subject><atomic>0</atomic><close_time></close_time><show_url>http://www.csesteel.com/exchange/b-0-s/contract.do</show_url><server_return_url>http://www.csesteel.com/exchange/zgytCallbackService.do</server_return_url><page_return_url>http://www.csesteel.com/exchange/b-0-s/contract.do</page_return_url><order_list><order><trade_order>1</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><out_trade_type></out_trade_type><service_name>fund_pay_by_direct</service_name><account>test03@ceshi.com</account><buyer_account>test03@ceshi.com</buyer_account><seller_account>zl@cs.sh.cn</seller_account><seller_name>中钢银通电子商务股份有限公司</seller_name><amount>56080.01</amount><comment>货款支付,合同号码：HT1702220005,品名：电镀锌耐指纹板卷,数量：9.457,单位：吨</comment></order><order><trade_order>2</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><out_trade_type></out_trade_type><service_name>fund_pay_by_direct</service_name><account>zl@cs.sh.cn</account><buyer_account>zl@cs.sh.cn</buyer_account><seller_account>zhegnsifu@cs.sh.cn</seller_account><seller_name>上海中钢投资集团有限公司</seller_name><amount>56080.01</amount><comment>寄售合同支付,合同号码：JS1702220005,品名：电镀锌耐指纹板卷,数量：9.457,单位：吨</comment></order><order><trade_order>3</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><out_trade_type></out_trade_type><service_name>fund_freeze_for_trade</service_name><account>zhegnsifu@cs.sh.cn</account><buyer_account>zhegnsifu@cs.sh.cn</buyer_account><seller_account>zl@cs.sh.cn</seller_account><seller_name>中钢银通电子商务股份有限公司</seller_name><amount>56080.01</amount><comment>寄售合同冻结资金,合同号码:JS1702220005,品名：电镀锌耐指纹板卷,数量：9.457,单位：吨</comment></order></order_list></param></request><response><head><serverdatetime>2017-02-22 12:24:44</serverdatetime><ver>1.0</ver></head><result><return_code>00000</return_code><return_msg>成功</return_msg><out_trade_no>ZFQ04096170222000001</out_trade_no><apply_date>20170222122255</apply_date><pay_order_no>2017022201000065022</pay_order_no><process_date>20170222122444</process_date><order_list><order><trade_order>1</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><pay_order_no>2017022201000065023</pay_order_no><return_code>00000</return_code><return_msg>成功</return_msg></order><order><trade_order>2</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><pay_order_no>2017022201000065024</pay_order_no><return_code>00000</return_code><return_msg>成功</return_msg></order><order><trade_order>3</trade_order><out_trade_no>ZFQ04096170222000001</out_trade_no><pay_order_no>2017022208000021253</pay_order_no><return_code>00000</return_code><return_msg>成功</return_msg></order></order_list></result></response></pay_interaction>&service_name=fund_multi_trade"; 
        //加密后的密文  
        String mi = RSAUtils.encrypt(ming, "C:\\Users\\Delta\\Desktop\\ceshi.pfx", "12345678");  
        System.err.println(mi);  
        //解密后的明文  
        ming = RSAUtils.decrypt(mi, "C:\\Users\\Delta\\Desktop\\ceshi.pfx","12345678");  
        System.err.println(ming);  
    }  
	
}
