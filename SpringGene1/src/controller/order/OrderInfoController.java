package controller.order;

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

import controller.base.BaseController;
import po.MapOrderProduct;
import po.OrderAndProductDTO;
import po.Orders;
import po.Report;
import po.ResModel;
import service.MapOrderProductService;
import service.OrderService;
import service.ReportService;
import utils.Constant;
import utils.ST;


@Controller
@RequestMapping(value = "/orderInfo")
public class OrderInfoController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	
	private String ORDER_PAGE = "order/orderList";
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private MapOrderProductService mapOrderProductService;
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/orderPage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ORDER_PAGE);
		return mv;
	}
	
	@RequestMapping(value = "/seletcOrder")
	@ResponseBody
	public PageInfo selectOrder(HttpServletRequest request,HttpServletResponse response){
		String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo pageInfo = new PageInfo();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String orderId = getParam("orderId");
            String ordState = getParam("ordState");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
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
            map.put("orderId", orderId);
            map.put("ordState", ordState);
            pageInfo= (PageInfo)orderService.selectOrderByParams(map);
		} catch (Exception e) {
			logger.error("seletcOrder error:" + e);
		}
        return pageInfo;
	}
	@RequestMapping(value = "/selectOrderAndPrductByOrderId")
	@ResponseBody
	public List<OrderAndProductDTO> selectOrderAndPrductByOrderId(HttpServletRequest request,HttpServletResponse response){
		List<OrderAndProductDTO> list = new ArrayList<OrderAndProductDTO>();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			return list;
		}
        try {
        	list = mapOrderProductService.selectOderAndProductByOrderId(Integer.valueOf(orderId));
		} catch (Exception e) {
			logger.error("selectOrderAndPrductByOrderId error:" + e);
		}
        return list;
	}
	@RequestMapping(value = "/selectOrderDetail")
	@ResponseBody
	public Orders selectOrderDetail(HttpServletRequest request,HttpServletResponse response){
		Orders order = new Orders();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			return order;
		}
        try {
        	order = orderService.selectOrdersByOrderId(Integer.valueOf(orderId));
		} catch (Exception e) {
			logger.error("selectOrderDetail error:" + e);
		}
        return order;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadReportPic", method = RequestMethod.POST)
	@ResponseBody
	public ResModel uploadReportPic(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		 ResModel resModel = new ResModel();
		 String mapOrderProductId= getParam("mapOrderProductId");
		 String reportName = getParam("reportName");
		 String reportResult = getParam("reportResult");
		 if(ST.isNull(mapOrderProductId)){
			 resModel.setSuccess(false);
			 return resModel;
		 }
		 //验证上传报告是否大于产品数量
		 MapOrderProduct mop = mapOrderProductService.selectMapOrderProductById(Integer.valueOf(mapOrderProductId));
		 int proCount = mop.getProCount();
		 Report rt = new Report();
		 rt.setIsdelete(false);
		 rt.setMapOrderProductId(mop.getId());
		 List<Report> list = reportService.selectReportByParams(rt);
		 if(list != null && list.size() > 0){
			 if(list.size() > proCount){
				 resModel.setSuccess(false);
				 resModel.setMsg("上传报告大于产品数量");
				 return resModel; 
			 }
		 }
		 try {
			 String filepath = FileUpload.uploadFile(file, request);
			 Report report = new Report();
			 report.setIsdelete(false);
			 report.setOrdId(mop.getOrdId());
			 report.setProId(mop.getProId());
			 report.setRepPdf(filepath);
			 report.setRepName(reportName);
			 report.setUserId(this.getUserId());
			 report.setMapOrderProductId(Integer.valueOf(mapOrderProductId));
			 report.setRepResult(reportResult);
			 boolean bl = reportService.insertReport(report);
			 if(!bl){
				 resModel.setSuccess(false);
				 return resModel;
			 }
			 //更改    mapOrderProduct 表的report_is_upload字段为已上传报告    1:未上传   2：已上传
			 mop.setReportIsUpload(2); 
			 mapOrderProductService.updateMapOrderProduct(mop);
			 
		} catch (Exception e) {
			logger.error("uploadReportPic error" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	@RequestMapping(value = "/selectOrderByMapOrderProductId")
	@ResponseBody
	public List<Report> selectOrderByMapOrderProductId(HttpServletRequest request,HttpServletResponse response){
		Report report = new Report();
		List<Report> list = new ArrayList<Report>();
		String mapOrderProductId = getParam("mapOrderProductId");
		if(ST.isNull(mapOrderProductId)){
			return 	list;
		}
		report.setMapOrderProductId(Integer.valueOf(mapOrderProductId));
		list = reportService.selectReportByParams(report);
		return list;
	}
	
	@RequestMapping(value = "/removeOrderByPdf")
	@ResponseBody
	public ResModel removeOrderPdf(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String filename = getParam("filename");
		String url = Constant.OSS_ENDPOINT+filename;
		if(ST.isNull(url)){
			resModel.setSuccess(false);
			return resModel;
		}
		Report report = new Report();
		report.setRepPdf(url);
		boolean bl = reportService.delReportByRepUrl(report);
		if(!bl){
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
}
