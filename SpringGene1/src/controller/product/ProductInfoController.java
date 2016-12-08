package controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Classify;
import po.Product;
import controller.base.BaseController;

@Controller
@RequestMapping("/web")
public class ProductInfoController extends BaseController{
	
	/* @RequestMapping(value = "/proall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> clsall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 
		//return classifyService.selectAll();
	 }    	*/

}
