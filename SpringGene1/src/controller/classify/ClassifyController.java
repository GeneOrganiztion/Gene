package controller.classify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
import utils.ST;

@Controller
@RequestMapping("/classify")
public class ClassifyController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ClassifyController.class);
	
	private String CLASSIFY_PAGE = "classify/oneClassifyList";
	
	@RequestMapping(value = "/oneClassifyListPage")
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
            String orderId = getParam("orderId");
            String ordNum = getParam("ordNum");
            String ordState = getParam("ordState");
            String flag = getParam("flag");
            if(ST.isNull(ordState)){
            	if("reAndDeOrder".equals(flag)){
            		ordState = "2,3";
            	}else if("detectionOrder".equals(flag)){
            		ordState = "4,5,6";
            	}else if("completeOrder".equals(flag)){
            		ordState = "7";
            	}
            }
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("ordNum", ordNum);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            map.put("orderId", orderId);
            if(!ST.isNull(ordState)){
				List<Integer> list = ST.StringToList(ordState);
				map.put("stateList", list);
			}
            //pageInfo= (PageInfo)orderService.selectOrderByParams(map);
		} catch (Exception e) {
			logger.error("seletcOrder error:" + e);
		}
        return pageInfo;
	}
}
