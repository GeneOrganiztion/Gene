package controller.classify;

import java.util.ArrayList;
import java.util.Date;
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

import controller.base.BaseController;
import po.Classify;
import po.ClassifyModel;
import po.Product;
import po.ResModel;
import po.row;
import service.ClassifyService;
import service.ProductService;
import utils.Constant;
import utils.ST;

@Controller
@RequestMapping("/classify")
public class ClassifyController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ClassifyController.class);
	
	private String ONE_CLASSIFY_PAGE = "classify/oneClassifyList";
	private String TWO_CLASSIFY_PAGE = "classify/twoClassifyList";
	
	@Autowired
	private ClassifyService classifyService;
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping(value = "/oneClassifyListPage")
	@ResponseBody
	public ModelAndView classifyListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ONE_CLASSIFY_PAGE);
		return mv;
	}  
	@RequestMapping(value = "/twoClassifyListPage")
	@ResponseBody
	public ModelAndView twoClassifyListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(TWO_CLASSIFY_PAGE);
		return mv;
	}  
	@RequestMapping(value = "/selectOneClassify")
	@ResponseBody
	public Classify selectOneClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String classifyId = getParam("classifyId");
		Classify cls = new Classify();
		if(ST.isNull(classifyId)){
			return cls;
		}
		cls.setId(Integer.valueOf(classifyId));
		return classifyService.selectOneClassify(cls);
	}  
	@RequestMapping(value = "/updateTwoClassify")
	@ResponseBody
	public ResModel updateTwoClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String twoClassifyId = getParam("twoClassifyId");
		String editclaName = getParam("editclaName");
		String editOneClassify = getParam("editOneClassify");
		Classify cls = new Classify();
		cls.setId(Integer.valueOf(twoClassifyId));
		cls.setClaName(editclaName);
		cls.setClaPid(Integer.valueOf(editOneClassify));
		classifyService.updateClassify(cls);
		resModel.setSuccess(true);
		return resModel;
	}  
	@RequestMapping(value = "/selectAllOneClassify")
	@ResponseBody
	public List<Classify> selectAllOneClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Classify cls = new Classify();
		cls.setClaPid(0);
		return classifyService.selectAllOneClassify(cls);
	}  
	@RequestMapping(value = "/saveTwoClassify")
	@ResponseBody
	public ResModel saveTwoClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		Classify cls = new Classify();
		String claName = getParam("claName");
        String oneClassifyId = getParam("oneClassifyId");
        cls.setClaName(claName);
        cls.setClaPid(Integer.valueOf(oneClassifyId));
        classifyService.saveClassify(cls);
        resModel.setSuccess(true);
		return resModel;
	}  
	@RequestMapping(value = "/seletcClassify")
	@ResponseBody
	public PageInfo seletcClassify(HttpServletRequest request,HttpServletResponse response){
		String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo pageInfo = new PageInfo();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String claName = getParam("claName");
            String claPid = getParam("claPid");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            String classifyId = getParam("classifyId");
            String flag = getParam("flag");//区分查询的是一级分类还是二级分类    一级分类：oneClassify  二级分类：twoClassify
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("claName", claName);
            map.put("flag", flag);
            map.put("classifyId", classifyId);
            map.put("claPid", claPid);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            pageInfo= (PageInfo)classifyService.selectClassifyParams(map);
		} catch (Exception e) {
			logger.error("seletcClassify error:" + e);
		}
        return pageInfo;
	}
	
	@RequestMapping(value = "/editOneClassify")
	@ResponseBody
	public ResModel editOneClassify(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String claName1 = getParam("claName");
		 String claName =  new String(claName1.getBytes("ISO-8859-1"),"UTF-8");
		 String classifyId = getParam("classifyId");
		 
		 try {
			 Classify cls = new Classify();
			 String filepath = FileUpload.uploadFile(file, request);
			 cls.setId(Integer.valueOf(classifyId));
			 cls.setClaName(claName);
			 cls.setClaContent(filepath);
			 cls.setLastModifiedTime(new Date());
			 
			 classifyService.updateClassify(cls);
			 
		} catch (Exception e) {
			logger.error("editOneClassify error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	@RequestMapping(value = "/saveOneClassify", method = RequestMethod.POST)
	@ResponseBody
	public ResModel saveOneClassify(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String claName1 = getParam("claName");
		 String claName =  new String(claName1.getBytes("ISO-8859-1"),"UTF-8");
		 try {
			 Classify cls = new Classify();
			 String filepath = FileUpload.uploadFile(file, request);
			 cls.setClaName(claName);
			 cls.setClaContent(filepath);
			 cls.setClaPid(Constant.ONE_CLASSID);
			 cls.setIsdelete(false);
			 cls.setCreateTime(new Date());
			 cls.setLastModifiedTime(new Date());
			 Integer id = classifyService.insertOneClassifyReturnId(cls);
			 resModel.setReturnId(id);
			 
		} catch (Exception e) {
			logger.error("saveOneClassify error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	@RequestMapping(value = "/removeOneClassfyById")
	@ResponseBody
	public ResModel removeReportById(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String oneClassifyId = getParam("oneClassifyId");
		if(ST.isNull(oneClassifyId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Classify cls = new Classify();
		cls.setId(Integer.valueOf(oneClassifyId));
		boolean bl = false;
		try {
			//删除服务器上的图片
			Classify clsfy = classifyService.selectOneClassify(cls);
			FileUpload.deleteObject(clsfy.getClaContent());
			bl = classifyService.delOneClassifyById(cls);
		} catch (Exception e) {
			logger.error("removeOrderById error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		if(!bl){
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
	@RequestMapping(value = "/delectOneClassifyPic")
	@ResponseBody
	public ResModel delectOneClassifyPic(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String oneClassifyId = getParam("oneClassifyId");
		Classify cls = new Classify();
		if(ST.isNull(oneClassifyId)){
			return resModel;
		}
		cls.setId(Integer.valueOf(oneClassifyId));
		cls.setClaContent("");
		cls.setIsdelete(false);
		cls.setLastModifiedTime(new Date());
		try {
			classifyService.updateClassify(cls);
		} catch (Exception e) {
			logger.error("delectOneClassifyPic error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}  
	
	@RequestMapping(value = "/deleteOneClassify")  //删除一级分类
	@ResponseBody
	public ResModel deleteOneClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String ids = getParam("oneClassifyIds");
		Classify cls = new Classify();
		Product product=new Product();
		try {
			List<Integer> list = ST.StringToList(ids);
			for(Integer id: list){
				cls.setId(id);
				//删除服务器上的图片
					Classify clsfy = classifyService.selectOneClassify(cls);
				
					
					//删除一级分类下的二级分类
					Classify twoCls = new Classify();
					twoCls.setClaPid(id);
					List<Classify> twoClassify = classifyService.selectTwoClassify(twoCls);
					for(Classify classify: twoClassify){
						Classify twoclsfy = new Classify();
						twoclsfy.setId(classify.getId());
						product.setClassifyId(classify.getId());
						List<Product> listproduct=productService.selectAll(product);
							if(listproduct.size()>0){
								resModel.setSuccess(false);
								resModel.setMsg("此分类下还有未下架的商品");
								return resModel;
							}else{
								classifyService.delClassify(twoclsfy);
							}
					}
					FileUpload.deleteObject(clsfy.getClaContent());
					classifyService.delClassify(cls);
			}
		} catch (Exception e) {
			logger.error("deleteOneClassify error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}  
	@RequestMapping(value = "/deleteTwoClassify")
	@ResponseBody
	public ResModel deleteTwoClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String ids = getParam("twoClassifyIds");
		Classify cls = new Classify();
		try {
			List<Integer> list = ST.StringToList(ids);
			for(Integer id: list){
				cls.setId(id);
					int result = classifyService.delClassify(cls);//返回0表示删除成功 返回状态1表示删除失败,2表示还有以存在的商品未下架
					if(2 == result){
						resModel.setSuccess(false);
						resModel.setMsg("此分类下还有未下架的商品");
						return resModel;
					}else if(1 == result){
						resModel.setSuccess(false);
						return resModel;
					}
				
			}
		} catch (Exception e) {
			logger.error("deleteTwoClassify error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}  
	
	
	 @RequestMapping(value = "/webclsall")
	 @ResponseBody
	 public List<row> webclsall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		List<row> rows=new ArrayList<row>();
		Classify cls=new Classify();
		cls.setClaPid(0);
		try{
		List<Classify> classifyall=classifyService.selectAll(cls);
		
		for(int i=0;i<classifyall.size();i++){
			Classify clsify=new Classify();
			row rowone=new row();
			clsify.setClaPid(classifyall.get(i).getId());
			rowone.setName(classifyall.get(i).getClaName());
			rowone.setId(classifyall.get(i).getId());
			List<Classify> classifylist=classifyService.selectAll(clsify);
			rowone.setRow(classifylist);
			rows.add(rowone);
		}
		}catch(Exception e){
			logger.info("webclsall"+e);
		
		}
		return rows;
	 }  
	 
}
