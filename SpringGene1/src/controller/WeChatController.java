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

import org.liufeng.course.service.CoreService;
import org.liufeng.course.util.SignUtil;
import org.liufeng.course.util.WeChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Cart;
import po.User;
import service.AdminService;
import service.CartService;
import service.UserService;
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
	
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	
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
		backUri = backUri;
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
	public void weixinUnion(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		String code = request.getParameter("code");
//商户相关资料 
		String appid = Constant.APPID;
		String appsecret = Constant.APPSECRET;
		String OPENID ="";
		String ACCESS_TOKE="";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
		/*Map<String, Object> dataMap = new HashMap<String, Object>();*/
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
		String openid=null;
		String userid=null;
		try{
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
				openid=jsonObj.getString("openid");
				String nickname=jsonObj.getString("nickname");
				String sex=jsonObj.getString("sex");
				boolean isman=true;
				if("1".equals(sex)){
					
				}else{
					isman=false;
				}
				String province=jsonObj.getString("province");
				String city=jsonObj.getString("city");
				String headimgurl=jsonObj.getString("headimgurl");
				User user =new User();
				user.setOpenid(openid);
				User own=(User)userService.select(user);
				if(null==own){
					user.setOpenid(openid);
					user.setCity(city);
					user.setProvince(province);
					user.setHeadImgurl(headimgurl);
					user.setNickname(nickname);
					user.setSex(isman);
					int uid=userService.insertUser(user);
					userid=String.valueOf(uid);
					Cart cart=new Cart();
					cart.setUserId(uid);
					cartService.insertCart(cart);
				}else{
					userid=String.valueOf(own.getId());
					user.setId(own.getId());
					user.setCity(city);
					user.setHeadImgurl(headimgurl);
					user.setNickname(nickname);
					user.setSex(isman);
					userService.updateUser(user);
				}		
			}
			
		}catch(Exception e){
			
			logger.info("weixinUnion errcode="+e);
		}
		
		logger.info("userid="+userid);
		logger.info("openid="+openid);
		
		response.sendRedirect("/DNAjiankang/index.html#/home/"+userid+"/"+openid);
		
		//response.sendRedirect("/SpringGene1/test.jsp?dataMap="+openid);
	}
	
	
	
	
	@RequestMapping("/topay")
	@ResponseBody
	public Map<String,String> weixinpay(HttpServletRequest request,
			HttpServletResponse response) throws Exception,IOException{
		//网页授权后获取传递的参数
		String userId = request.getParameter("openId"); 	
		String finalmoney = request.getParameter("finalmoney"); 
		String orderId = request.getParameter("orderId"); 
		logger.info("userId="+userId);
		logger.info("finalmoney="+finalmoney);
//商户相关资料 
		String appsecret = Constant.APPSECRET;
		String partnerkey = Constant.PARTNERKEY;
		String appid = Constant.APPID;
		String partner = Constant.PARTNER;
		
		//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				//商户号
				String mch_id = partner;
				//子商户号  非必输
//				String sub_mch_id="";
				/*//设备号   非必输
				String device_info="";*/
				
				//商品描述
//				String body = describe;
				
//商品描述根据情况修改
				String body = "基因商城测试";
				//商户订单号
				String out_trade_no=null;
				//附加数据
				String attach = DateUtil.format(new Date())+"1";
				logger.info("attach="+attach);
				//订单日期起止时间
				/*if(null==orderId){*/
				 Date d=new Date();  
				/* String time_start = DateUtil.format(d);
		    	 String split_time_start=time_start.substring(0,14);
		    	 logger.info("split_time_start="+split_time_start);
		    	 
		    	 String time_expire = DateUtil.format(new Date(d.getTime() + (long)3 * 24 * 60 * 60 * 1000));
		    	 String split_time_expire=time_expire.substring(0,14);
		    	 logger.info("split_time_expire="+split_time_expire);*/
		    	 out_trade_no= DateUtil.format(d);
		    	 
		    	 
				//}else{
					
					/*out_trade_no=orderId;*/
					
				//}
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
				String openid = userId;
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
				
				/*response.sendRedirect("/SpringGene1/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);*/
		
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
	@ResponseBody
	public  HashMap<String,String> addresstest(HttpServletRequest request,
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
				logger.info("accesstoken huoqu errcode="+jsonObj.getString("errcode"));
				System.out.println(tempValue);
				//response.sendRedirect("/SpringGene1/error.jsp");
			}

			 ACCESS_TOKE=jsonObj.getString("access_token");
			logger.info("ACCESS_TOKE="+ACCESS_TOKE);
		}
        //获取ticket
		String JsApiTicket="";
		String URLTICK = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+ACCESS_TOKE+"&type=jsapi";
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
				logger.info("tick huoqu errmsg="+jsonObj1.getString("errmsg"));
				//response.sendRedirect("/SpringGene1/error.jsp");
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
        String url="http://nbuxinxiren.cn/DNAjiankang/index.html#/orderPay/";
        String str = "jsapi_ticket=" + JsApiTicket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        HashMap<String,String> hashMap=new HashMap<String,String>();
        String signature = HttpXmlClient.SHA1(str);
        hashMap.put("signature", signature);
        hashMap.put("appid", Constant.APPID);
        hashMap.put("timestamp", timestamp);
        hashMap.put("noncestr", noncestr);
        logger.info("signature="+signature);
        logger.info("appid="+Constant.APPID);
        logger.info("timestamp="+timestamp);
        logger.info("noncestr="+noncestr);
        return hashMap;
	
	
	}
	  @RequestMapping(value={"/wexin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ResponseBody
	  public String xxtInterface(WeChat wc) throws Exception { 
	    String signature = wc.getSignature();
	    String timestamp = wc.getTimestamp();
	    String nonce = wc.getNonce();
	    String echostr = wc.getEchostr();

	    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
	      return echostr;
	    }
	    
	    return null; 
	    }
	  @RequestMapping(value={"/wexin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");

	    String respXml = CoreService.processRequest(request);
	    return respXml;
	  }
	  
	    @RequestMapping(value={"/totest"},method={org.springframework.web.bind.annotation.RequestMethod.POST})
		@ResponseBody
		public Map<String,String> totest(HttpServletRequest request,
				HttpServletResponse response) throws Exception,IOException{
	    	
			//网页授权后获取传递的参数
			String userId = request.getParameter("openId"); 	
			String finalmoney = request.getParameter("finalmoney"); 
			String orderId = request.getParameter("orderId"); 
			System.out.println("userId="+ userId);
			System.out.println("finalmoney="+ finalmoney);

			Map<String,String> map=new HashMap<String,String>();
			
			map.put("userId", userId);
			map.put("finalmoney", finalmoney);
			return map;	
					/*response.sendRedirect("/SpringGene1/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);*/
			
		}

}
