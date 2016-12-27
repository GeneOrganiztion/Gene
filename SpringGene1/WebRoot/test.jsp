<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信获取用户信息</title>
	<script src="<%=path%>/assets/js/jquery.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
</head>
<body>

 <div style="text-align:center;size:30px;"><input type="button" style="width:200px;height:80px;" value="获取收货地址" onclick="callperson()"></div>
	 <script type="text/javascript">
	 function callperson(){
		  var openid="ofzXwvr0wTScgzzGAiTC89IxfbrA";
		  var finalmoney=111;
		   $.ajax({
				type: "post",
				url:  "<%=path%>/weixin/topay.do",
				data: {openId: "ofzXwvnbUQYrVMmYn8uxZuHbbX5g", finalmoney: 30, orderId: 2, orderProducts:[{pro_count:3,pro_id:3}]},
				success: function(msg){
					alert(msg);
					
				}
			});  
	 }
      </script>

</body>
</html>