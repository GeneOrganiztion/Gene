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
	private String PRODUCTEDITOR_PAGE = "product/producteditor";
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value="/productPage")
	public ModelAndView productPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PRODUCT_PAGE);
		return mv;
	}
	@RequestMapping(value="/productEditorPage")
	public ModelAndView productEditorPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(PRODUCTEDITOR_PAGE);
		return mv;
	}

 
	 @RequestMapping(value = "/phoneproall")
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
	 
	 @RequestMapping(value = "/phoneall")
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
	 
	 @RequestMapping(value = "/weball", method = RequestMethod.POST)
	 @ResponseBody
	 public List<Product> weball(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 List<Product> listproduct=new ArrayList<Product>();
		 try{
		 Product product=new Product();
		 listproduct=productService.selectWebAll(product);
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.info("weball"+e);
		 }
		return listproduct;
	 }
	 
	 @RequestMapping(value = "/phonefirstall")
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
			 classifydemo.setListProduct(productService.selectbyClassify(product));
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
						classifydemo.setListProduct(productService.selectbyClassify(product));
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
	 
	 @RequestMapping(value = "/UploadImage")
	 @ResponseBody
	 public ResponseMessage UploadImage(HttpServletRequest request,
			 @RequestParam("file") MultipartFile file) throws Exception {
		 	ResponseMessage msg=new ResponseMessage();
		 	String filepathurl=null;
			String pro_id= getParam("pro_id");
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
	 @RequestMapping(value = "/insertProduct")
	 @ResponseBody
	 public int insertProduct(HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		 	int proid=-1;
		 	String classifyid = getParam("classify");
		 	String name= getParam("name");
		 	String head = getParam("head");
		 	String price = getParam("price");
		 	String geneNum = getParam("genenum");
			String comment = getParam("comment");
		 	String sum = getParam("sum");
		 	String rateprice = getParam("rateprice");
		 	String isonline = getParam("isonline");
		 	try {
		 		Product product=new Product();
		 		product.setClassifyId(Integer.valueOf(classifyid));
		 		product.setProName(name);
		 		product.setGeneNum(Integer.valueOf(geneNum));
		 		product.setProHead(head);
		 		product.setproRemark(comment);
		 		product.setSelNumber(0);
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
	 
	 
	 @RequestMapping(value = "/updateProduct")
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
		 	String geneNum = getParam("genenum");
		 	String rateprice = getParam("rateprice");
		 	String isonline = getParam("isonline");
		 	try {
		 		Product product=new Product();
		 		product.setId(Integer.valueOf(pro_id));
		 		product.setClassifyId(Integer.valueOf(classifyid));
		 		product.setProName(name);
		 		product.setGeneNum(Integer.valueOf(geneNum));
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
	 
	 
	 @RequestMapping(value = "/UploadDetailImage")
	 @ResponseBody
	 public ResponseMessage UploaddetailImage(HttpServletRequest request,
			 @RequestParam("file") MultipartFile file) throws Exception {
		 	String name= getParam("name");
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
	 @RequestMapping(value = "/DeleteImage")
	 @ResponseBody
	 public ResponseMessage DeleteImage(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String url = getParam("filename");
		 ResponseMessage msg=new ResponseMessage();
		 try{
			 String filename=ST.getFileName(url);
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
	 
	 @RequestMapping(value = "/DeleteImageById")
	 @ResponseBody
	 public ResponseMessage DeleteImageById(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String id = getParam("ImageId");
		 ResponseMessage msg=new ResponseMessage();
		 try{
			 Image image=new Image();
			 image.setId(Integer.valueOf(id));
			 image=(Image)imageService.selectbyId(image);
			 String filename=ST.getFileName(image.getUrl());
			 if(FileUpload.deleteObject(filename)){
				 imageService.deleteImage(image);
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
	 @RequestMapping(value = "/SelectImage")
	 @ResponseBody
	 public boolean SelectImage(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String pro_id = getParam("id");
		 boolean flag=false;
		 try{
			 Image image=new Image();
			 image.setProId(Integer.valueOf(pro_id));
			 List<Image> listImage=imageService.ImagebyProductId(image);
			 if(listImage.size()==0){
				 
			 }else{
				 flag=true; 
			 }
			 
		 }catch(Exception e){
			 logger.info("SelectImage"+e);
			 e.printStackTrace();  
		 }
		 return flag; 	
	 }
	 
	 
	 @RequestMapping(value = "/DeleteShowImage")
	 @ResponseBody
	 public ResponseMessage DeleteShowImage(HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		 String url = getParam("filename");
		 ResponseMessage msg=new ResponseMessage();
		 try{
			 String filename=ST.getFileName(url);
			 if(FileUpload.deleteObject(filename)){
				 Image image=new Image();
				 image.setUrl(url);
				 if(imageService.deleteImage(image)){
					 msg.setMessage("success");	 
				 }else{
					 msg.setMessage("error");	  
				 }
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
	            if("".equals(productName)||null==productName){
	            	
	            }else{
	            System.out.println("proName="+new String(new String(productName.getBytes("iso-8859-1"),"UTF-8")));
	            }
	            String productID = getParam("productID"); 
	            String productOnline = getParam("productOnline");
	            String oneclassify_id = getParam("oneclassify");
	            String classify_id = getParam("classify");
	            Map<String, Object> map = new HashMap<String, Object>();
	            List<Integer> list = null;
	            String clslist="";
	           if(null==oneclassify_id&&null==classify_id){
	        	   map.put("classifylist",null);
	           }else{
	        	   if(classify_id!=null){
	        		   list=ST.StringToList(classify_id);
	        		   map.put("classifylist",list);
	        	   }else{
	        		   Classify towcls=new Classify();
	        		   towcls.setClaPid(Integer.valueOf(oneclassify_id));
	        		   List<Classify> clslistobj=classifyService.selectTwoClassify(towcls);
	        		   for(int i=0;i<clslistobj.size();i++){
	        			   if(clslistobj.size()==1||i==(clslistobj.size()-1)){
	        				   clslist=clslist+clslistobj.get(i).getId();
	        			   }else{
	        				   clslist=clslist+clslistobj.get(i).getId()+","; 
	        			   }
	        			   
	        		   }
	        		 
	        	   }       	   
	           }
	           System.out.println("clslist="+clslist);
	            if(!ST.isNull(clslist)){
	            	list=ST.StringToList(clslist);
	            	System.out.println("list="+list);
					map.put("classifylist",list);
				}
	            Object IsOnline=true;
	            if("true".equals(productOnline)){
	            	IsOnline=true;
	            }else if("false".equals(productOnline)){
	            	IsOnline=false;
	            }else{
	            	IsOnline=null;
	            }
	            
	            map.put("productOnline", IsOnline);
	            String beginTime = getParam("beginTime");
	            String endTime = getParam("endTime");
	            map.put("productName", productName);
	            map.put("productID", productID);
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
	            pageInfo= (PageInfo)productService.selectProductByParams(map);
			} catch (Exception e) {
				logger.error("selectProduct error:" + e);
			}
	        return pageInfo;
		}
	 
	 	 @RequestMapping(value = "/selectOneProduct")
		 @ResponseBody
		 public Product selectOneProduct(HttpServletRequest request,
		            HttpServletResponse response) throws Exception {
	 		 String pro_id = getParam("ProductId");
	 		 Product ResultProduct=new Product();
			 try{
			 Product product=new Product();
			 product.setId(Integer.valueOf(pro_id));
			 ResultProduct=(Product)productService.selectOne(product);
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.info("selectOneProduct"+e);
			 }
			return ResultProduct;
		 }
	 	 
	 	 @RequestMapping(value = "/phoneOneProduct")
		 @ResponseBody
		 public Product phoneOneProduct(HttpServletRequest request,
		            HttpServletResponse response) throws Exception {
	 		 String pro_id = getParam("ProductId");
	 		 Product ResultProduct=new Product();
			 try{
			 Product product=new Product();
			 product.setId(Integer.valueOf(pro_id));
			 ResultProduct=(Product)productService.selectOne(product);
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.info("phoneOneProduct"+e);
			 }
			return ResultProduct;
		 }
	 	 
	 	 @RequestMapping(value = "/selectImageProduct")
		 @ResponseBody
		 public List<Image> selectImageProduct(HttpServletRequest request,
		            HttpServletResponse response) throws Exception {
	 		 String pro_id = getParam("ProductId");
	 		 Image image=new Image();
	 		List<Image> imagelist=null;
			 try{
			 image.setProId(Integer.valueOf(pro_id));
			 imagelist=imageService.ImagebyProductId(image);
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.info("selectImageProduct"+e);
			 }
			return imagelist;
		 }
	 	 
	 	 @RequestMapping(value = "/addProductContent")
		 @ResponseBody
		 public Boolean addProductContent(HttpServletRequest request,
		            HttpServletResponse response) throws Exception {
	 		 String pro_id = getParam("ProductId");
	 		 String proDetail = getParam("productDetail");
	 		 Product product=new Product();
	 		 boolean flag=false;
			 try{
			  product.setId(Integer.valueOf(pro_id));
			  product.setProDetail(proDetail);
			  flag=productService.updateProduct(product);
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.info("addProductContent"+e);
			 }
			return flag;
		 }
	 	 
	 	@RequestMapping(value="/deleteproduct")
		@ResponseBody
		public boolean delete(HttpServletRequest request,HttpServletResponse response){
			String 	productIds = getParam("ProductId");
			try {
				List<Integer> list = ST.StringToList(productIds);
				productService.deleteProductIds(list);
			} catch (Exception e) {
				logger.error("deleteProduct error:" + e);
				return false;
			}
			return true;
		}
}
