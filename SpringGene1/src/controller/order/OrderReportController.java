package controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.BaseController;
import po.Orders;
import po.Report;
import service.OrderService;
import service.ReportService;

@Controller
@RequestMapping("/report")
public class OrderReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderReportController.class);
	@Autowired
	private ReportService reportService;
	
	
	/**
	 * 根据userId或其他属性查看订单的详情，以及订单下的商品
	 * @param request    userId:用户ID   ordState:订单状态（选填  不是必须的）
	 * @param response
	 * @return
	 * @throws Exception
	 *  */
	@RequestMapping(value = "/phoneGetReportByUserId")
	@ResponseBody
	public List<Report> GetReportByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Report> listRop = new ArrayList<Report>();
		String userId = getParam("userId");
		try {
			Report report = new Report();
			report.setUserId(Integer.valueOf(userId));
			listRop = reportService.selectReportByParams(report);
		} catch (Exception e) {
			logger.error("GetReportByUserId error:" + e);
		}
		return listRop;
	}
}
