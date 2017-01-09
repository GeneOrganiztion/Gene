package controller.classify;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import po.Classify;
import po.ResModel;
import service.ClassifyService;
import service.ProductService;
import utils.ST;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
@Controller
@RequestMapping("/classify2")
public class Classify2Controller extends BaseController{
	
	
private static final Logger logger = LoggerFactory.getLogger(ClassifyController.class);
	
	private String CLASSIFY_PAGE = "classify/classifyList";
	
	@Autowired
	private ClassifyService classifyService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/classifyListPage")
	@ResponseBody
	public ModelAndView classifyListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(CLASSIFY_PAGE);
		return mv;
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
	@RequestMapping(value = "/seletcTwoClassify")
	@ResponseBody
	public List<Classify> seletcTwoClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Classify cls = new Classify();
		List<Classify> list = new ArrayList<Classify>();
		String classifyId = getParam("classifyId");
		if(ST.isNull(classifyId)){
			return list;
		}
		cls.setClaPid(Integer.valueOf(classifyId));
		list = classifyService.selectTwoClassify(cls);
		return list;
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
	
	@RequestMapping(value = "/seletcOneTwoClassify")
	@ResponseBody
	public Classify seletcOneTwoClassify(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Classify cls = new Classify();
		String classifyId = getParam("classifyId");
		if(ST.isNull(classifyId)){
			return cls;
		}
		cls.setId(Integer.valueOf(classifyId));
		cls = classifyService.selectOneClassify(cls);
		return cls;
	}  
}
