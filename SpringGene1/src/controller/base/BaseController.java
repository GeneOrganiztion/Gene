package controller.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import po.Admin;
import utils.ST;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
   
    
    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected String getParam(String k) {
        String value = getParam(k, null);  
        if(value != null && value != ""){
        	String data = null;
			try {
				/*data = new String(value.getBytes("ISO8859-1"),"UTF-8");*/ //有问题,在本地页面提交的中文是没有乱码现象的,如果再转一次就乱了
				data=value;
			}/* catch (UnsupportedEncodingException e) {
			}*/
			catch(Exception e){
				
			}
        	return data;
        }else{
        	return null;
        }
    }

    protected String getParam(String k, String def) {
        return StringUtils.trim(ST.getDefault(request.getParameter(k), def));
    }
    
    public HttpSession getNowSession()
    {
      ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
      return attrs.getRequest().getSession();
    }
    
    public Admin getNowUser()
    {
      Object object = getNowSession().getAttribute("SESSION_USER");
      if (object != null) {
    	  Admin adminInfo = (Admin)object;
        return adminInfo;
      }
      return null;
    }
    
    public Integer getUserId()
    {
    	Admin adminInfo = getNowUser();
    	if(!ST.isNull(adminInfo)){
    		return adminInfo.getId();
    	}
    	return null;
    }
    
    
}
