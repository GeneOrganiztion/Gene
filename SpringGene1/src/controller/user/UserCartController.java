package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.BaseController;
import po.Cart;
import po.MapProductCart;
import po.Product;
import service.CartService;
import service.ClassifyService;

@Controller
@RequestMapping("/cart")
public class UserCartController extends BaseController {
	
	
	@Autowired
	private CartService cartService;
	
	 @RequestMapping(value = "/phonecartsel")
	 @ResponseBody
	 public Cart cartsel(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String userId = getParam("userId");
		 Cart cart=new Cart();
		 cart.setUserId(Integer.parseInt(userId));
		return (Cart)cartService.selectCart(cart);
	 }  
	 //cartId当用户登录的时候就取到缓存在前端
	 //添加商品的时候需要判断是否是同一种商品,如果是要将商品合并
	 @RequestMapping(value = "/phonecartadd")
	 @ResponseBody
	 public boolean phonecartadd(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String proId = getParam("proId");
		 String cartId = getParam("cartId");
		 String procount = getParam("procount");
		 MapProductCart mapduct=new MapProductCart();
		 mapduct.setCartId(Integer.parseInt(cartId));
		 mapduct.setProId(Integer.parseInt(proId));
		return cartService.addProduct(mapduct,Integer.parseInt(procount));
	 }  
	 
	 @RequestMapping(value = "/phonecartdel")
	 @ResponseBody
	 public boolean phonecartdel(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String maproId = getParam("maproId");
		 MapProductCart mapduct=new MapProductCart();
		 mapduct.setId(Integer.parseInt(maproId));
		return cartService.deleProduct(mapduct);
	 }
	 
	 
	  

}
