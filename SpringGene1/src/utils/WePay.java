package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.WeChatController;
import wepay.utils.GetWxOrderno;
import wepay.utils.RequestHandler;
import wepay.utils.TenpayUtil;

public class WePay {
	private static final Logger logger = LoggerFactory.getLogger(WePay.class);
	
	public static Map<String,String> toPay(String openId,int finalmoney,String productName,HttpServletRequest request,
			HttpServletResponse response){
		String appsecret = Constant.APPSECRET;
		String partnerkey = Constant.PARTNERKEY;
		String appid = Constant.APPID;
		String partner = Constant.PARTNER;
		String mch_id = partner;
		String body = productName;
		//商户订单号
		String out_trade_no=null;
		//附加数据
		String attach = DateUtil.format(new Date())+"1";
		Date d=new Date();  
    	out_trade_no= DateUtil.format(d);                                                                                                                                                                     
		int intMoney = finalmoney;
		logger.info("out_trade_no="+out_trade_no);
		//总金额以分为单位，不带小数点
		int total_fee = intMoney;
		//订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		//订 单 生 成 时 间   非必输
//		String time_start ="";
		//订单失效时间      非必输
//		String time_expire = "";
		//商品标记   非必输
//		String goods_tag = "";
		
		//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url ="http://nbuxinxiren.cn/SpringGene1/weixin/wexin.do";
		//随机数 
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;
		String trade_type = "JSAPI";
		String openid = openId;
		//非必输
//		String product_id = "";
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);  
		packageParams.put("mch_id", mch_id);  
		packageParams.put("nonce_str", nonce_str);  
		packageParams.put("body", body);  
		packageParams.put("attach", attach);  
		packageParams.put("out_trade_no", out_trade_no);  
		//这里写的金额为1 分到时修改
		packageParams.put("total_fee", "1");  
		/*packageParams.put("total_fee", "finalmoney");  */
		packageParams.put("spbill_create_ip", spbill_create_ip);  
		packageParams.put("notify_url", notify_url); 
		packageParams.put("trade_type", trade_type);  
		packageParams.put("openid", openid);  
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);
		
		String sign = reqHandler.createSign(packageParams);
		String xml="<xml>"+
				"<appid>"+appid+"</appid>"+
				"<mch_id>"+mch_id+"</mch_id>"+
				"<nonce_str>"+nonce_str+"</nonce_str>"+
				"<sign>"+sign+"</sign>"+
				"<body><![CDATA["+body+"]]></body>"+
				"<attach>"+attach+"</attach>"+
				"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
				"<total_fee>"+1+"</total_fee>"+
				"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
				"<notify_url>"+notify_url+"</notify_url>"+
				"<trade_type>"+trade_type+"</trade_type>"+
				"<openid>"+openid+"</openid>"+
				"</xml>";
		System.out.println(xml);
		String allParameters = "";
		try {
			allParameters =  reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String, Object> dataMap2 = new HashMap<String, Object>();
		String prepay_id="";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			logger.info("prepay_id="+prepay_id);
			if(prepay_id.equals("")){
				request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
				logger.info("prepay_id=null");
				response.sendRedirect("/SpringGene1/error.jsp");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		Map<String,String> map=new HashMap<String,String>();
		String appid2 = appid;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id="+prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);  
		logger.info("appId="+appid2);
		finalpackage.put("timeStamp", timestamp); 
		logger.info("timestamp="+timestamp);
		finalpackage.put("nonceStr", nonceStr2); 
		logger.info("nonceStr="+nonceStr2);
		finalpackage.put("package", packages);  
		logger.info("packages="+packages);
		finalpackage.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalpackage);
		logger.info("finalsign="+finalsign);
		map.put("appId", appid2);
		map.put("signType", "MD5");
		map.put("timeStamp", timestamp);
		map.put("nonceStr", nonceStr2);
		map.put("package", packages);
		map.put("paySign", finalsign);
		return map;
	}
}
