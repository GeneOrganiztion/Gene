/**
 *
 * @author geloin
 * @date 2012-5-5 涓婂崍9:31:52
 */
package controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;





import po.Admin;
import service.AdminService;

/**
 *
 * @author chenzhangsheng
 * @date 2016-12-7 18:31:52
 */

@Controller
@RequestMapping("/CoreServlet")
public class LoginController extends BaseController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String formnoticedetail1(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Admin admin=null;
    	String msg=null;
    	String username = getParam("user");//用户名
        String password = getParam("psd");//密码
        try{
        admin=(Admin)adminService.login(username, password);
        System.out.println("admin="+admin);
        }catch(Exception e){	
        	e.printStackTrace();
        	msg = "服务器繁忙，请稍后重试";
            request.setAttribute("msg", msg);
            return "html/login";
        }
        if(null==admin){
        	msg = "用户名或密码错误,请确认是否输入正确";
            request.setAttribute("msg", msg);
            return "html/login";
        }
        if (admin.getIsdelete()) {
            msg = "当前用户已被禁用,请检查用户是否正常";
            request.setAttribute("msg", msg);
            return "html/login";
          }
        session.setAttribute("SESSION_USER", admin);
        return "index";
    }

}