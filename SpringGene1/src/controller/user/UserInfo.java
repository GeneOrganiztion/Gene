package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Cart;
import po.User;
import service.CartService;
import service.UserService;
import controller.base.BaseController;

@Controller
@RequestMapping("/user")
public class UserInfo  extends BaseController  {
	@Autowired
	private UserService userService;
	 @RequestMapping(value = "/phoneUserId")
	 @ResponseBody
	 public User phoneUserId(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String userId = getParam("userId");
		 User user=new User();
		 user.setId(Integer.parseInt(userId));
		return (User)userService.select(user);
	 }  
	
	

}
