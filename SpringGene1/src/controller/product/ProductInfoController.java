package controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;

import po.Classify;
import po.ClassifyDemo;
import po.Image;
import po.Product;
import po.ResponseMessage;
import service.ClassifyService;
import service.ImageService;
import service.ProductService;
import utils.Constant;
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
	private String PRODUCT_PAGE = "product/productall";
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value="/productPage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PRODUCT_PAGE);
		return mv;
	}
 
	 @RequestMapping(value = "/phoneproall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> phoneproall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 List<Product> listproduct=new ArrayList<Product>();
		 String clsId = getParam("clsId");
		 try{
		 Product product=new Product();
		 product.setClassifyId(Integer.parseInt(clsId));
		 listproduct=productService.selectbyClassify(product);
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.info("phoneproall"+e);
		 }
		return listproduct;
	 }
	 
	 @RequestMapping(value = "/phoneall", method = RequestMethod.GET)
	 @ResponseBody
	 public List<Product> phoneall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 List<Product> listproduct=new ArrayList<Product>();
		 try{
		 Product product=new Product();
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
	 
	 @RequestMapping(value = "/UploadImage", method = RequestMethod.POST)
	 @ResponseBody
	 public ResponseMessage UploadImage(HttpServletRequest request,
			 @RequestParam("file") MultipartFile file) throws Exception {
		 	ResponseMessage msg=new ResponseMessage();
		 	String filepathurl=null;
			String pro_id= getParam("pro_id");
			System.out.println("pro_idhoutai="+pro_id);
		 	try {
			   Image image=new Image();
			   image.setProId(Integer.valueOf(pro_id));
		 	   List<Image> listimage=imageService.selectbyProduct(image);
		 	   if(null==pro_id||"".equals(pro_id)){
			      msg.setMessage("error");
		 	   }else{
		 		  if(listimage.size()<5){
			 		  String filepath = FileUpload.uploadFile(file, request);
				      filepathurl = filepath;
				      image.setUrl(filepath);			      
				      imageService.addImage(image);
				      msg.setMessage(filepathurl);
			 	   }else{
			 		  msg.setMessage("tomore");
			 	   }   
		 	   } 
		    } catch (Exception e) {
		      msg.setMessage("error");
		      logger.info("UploadImage"+e);
		      e.printStackTrace();
		      return null;
		    }	
		 	return msg;
		
	 }
	 /**
		 * 验证是否存在此用户名
		 * @param request
		 * @param response    -1代表插入商品失败    >0代表插入成功
		 * @return
		 */
	 @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
	 @ResponseBody
	 public int insertProduct(HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		 	int proid=-1;
		 	String classifyid = getParam("classify");
		 	String name= getParam("name");
		 	String head = getParam("head");
		 	String price = getParam("price");
		 	String sum = getParam("sum");
		 	String rateprice = getParam("rateprice");
		 	String isonline = getParam("isonline");
		 	try {
		 		Product product=new Product();
		 		product.setClassifyId(Integer.valueOf(classifyid));
		 		product.setProName(name);
		 		product.setProHead(head);
		 		product.setProductPrice(Integer.valueOf(price));
		 		product.setProRateprice(Integer.valueOf(rateprice));
		 		product.setProSum(Integer.valueOf(sum));
		 		if(Integer.valueOf(isonline)==1){
		 			product.setProOnline(true);
		 		}else{
		 			product.setProOnline(false);
		 		}
		 		proid=productService.saveProduct(product);
		    } catch (Exception e) {
		      logger.info("insertProduct"+e); 
		    }
		 	
		 	System.out.println("proid="+proid);
		 	return proid;
		
	 }
	 
	 
	 @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	 @ResponseBody
	 public boolean updateProduct(HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		 	boolean falg=false;
		 	String pro_id = getParam("id");
		 	String classifyid = getParam("classify");
		 	String name= getParam("name");
		 	String head = getParam("head");
		 	String price = getParam("price");
		 	String sum = getParam("sum");
		 	String rateprice = getParam("rateprice");
		 	String isonline = getParam("isonline");
		 	System.out.println("pro_id="+pro_id);
		 	try {
		 		Product product=new Product();
		 		product.setId(Integer.valueOf(pro_id));
		 		product.setClassifyId(Integer.valueOf(classifyid));
		 		product.setProName(name);
		 		product.setProHead(head);
		 		product.setProductPrice(Integer.valueOf(price));
		 		product.setProRateprice(Integer.valueOf(rateprice));
		 		product.setProSum(Integer.valueOf(sum));
		 		if(Integer.valueOf(isonline)==1){
		 			product.setProOnline(true);
		 		}else{
		 			product.setProOnline(false);
		 		}
		 		falg=productService.updateProduct(product);
		    } catch (Exception e) {
		      logger.info("updateProduct"+e); 
		    }
		 	return falg;
		
	 }
	 
	 
	 @RequestMapping(value = "/UploadDetailImage", method = RequestMethod.POST)
	 @ResponseBody
	 public ResponseMessage UploaddetailImage(HttpServletRequest request,
			 @RequestParam("file") MultipartFile file) throws Exception {
		 	ResponseMessage msg=new ResponseMessage();
		 	try {
		 		  String filepathurl=null;
		 		  String filepath = FileUpload.uploadFile(file, request);
			      filepathurl = filepath;
			      msg.setMessage(filepathurl);
		 	   }catch (Exception e) {
		      logger.info("UploaddetailImage"+e);
		      e.printStackTrace();
		      msg.setMessage("error");
		    }	
		 	return msg;
	 }
	 @RequestMapping(value = "/DeleteImage", method = RequestMethod.POST)
	 @ResponseBody
	 public ResponseMessage DeleteImage(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String filename = getParam("filename");
		 ResponseMessage msg=new ResponseMessage();
		 try{
			 
			 if(FileUpload.deleteObject(filename)){
				 msg.setMessage("success");	 
				 
			 }else{
			 msg.setMessage("error");	 
			 }
		 }catch(Exception e){
			 logger.info("DeleteImage"+e);
			 e.printStackTrace();
			 msg.setMessage("error");	 
		 }
		 return msg; 	
	 }
	 
	 @RequestMapping(value = "/DeleteShowImage", method = RequestMethod.POST)
	 @ResponseBody
	 public ResponseMessage DeleteShowImage(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String filename = getParam("filename");
		 String Url=Constant.OSS_ENDPOINT+filename;
		 ResponseMessage msg=new ResponseMessage();
		 try{
			 
			 if(FileUpload.deleteObject(filename)){
				 Image image=new Image();
				 System.out.println("Url="+Url);
				 image.setUrl(Url);
				 imageService.deleteImage(image);
				 msg.setMessage("success");	 
			 }else{
			 msg.setMessage("error");	 
			 }
		 }catch(Exception e){
			 logger.info("DeleteShowImage"+e);
			 e.printStackTrace();
			 msg.setMessage("error");	 
		 }
		 return msg; 	
	 }
	 
	 	@RequestMapping(value = "/selectProduct")
	    @ResponseBody
	    public PageInfo selectAdminByParams(HttpServletRequest request, HttpServletResponse response)throws Exception{
		 	String sidx = getParam("sidx");// 排序字段;
	        String sord = getParam("sord");// 升序降序;
	        PageInfo pageInfo = new PageInfo();
	        try {
	        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
	            int pageNo = Integer.valueOf(getParam("page"));// 第几页
	            String productName = getParam("productName");
	            String productPrice = getParam("productPrice");
	            String productRatePrice = getParam("productRatePrice");
	            String productSum = getParam("productSum");
	            String productOnline = getParam("productOnline");        
	            String beginTime = getParam("beginTime");
	            String endTime = getParam("endTime");
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("productName", productName);
	         /*   map.put("productPrice", Integer.valueOf(productPrice));
	            map.put("productRatePrice", Integer.valueOf(productRatePrice));
	            map.put("productSum", Integer.valueOf(productSum));
	            map.put("productOnline", Integer.valueOf(productOnline));*/
	            map.put("sidx", sidx);// 排序字段
	            map.put("sord", sord);// 升序降序
	            map.put("rowCount", oneRecord);//一页几行
	            map.put("pageNo", pageNo);
	            if(!ST.isNull(beginTime)){
	            	map.put("beginTime", beginTime + " 00:00:00");
	            }
	            if(!ST.isNull(endTime)){
	            	map.put("endTime", endTime + " 59:59:59");
	            }
	            map.put("productName", productName);
	            pageInfo= (PageInfo)productService.selectProductByParams(map);
			} catch (Exception e) {
				logger.error("selectProduct error:" + e);
			}
	        return pageInfo;
		}
	 
	 	
	 
}
