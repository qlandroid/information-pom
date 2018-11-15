package com.information.pay.wxpay;


public class WxpayConfig {

	//以下相关参数需要根据自己实际情况进行配置

	public static String APP_ID = "";// appid
	public static String APP_SECRET = "";// appsecret
	public static String MCH_ID = "";// 你的商业号
	public static String API_KEY = "";// API key
	public static String NOTIFY_URL = "";//回调地址,必须为外网可以访问的地址

	public static String CREATE_IP = "8.8.8.8";// IP
	public static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单接口
}
