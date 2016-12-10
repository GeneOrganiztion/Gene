package controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Classify;
import po.ClassifyDemo;
import po.Product;
import service.ClassifyService;
import service.ProductService;
import utils.ST;
import controller.base.BaseController;
import controller.order.OrderDetailController;

@Controller
@RequestMapping("/product")
public class ProductInfoController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ProductInfoController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private ClassifyService classifyService;
	 @RequestMapping(value = "/phoneproall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> phoneproall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 List<Product> listproduct=new ArrayList<Product>();
		 String clsId = getParam("clsId");
		 try{
		 Product product=new Product();
		 product.setIsdelete(false);
		 product.setProOnline(true);
		 product.setClassifyId(Integer.parseInt(clsId));
		 listproduct=productService.selectAll(product);
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.info("phoneproall"+e);
		 }
		return listproduct;
	 }
	 
	 @RequestMapping(value = "/phonefirstall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<ClassifyDemo> phonefirstall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 List<ClassifyDemo> classifydemolist=new ArrayList<ClassifyDemo>();
		 String clsId = getParam("clsId");
		 try{
		 Classify cls=new Classify();
		 cls.setClaPid(Integer.parseInt(clsId));
		 List<Classify> listClassify=classifyService.selectAll(cls);
		 if(null==listClassify||listClassify.size()==0){
			 System.out.println("listClassify.size()"+listClassify.size());
			 ClassifyDemo classifydemo=new ClassifyDemo();
			 classifydemo.setId(Integer.parseInt(clsId));
			 Product product=new Product();
			 product.setClassifyId(Integer.parseInt(clsId));
			 classifydemo.setListProduct(productService.selectAll(product));
			 classifydemolist.add(classifydemo);
		 }else{
			 for(int i=0;i<listClassify.size();i++){
				System.out.println("listClassify.size()="+listClassify.size());
				ClassifyDemo classifydemo=new ClassifyDemo();
				//第一个classify填充product
					if(i==0){
						int classifyid=listClassify.get(i).getId();
						System.out.println("classifyid="+classifyid);
						classifydemo.setId(classifyid);
						classifydemo.setClaName(listClassify.get(i).getClaName());
						Product product=new Product();
						product.setClassifyId(classifyid);
						classifydemo.setListProduct(productService.selectAll(product));
						classifydemolist.add(classifydemo);
					}else{
						System.out.println("classifyid="+listClassify.get(i).getId());
						classifydemo.setId(listClassify.get(i).getId());
						classifydemo.setClaName(listClassify.get(i).getClaName());
						classifydemolist.add(classifydemo);	
					}
				 
			 } 
		 }
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.info("phonefirstall"+e);
		 }
		return classifydemolist;
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
