package controller.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Report;
import service.ReportService;
import controller.base.BaseController;
import controller.order.OrderDetailController;

@Controller
@RequestMapping("/report")
public class ReportInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
	@Autowired
	private ReportService<Report> reportService;
	
	/**
	 * 根据商品id和ordId查询报告
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneSelectReportByParams")
	@ResponseBody
	public List<Report> selectReportByParams(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String orId = getParam("orId");
		String proId = getParam("proId");
		List<Report> list = new ArrayList<Report>();
		Report report = new Report();
		try {
			report.setOrdId(Integer.valueOf(orId));
			report.setProId(Integer.valueOf(proId));
			report.setIsdelete(false);
			list = reportService.selectReportByParams(report);
		} catch (Exception e) {
			logger.error("selectReportByParams error:" + e);
		}
		return list;
	}
}
