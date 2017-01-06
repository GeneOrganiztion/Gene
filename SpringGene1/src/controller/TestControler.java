package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
import po.Cart;
import po.ResModel;
import po.Student;
import po.User;
import service.CartService;
import service.StudentService;
import service.UserService;

@Controller
@RequestMapping(value="/testStudent")
public class TestControler extends BaseController {

    @SuppressWarnings("unused")
    @Autowired
    private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	

    @RequestMapping(value = "/test")
    @ResponseBody
    public void testStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String openid = getParam("openid");//用户名
		String nickname=getParam("nickname");
		String sex=getParam("sex");
		String province=getParam("province");
		String city=getParam("city");
		String headimgurl=getParam("headimgurl");
		String userid=null;
		boolean isman=true;
		if("1".equals(sex)){
			
		}else{
			isman=false;
		}
		User user =new User();
		user.setOpenid(openid);
		User own=(User)userService.select(user);
		System.out.println("own="+own);
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
		System.out.println("userid="+userid);
		System.out.println("openid="+openid);
    }
    
    @RequestMapping(value = "/testPage")
    public ModelAndView testPage(HttpServletRequest request,HttpServletResponse response) {
    	ModelAndView modelAndView = new ModelAndView("student");
       // modelAndView.setViewName("student");
        return modelAndView;

    }
    
    @RequestMapping(value = "/phonegettime")
    @ResponseBody
    public List<Integer> phonegettime(HttpServletRequest request,HttpServletResponse response) {
    	List<Integer> listint=new ArrayList<Integer>();
    	listint.add(25);
    	listint.add(30);
        return listint;

    }
    
    @RequestMapping(value = "/phonegetmoney")
    @ResponseBody
    public Integer phonegetmoney(HttpServletRequest request,HttpServletResponse response) {
    	
        return 1;

    }

}
