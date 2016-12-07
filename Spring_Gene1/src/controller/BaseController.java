package controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        return getParam(k, null);
    }

    protected String getParam(String k, String def) {
        return StringUtils.trim(ST.getDefault(request.getParameter(k), def));
    }
}
