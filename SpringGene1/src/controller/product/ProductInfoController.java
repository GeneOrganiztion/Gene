package controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Classify;
import po.Product;
import service.ClassifyService;
import service.ProductService;
import utils.ST;
import controller.base.BaseController;

@Controller
@RequestMapping("/product")
public class ProductInfoController extends BaseController{
	
	@Autowired
	private ProductService productService;
	
	 @RequestMapping(value = "/phoneproall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> phoneproall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String clsId = getParam("clsId");
		 Product product=new Product();
		 product.setIsdelete(false);
		 product.setProOnline(true);
		 product.setClassifyId(Integer.parseInt(clsId));
		return productService.selectAll(product);
	 }   
	 
	 
	/* @RequestMapping(value = "/savepro", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> savepro(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 String pro_name = getParam("pro_name");
		 String pro_head = getParam("pro_head");
		 String pro_price = getParam("pro_price");
		 String pro_count = getParam("pro_count");
		 String pro_rateprice = getParam("pro_rateprice");
		 String pro_online = getParam("pro_online");
		 String pro_image = getParam("pro_image");
		 String pro_detail = getParam("pro_detail");
		 String clsId = getParam("clsId");
		 Product product=new Product();
		 product.setIsdelete(false);
		 product.setProOnline(true);
		 product.
		return productService.selectAll(product);
	 }   */

}
