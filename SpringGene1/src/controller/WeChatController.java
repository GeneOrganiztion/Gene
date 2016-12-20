package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Constant;
import utils.DateUtil;

import com.github.pagehelper.PageInfo;

import wepay.utils.GetWxOrderno;
import wepay.utils.RequestHandler;
import utils.Sha1Util;
import wepay.utils.HttpXmlClient;
import wepay.utils.TenpayUtil;
import wepay.utils.HttpConnect;
import wepay.utils.HttpResponse;

@Controller
@RequestMapping("/weixin")
public class WeChatController {
	private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);
	@RequestMapping("/oauth")
	public void oauthuser(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		//共账号及商户相关参数
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String partner =Constant.PARTNER;
		String partnerkey = Constant.PARTNERKEY;
		
		String backUri = Constant.WEBACKURI;
		System.out.println(backUri);
		//授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
		//最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
		//比如 Sign = %3D%2F%CS% 
		backUri = backUri+"?userId=b88001&orderNo=1499900164812&describe=西瓜&money=1780.00";
		//URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = URLEncoder.encode(backUri);
		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=" + appid+
				"&redirect_uri=" +
				 backUri+
				"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		response.sendRedirect(url);
		
	}
	

	@RequestMapping("/weixinUnion")
	@ResponseBody
	public Map<String, Object> weixinUnion(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		String code = request.getParameter("code");
//商户相关资料 
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String OPENID ="";
		String ACCESS_TOKE="";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
		String tempValue="";
		if( temp == null){
				response.sendRedirect("/SpringGene1/error.jsp");
				logger.info("temp==null");
		}else
		{
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject  jsonObj = JSONObject.fromObject(tempValue);
			if(jsonObj.containsKey("errcode")){
				logger.info("code huoqu errcode=");
				System.out.println(tempValue);
				response.sendRedirect("/SpringGene1/error.jsp");
			}
			OPENID = jsonObj.getString("openid");
			ACCESS_TOKE=jsonObj.getString("access_token");
			logger.info("openId="+OPENID);
			logger.info("ACCESS_TOKE="+ACCESS_TOKE);
		}
		
		String UserinfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKE+"&openid="+OPENID+"&lang=zh_CN";
		HttpResponse userinfo = HttpConnect.getInstance().doGetStr(UserinfoURL);
		String userValue=null;
		if(userinfo == null){
			response.sendRedirect("/SpringGene1/error.jsp");
			logger.info("userinfo==null");
		}else{
			try {
				userValue = userinfo.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject  jsonObj = JSONObject.fromObject(userValue);
			if(jsonObj.containsKey("errcode")){
				logger.info("errcode=");
				System.out.println(userValue);
				response.sendRedirect("/SpringGene1/error.jsp");
			}
			dataMap.put("openid", jsonObj.getString("openid"));
			dataMap.put("nickname", jsonObj.getString("nickname"));
			dataMap.put("sex", jsonObj.getString("sex"));
			logger.info("dataMap sex="+dataMap.get("sex"));
			logger.info("dataMap nickname="+dataMap.get("nickname"));
			dataMap.put("province", jsonObj.getString("province"));
			logger.info("dataMap province="+dataMap.get("province"));
			dataMap.put("city", jsonObj.getString("city"));
			dataMap.put("headimgurl", jsonObj.getString("headimgurl"));
			logger.info("dataMap headimgurl="+dataMap.get("headimgurl"));
		}
		
		return dataMap;
	}
	
	
	
	
	@RequestMapping("/topay")
	public void weixinpay(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		//网页授权后获取传递的参数
		String userId = request.getParameter("userId"); 	
		String orderNo = request.getParameter("orderNo"); 	
		String money = request.getParameter("money");
		String code = request.getParameter("code");
		//金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		
//商户相关资料 
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String partner = Constant.PARTNER;
		String partnerkey = Constant.PARTNERKEY;

		
		String openId ="";
		String access_token="";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
		String tempValue="";
		if( temp == null){
				response.sendRedirect("/SpringGene1/error.jsp");
				logger.info("temp==null");
		}else
		{
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject  jsonObj = JSONObject.fromObject(tempValue);
			if(jsonObj.containsKey("errcode")){
				logger.info("errcode=");
				System.out.println(tempValue);
				response.sendRedirect("/SpringGene1/error.jsp");
			}
			openId = jsonObj.getString("openid");
			
			logger.info("openId="+openId);
		}
		
		
		//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				String currTime = TenpayUtil.getCurrTime();
				//8位日期
				String strTime = currTime.substring(8, currTime.length());
				//四位随机数
				String strRandom = TenpayUtil.buildRandom(4) + "";
				//10位序列号,可以自行调整。
				String strReq = strTime + strRandom;
				
				
				//商户号
				String mch_id = partner;
				//子商户号  非必输
//				String sub_mch_id="";
				//设备号   非必输
				String device_info="";
				//随机数 
				String nonce_str = strReq;
				//商品描述
//				String body = describe;
				
//商品描述根据情况修改
				String body = "基因商城测试";
				//附加数据
				String attach = DateUtil.format(new Date())+"1";
				logger.info("attach="+attach);
				//订单日期起止时间
				 Date d=new Date();  
				 String time_start = DateUtil.format(d);
		    	 String split_time_start=time_start.substring(0,14);
		    	 logger.info("split_time_start="+split_time_start);
		    	 
		    	 String time_expire = DateUtil.format(new Date(d.getTime() + (long)3 * 24 * 60 * 60 * 1000));
		    	 String split_time_expire=time_expire.substring(0,14);
		    	 logger.info("split_time_expire="+split_time_expire);
				
				//商户订单号
				String out_trade_no = DateUtil.format(d);
				int intMoney = Integer.parseInt(finalmoney);
				logger.info("out_trade_no="+out_trade_no);
				//总金额以分为单位，不带小数点
				int total_fee = intMoney;
				//订单生成的机器 IP
				String spbill_create_ip = request.getRemoteAddr();
				//订 单 生 成 时 间   非必输
//				String time_start ="";
				//订单失效时间      非必输
//				String time_expire = "";
				//商品标记   非必输
//				String goods_tag = "";
				
				//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				String notify_url ="http://192.168.1.111:8082/testPay/aa.htm";
				
				
				String trade_type = "JSAPI";
				String openid = openId;
				//非必输
//				String product_id = "";
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);  
				packageParams.put("mch_id", mch_id);  
				packageParams.put("nonce_str", nonce_str);  
				packageParams.put("body", body);  
				packageParams.put("attach", attach);  
				packageParams.put("out_trade_no", out_trade_no);  
				
				
				//这里写的金额为1 分到时修改
				packageParams.put("total_fee", "1");  
//				packageParams.put("total_fee", "finalmoney");  
				packageParams.put("spbill_create_ip", spbill_create_ip);  
				packageParams.put("notify_url", notify_url); 
				packageParams.put("time_start", split_time_start);  
				packageParams.put("time_expire", split_time_expire); 
				
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
						"<time_start>"+split_time_start+"</time_start>"+
						"<time_expire>"+split_time_expire+"</time_expire>"+
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
				
				response.sendRedirect("/SpringGene1/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
		
	}
	@RequestMapping("/topaytest")
	@ResponseBody
	public HashMap<String,String> topaytest(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		HashMap<String,String> hashMap=new HashMap<String,String>();
		hashMap.put("appid", Constant.APPID);
		hashMap.put("timeStamp", "1231231241113123");
		hashMap.put("nonceStr","131312412412245");
		hashMap.put("package","adasd123123123123");
		hashMap.put("sign", "asdasdasd21312");
		String out_trade_no = DateUtil.format(new Date());
		hashMap.put("orderid", out_trade_no);
		return hashMap;
	}
	
	@RequestMapping("/ordertopay")
	public void ordertopay(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{

				String appid2 = Constant.APPID;
				String currTime = TenpayUtil.getCurrTime();
				RequestHandler reqHandler = new RequestHandler(request, response);
				reqHandler.init(appid2, Constant.APPSECRET, Constant.PARTNERKEY);		
				SortedMap<String, String> finalpackage = new TreeMap<String, String>();		
				String timestamp = "1481960236";
				String nonceStr2 = "1537164492";
				String prepay_id2 = "prepay_id="+"wx20161217153716ed37a1ce500970530591";
				String packages = prepay_id2;
				finalpackage.put("appId", appid2);  
				finalpackage.put("timeStamp", timestamp);  
				finalpackage.put("nonceStr", nonceStr2);  
				finalpackage.put("package", packages);  
				finalpackage.put("signType", "MD5");
				String finalsign ="D237058958F6BD1716A60A413CED26F5";
				logger.info("最后阶段finalsign="+finalsign);
				response.sendRedirect("/SpringGene1/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
		
	}
	@RequestMapping("/address")
	public void address(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
	        
		String code = request.getParameter("code");
		//商户相关资料 
				String appid = Constant.APPID;
				String appsecret = Constant.APPSECRET;
				String ACCESS_TOKE="";
				String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
				HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
				String tempValue="";
				if(temp == null){
						response.sendRedirect("/SpringGene1/error.jsp");
						logger.info("temp==null");
				}else
				{
					try {
						tempValue = temp.getStringResult();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JSONObject  jsonObj = JSONObject.fromObject(tempValue);
					if(jsonObj.containsKey("errcode")){
						logger.info("code huoqu errcode=");
						System.out.println(tempValue);
						response.sendRedirect("/SpringGene1/error.jsp");
					}
					ACCESS_TOKE=jsonObj.getString("access_token");
					logger.info("ACCESS_TOKE="+ACCESS_TOKE);
				}
		
		
	        String currTime = TenpayUtil.getCurrTime();
			//8位日期
			String strTime = currTime.substring(8, currTime.length());
			//四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
			//10位序列号,可以自行调整。
			String strReq = strTime + strRandom;
			String path = request.getContextPath();
		        
			//以为我配置的菜单是http://yo.bbdfun.com/first_maven_project/，最后是有"/"的，所以url也加上了"/"
		    String url = request.getScheme() + "://" + request.getServerName() +  path + "/address.jsp/";  
		    logger.info("url="+url);
	        String noncestr = strReq;        
	        String timestamp = Sha1Util.getTimeStamp();
	    /*	SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", appid);  
			packageParams.put("url", url);
			packageParams.put("timestamp", timestamp); 
			packageParams.put("nonceStr", noncestr);  
			packageParams.put("accessToken", ACCESS_TOKE);  */
	      /*  RequestHandler reqHandler = new RequestHandler(request, response);
			String addrSign = reqHandler.createSign(packageParams);*/
			//String addrSign =Sha1Util.createSHA1Sign(packageParams);
	        String str = "appid=" + appid +
	                "&noncestr=" + noncestr +
	                "&timestamp=" + timestamp +
	                "&url=" + url;
			String addrSign = HttpXmlClient.SHA1(str);
			logger.info("addrSign="+addrSign);
	        System.out.println("noncestr=" + noncestr);
	        logger.info("noncestr="+noncestr);
	        System.out.println("timestamp=" + timestamp);
	        logger.info("timestamp="+timestamp);
	        System.out.println("addrSign=" + addrSign);
	        logger.info("addrSign="+addrSign);
	        response.sendRedirect("/SpringGene1/address.jsp?appid="+appid+"&timeStamp="+timestamp+"&nonceStr="+noncestr+"&addrSign="+addrSign);
		
	}
	
	@RequestMapping("/addresstest")
	public void addresstest(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		String ACCESS_TOKE="";
		String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constant.APPID+"&secret="+Constant.APPSECRET;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
		String tempValue="";
		if( temp == null){
				response.sendRedirect("/SpringGene1/error.jsp");
				logger.info("temp==null");
		}else
		{
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject  jsonObj = JSONObject.fromObject(tempValue);
			if(jsonObj.containsKey("errcode")){
				logger.info("accesstoken huoqu errcode=");
				System.out.println(tempValue);
				response.sendRedirect("/SpringGene1/error.jsp");
			}

			 ACCESS_TOKE=jsonObj.getString("access_token");
			logger.info("ACCESS_TOKE="+ACCESS_TOKE);
		}
		
        //获取ticket
		String JsApiTicket="";
		String URLTICK = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+ACCESS_TOKE;
		HttpResponse temptick = HttpConnect.getInstance().doGetStr(URLTICK);		
		String tempValue1="";
		if( temptick == null){
				response.sendRedirect("/SpringGene1/error.jsp");
				logger.info("temp==null");
		}else
		{
			try {
				tempValue1 = temptick.getStringResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject  jsonObj1 = JSONObject.fromObject(tempValue1);
			if(jsonObj1.containsKey("errcode")){
				logger.info("tick huoqu errcode");
				System.out.println(tempValue1);
				response.sendRedirect("/SpringGene1/error.jsp");
			}

			JsApiTicket=jsonObj1.getString("ticket");
			logger.info("JsApiTicket="+JsApiTicket);
		}

		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
        String noncestr = strReq;
        String timestamp =Sha1Util.getTimeStamp();
        //获取请求url
        String path = request.getContextPath();
        //以为我配置的菜单是http://yo.bbdfun.com/first_maven_project/，最后是有"/"的，所以url也加上了"/"
        String url = request.getScheme() + "://" + request.getServerName() +  path + "/";  
        String str = "jsapi_ticket=" + JsApiTicket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = HttpXmlClient.SHA1(str);
        logger.info("signature="+signature);
        response.sendRedirect("/SpringGene1/address.jsp?appid="+Constant.APPID+"&timeStamp="+timestamp+"&nonceStr="+noncestr+"&signature="+signature); 
	
	
	}

}
