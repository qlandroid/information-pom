package com.information.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String sendJsonPost(String url,String json){
        int httpTimeout = 3000;

        CloseableHttpClient httpClient = createHttpClient();

        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpTimeout)
                .setConnectTimeout(httpTimeout).setSocketTimeout(httpTimeout).build();
        post.setConfig(requestConfig);

        post.addHeader(new BasicHeader("Content-Type","application/json;charset=UTF-8"));

        try {

            post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
            CloseableHttpResponse resp = httpClient.execute(post);
            try {
                HttpEntity entity = resp.getEntity();
                String response = EntityUtils.toString(entity, "UTF-8");
                return response;
            }finally {
                resp.close();
            }
        } catch (Exception e) {
            logger.error("send json post error:",e);
        }
        return "";
    }
    
    public static String sendParamsPost(String url, Map<String, String> map){
        int httpTimeout = 3000;

        CloseableHttpClient httpClient = createHttpClient();

        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpTimeout)
                .setConnectTimeout(httpTimeout).setSocketTimeout(httpTimeout).build();
        post.setConfig(requestConfig);
        try {
        	List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
            Set<String> keys = map.keySet();
            for(String key : keys){
                formparams.add(new BasicNameValuePair(key, map.get(key)));  
            }
            post.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));
            CloseableHttpResponse resp = httpClient.execute(post);
            try {
                HttpEntity entity = resp.getEntity();
                String response = EntityUtils.toString(entity, "UTF-8");
                return response;
            }finally {
                resp.close();
            }
        } catch (Exception e) {
            logger.error("send post error:",e);
        }
        return "";
    }

    
    public static CloseableHttpClient createHttpClient(){
        SSLContext ctx = null;
        try {
            File directory = new File("");// 参数为空
            String courseFile = directory.getCanonicalPath();
            ctx = custom(courseFile+"/src/main/resources/cacerts-chinasteel","changeit");
        } catch (Exception e) {
            logger.error("SSLCustomRegistryFactoryBean create error:",e);
        }
        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(ctx,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslConnectionFactory).build();

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionFactory).build();
        return httpClient;
    }

    private static SSLContext custom(String keyStorePath, String keyStorepass)throws Exception{
        logger.info("SSLContext init begin:"+keyStorePath);
        SSLContext sc = null;
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            logger.info("SSLContext init end:"+sc.toString());
        } catch (Exception e) {
            logger.error("SSLContext init error:",e);
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        return sc;
    }
    
    public static Map<String, String> preDealRequestParameters(
            HttpServletRequest request) {
        Map<String, String> paraMap = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter
                .hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            try {
                paraMap.put(name, URLDecoder.decode(valueStr, "UTF-8"));
            }
            catch (Exception exp) {
                logger.error(
                        "getRequestParametersWithURIDecode failed with URLDecoder.decode failed , ",
                        exp);
            }
        }
        return paraMap;
    }
}
