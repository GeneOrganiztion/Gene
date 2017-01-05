package controller.report;

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

import com.github.pagehelper.PageInfo;

import po.Report;
import service.ReportService;
import utils.ST;
import controller.base.BaseController;
import controller.order.OrderDetailController;

@Controller
@RequestMapping("/report")
public class ReportInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
	@Autowired
	private ReportService<Report> reportService;
	
	
	private String REPORT_PAGE = "report/reportList";
	
	
	
	@RequestMapping(value = "/reportListPage")
	@ResponseBody
	public ModelAndView classifyListPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(REPORT_PAGE);
		return mv;
	}  
	
	@RequestMapping(value = "/seletreport")
	@ResponseBody
	public PageInfo seletcClassify(HttpServletRequest request,HttpServletResponse response){
		String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo pageInfo = new PageInfo();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String reportName = getParam("reportName");
            String userName = getParam("userName");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            String reportID = getParam("reportID");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("reportName", reportName);
            map.put("reportID", reportID);
            map.put("userName", userName);
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            pageInfo= (PageInfo)reportService.selectReportParams(map);
		} catch (Exception e) {
			logger.error("seletcClassify error:" + e);
		}
        return pageInfo;
	}
	/**
	 * 根据orId,proId,userId中的一个或者多个参数查询报告
	 * @param request  orId订单Id   proId商品Id    userId用户Id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneSelectReportByParams")
	@ResponseBody
	public List<Report> selectReportByParams(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String orId = getParam("orId");
		String proId = getParam("proId");
		String userId = getParam("userId");
		List<Report> list = new ArrayList<Report>();
		Report report = new Report();
		try {
			if(!ST.isNull(orId)){
				report.setOrdId(Integer.valueOf(orId));
			}
			if(!ST.isNull(proId)){
				report.setProId(Integer.valueOf(proId));
			}
			if(!ST.isNull(userId)){
				report.setUserId(Integer.valueOf(userId));
			}
			report.setIsdelete(false);
			list = reportService.selectReportByParams(report);
		} catch (Exception e) {
			logger.error("selectReportByParams error:" + e);
		}
		return list;
	}
	
	
}
