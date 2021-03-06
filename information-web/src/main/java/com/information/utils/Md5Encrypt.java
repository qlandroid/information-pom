package com.information.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encrypt {

/** 
* 功能：MD5加密处理核心文件，不需要修改
* 版本：3.1
* 修改日期：2010-11-01
* 说明：
* 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
* 该代码仅供学习和研究支付宝接口使用，只是提供一个
* */
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f'   };

    /**
     * 对字符串进行MD5加密
     * 
     * @param text
     *            明文
     * 
     * @return 密文
     */
    public static String md5(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {

            throw new IllegalStateException("System doesn't support your  EncodingException.");

        }

        byte[] bytes = msgDigest.digest();

        String md5Str = new String(encodeHex(bytes));

        return md5Str;
    }

    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }
    
    public static void main(String[] args) {
		String key = "ZD7D0ZM7SC8CVV6Q";
		String msg = "96520C3A6D9C33539152864A97D1D3EAEE3B09A14B216A342D7ED50971128EC3758D46F14B3BC872DCB532C93CE3D7AE6B15760B45BD40E562978AB570D87CD0E6C9DC56D127A0B4656E9EB80DD5231E80DA1CDCEBB43CD892323D7B5D86829540C64137398E47F45F64C322FE05AF64D99DA3824B1A9AD938C584946DBA369AAF3DE97B64F883AF3CAB278F71004584AE70DFB8F8F4D8EF8D099D684DF74E0D726499D02DA24A8C84CCBE6DB3620E41B090ED1738386C1A939E33C35E96B7E9E35270560D34F14133E499EABF7468E00EABD3722E76C3B4D27FCFD737150C51357AEBB89551C3A86833C73638D81E5B4F70C3E28507DBA08A0075DB106A57772D0594F7F364D4E56470222D7E61F38A731105443BFA81E3AF53D53B8F85F434B53941A2E30C8AE8D8228BD10605D72686E17040E0BBCB533A12EC7B6D7F0CABF04100573100091E7D1FCAA2637C711504CFE179D4CC1EB213AB89C36B0F4E8A5F35856156233DDA463CDF43DA344D72112A63C0E8210400F52693AB85329DC94860B81F3F84FEF79D0A5C8B5BBE9F9E10723339B0411E6A01740988C6B798711A312B300055DC498FFB01DB0F643ABE7008920CB9E030F053556AC17DCDBADFD68F47184D524FAFBB1A09EEB5BD787B91C1443B01E074CFE3D3BA87DBB829354F33DF11813E354F8E7F005659C97FA6572D6F1E7E2CAE02AE757672364CA98126B7DC3496DC269BFFF7C25E588E5A1AB61B608CF2CEF669A0A0B349FEF894FDD6C30B78FA3ECA04E7D3899C0AF6ADDF842FB711B4BDEBC1EBFE1ED599C9A244E2F0B710FC278AEABC2EFCFCBBB9FA0ECCAD1770C0C06CA491176D550E328ACB5084979C6849E028465D32985462959218709C81F6B81D9206AF17A9EA92A034EA84A891A8FC3ABA7171B4116294D358165F2EF9F7087A3F0E6A2EDE6AF47F372D5059D8E643E095F1A15706DCD36D2BC7F123F2EB05945EC27CFFBD8FE6F963E1CEADE05B98D7A2EBF4309A6023FDD08C3A2791010AECFF797268FB0942CCD975763E92ED4FF2C780DDE2A5719950A3B1B95A4B15FA2866652231865254F8E91ABEA8F175813ACDF02C06D33DA52B9A37F55734CC537B8B0DF8A33CD33969F4DB42F24B38782A9422BD92D2BD2949FA5ECB805BB6B78ADD6A13669A2D36E214FF39033A35F711C31C003ACC0EAF590C9B9C7944818C17ACF3DADDC326F087C1186EA8D1BC975D2127A86EA666D297028A8AF29A9BC0FCD30BF6B055448AA71F3B648B85013AA77ACD24E59DC6F136D3B981E325D596DAFCF38D71C586162CAA6CF6E5C2666C8A496F44439F5AD4DAC50517926DF3355988A6AACAFE30A10DEA5B1BA01C076B3ED2B06FFA117F4E9A0F1BA81BB1F3751AD814CDD5AAEB5BF655179BBB13403801052CF5F1D624C277B0B2E2D47EA52CC6362F581973397329246C273B18692AAEF94D0C5F5807F726E44417DE09F47005C4E0035D9127385A238779A6E3348B9C8AEEDB434D68CE29EAF4F00EF31B0C729793138EB13F2DC01D0BB1319C5345576BC7A39CE92C535C01B6CE40C5DAADB91EB79C0C66DB40C53DD5066AE237C42CB9370D301F7124CA87BAFE6FBC602468FCB6BE5DD8AEB181ABFF83565A7A6ADB04632B553C6469DA0794E51646D72C481520ABC288B8FFC5099C80D0E38C8F27C479B80AB9128B1220E2E55E8152B0C522DD920467962280FDAF5ED426EC23956F48C92CBF414EA9601AB035D91B525C2DAD7B629A98F88EC7AC4982693FEA602D8264BC6DF4E6FFC4F04AD8C58027F1EA29E39CE3C8017D6A";
		String result = md5(msg+key);
		System.out.println(result);
	}

}
