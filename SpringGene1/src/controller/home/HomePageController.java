package controller.home;

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

import po.Classify;
import po.HomePage;
import po.Product;
import po.ResModel;
import service.ClassifyService;
import service.HomePageService;
import utils.Constant;
import utils.ST;

import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
import controller.order.OrderDetailController;

@Controller
@RequestMapping(value="/home")
public class HomePageController extends BaseController {
private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	@Autowired
	private HomePageService homePageService;
	private String HOME_PAGE = "home/homePage";
	
	@RequestMapping(value="/homePage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(HOME_PAGE);
		return mv;
	}
	
	@RequestMapping(value = "/phoneHomePage")
	@ResponseBody
	public List<HomePage> phoneHomePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HomePage hop=new HomePage();
		return homePageService.selectAll(hop);
	}
	
	@RequestMapping(value = "/seletcHomePage")
	@ResponseBody
	public PageInfo seletcClassify(HttpServletRequest request,HttpServletResponse response){
		String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo pageInfo = new PageInfo();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String HomeName = getParam("HomeName");
            System.out.println("HomeName="+HomeName);
            String HomePageid = getParam("HomePageid");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
       
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("HomeName", HomeName);
            map.put("HomePageid", HomePageid);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            pageInfo= (PageInfo)homePageService.selectHomePageParams(map);
		} catch (Exception e) {
			logger.error("seletcClassify error:" + e);
		}
        return pageInfo;
	}
	
	
	
	
	
	@RequestMapping(value = "/saveHomePage", method = RequestMethod.POST)
	@ResponseBody
	public ResModel saveOneClassify(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String HomeName = getParam("HomeName");
		 String Name =  new String(HomeName.getBytes("ISO-8859-1"),"UTF-8");
		 String HomeUrl = getParam("HomeUrl");
		 String HomeComment = getParam("HomeComment");
		 String Comment =  new String(HomeComment.getBytes("ISO-8859-1"),"UTF-8");
		 try {
			 HomePage page = new HomePage();
			 String filepath = FileUpload.upFileRename(file, request);
			 page.setName(Name);
			 page.setImage(filepath);
			 page.setComment(Comment);
			 page.setHref(HomeUrl);
			 page.setIsdelete(false);
			 page.setCreateTime(new Date());
			 page.setLastModifiedTime(new Date());
			 Integer id = homePageService.saveHomePage(page);
			 resModel.setReturnId(id);
			 
		} catch (Exception e) {
			logger.error("saveHomePage error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	@RequestMapping(value = "/selectOneHomePage")
	@ResponseBody
	public HomePage selectOneHomePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String HomePageId = getParam("HomePageId");
		 HomePage page = new HomePage();
		if(ST.isNull(HomePageId)){
			return page;
		}
		page.setId(Integer.valueOf(HomePageId));
		return homePageService.selectOneHomePageById(page);
	}  
	@RequestMapping(value = "/deleteHomePage")
	@ResponseBody
	public ResModel deleteHomePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String ids = getParam("HomePageIds");
		HomePage page = new HomePage();
		try {
			List<Integer> list = ST.StringToList(ids);
			for(Integer id: list){
				page.setId(id);
				homePageService.delHomePage(page);
				resModel.setSuccess(true);	
			}
		}catch(Exception e){
			logger.error("deleteHomePage error" + e);
			resModel.setSuccess(false);
		}
		return resModel;
	} 
	@RequestMapping(value = "/delectHomePagePic")
	@ResponseBody
	public ResModel delectHomePagePic(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResModel resModel = new ResModel();
		String HomePageId = getParam("HomePageId");
		HomePage page = new HomePage();
		if(ST.isNull(HomePageId)){
			return resModel;
		}
		page.setId(Integer.valueOf(HomePageId));
		page.setImage("");
		page.setLastModifiedTime(new Date());
		try {
			homePageService.updateHomePage(page);
		} catch (Exception e) {
			logger.error("delectHomePagePic error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	

	@RequestMapping(value = "/editHomePage")
	@ResponseBody
	public ResModel editHomePage(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String HomePageid  = getParam("HomePageid");
		 String HomeName = getParam("HomeName");
		 String Name =  new String(HomeName.getBytes("ISO-8859-1"),"UTF-8");
		 String HomeUrl = getParam("HomeUrl");
		 String HomeComment = getParam("HomeComment");
		 String Comment =  new String(HomeComment.getBytes("ISO-8859-1"),"UTF-8");
		 try {
			 HomePage page = new HomePage();
			 String filepath = FileUpload.upFileRename(file, request);
			 page.setId(Integer.valueOf(HomePageid));
			 page.setName(Name);
			 page.setImage(filepath);
			 page.setComment(Comment);
			 page.setHref(HomeUrl);
			 page.setIsdelete(false);
			 page.setCreateTime(new Date());
			 page.setLastModifiedTime(new Date());
			 homePageService.updateHomePage(page);
			 resModel.setSuccess(true);
			 
		} catch (Exception e) {
			logger.error("editHomePage error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
}
