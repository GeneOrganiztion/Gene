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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;

import po.MapOrderProduct;
import po.Orders;
import po.Report;
import po.ResModel;
import service.MapOrderProductService;
import service.ReportService;
import utils.Constant;
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
	
	@Autowired
	private MapOrderProductService mapOrderProductService;
	
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
            String orderNumber = getParam("orderNumber");
            							
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
            map.put("orderNumber", orderNumber);
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
	/**
	 * 根据reportId 数查询报告
	 * @param request  reportId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectReoprt")
	@ResponseBody
	public Report selectReoprt(HttpServletRequest request,HttpServletResponse response){
		String reportId = getParam("reportId");
		Report report = new Report();
		if(ST.isNull(reportId)){
			return report;
		}
		try {
			report = reportService.selectReportByReportId(Integer.valueOf(reportId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}
	
	
	@RequestMapping(value = "/saveReoprt")
	@ResponseBody
	public ResModel saveReoprt(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String reportId = getParam("reportId");
		String reportName = getParam("reportName");
		String repResult = getParam("repResult");
		String repState = getParam("repState");
		Report report = new Report();
		report.setId(Integer.valueOf(reportId));
		report.setRepName(reportName);
		report.setRepResult(repResult);
		report.setRepState(Integer.valueOf(repState));
		try {
			reportService.updateReportById(report);
		} catch (Exception e) {
			logger.error("saveReoprt error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
	@RequestMapping(value = "/removeReportById")
	@ResponseBody
	public ResModel removeReportById(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String reportId = getParam("reportId");
		if(ST.isNull(reportId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Report report = new Report();
		report.setId(Integer.valueOf(reportId));
		boolean bl = false;
		try {
			//删除报告之前先查询出要删除的数据，删除成功，把map_order_product表中的已上传报告数量减一
			Report reportDelBefor = reportService.selectReportByReportId(Integer.valueOf(reportId));
			reportDelBefor.setRepPdf("");
			reportService.updateReportById(reportDelBefor);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
	
	@RequestMapping(value = "/uploadReportPic")
	@ResponseBody
	public ResModel uploadReportPic(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String reportId = getParam("reportId");
		 //验证是否已上传了报告
		 Report report = new Report();
		 if(!ST.isNull(reportId)){
			 report = reportService.selectReportByReportId(Integer.valueOf(reportId));
			 if(!ST.isNull(report.getRepPdf())){
				 resModel.setMsg("此报告以上传");
				 resModel.setSuccess(false);
				 return resModel; 
			 }
		 }
		 try {
			 String filepath = FileUpload.upFileRename(file, request);
			 //更改report表的数据
			 report.setRepPdf(filepath);
			 report.setRepState(Constant.REPORT_STATUS3);
			 reportService.updateReportById(report);
			
			 
		} catch (Exception e) {
			logger.error("uploadReportPic error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
}
