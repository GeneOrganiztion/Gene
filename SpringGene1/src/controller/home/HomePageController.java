package controller.home;

import java.util.HashMap;
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

import service.ClassifyService;
import service.HomePageService;
import utils.ST;

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
	
	
	
	
}
