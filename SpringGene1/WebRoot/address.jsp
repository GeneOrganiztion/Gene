<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微信共享收货地址</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
	<script src="<%=path%>/assets/js/jquery.js"></script>
  </head>
  
  <body>
		<br><br><br><br><br><br><br>
  			<div style="text-align:center;size:30px;"><input type="button" style="width:200px;height:80px;" value="获取收货地址" onclick="calladdress()"></div>
  </body>
  
  
  
  
  
   <script type="text/javascript">
  
   $.ajax({
		type: "post",
		url:  "<%=path%>/weixin/addresstest.do",
		/* data: {ProductId: id}, */
		success: function(msg){
			console.log(msg);
			 wx.config({
		            debug: false,
		            appId: msg.appid,
		            timestamp: msg.timestamp,
		            nonceStr: msg.noncestr,
		            signature: msg.signature,
		            jsApiList: [
		              // 所有要调用的 API 都要加到这个列表中
		                'checkJsApi',
		                'openAddress',
		                
		              ]
		          });
		          wx.checkJsApi({
		    	      jsApiList: [
		    	          'openAddress',
		    	      ],
		    	      success: function (res) {
		    	          alert(JSON.stringify(res));
		    	      }
		    	  });   
		}
	});
         
          
      </script>
      
  <script type="text/javascript">
  
  	function calladdress(){
  		wx.openAddress({
            trigger: function (res) {
              alert('用户开始拉出地址');
            },
            success: function (res) {
              alert('用户成功拉出地址');
              alert(JSON.stringify(res));
            },
            cancel: function (res) {
              alert('用户取消拉出地址');
            },
            fail: function (res) {
              alert(JSON.stringify(res));
            }
          });
		}
  </script>
  
  
  
</html>
