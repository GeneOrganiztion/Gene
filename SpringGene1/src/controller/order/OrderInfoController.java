package controller.order;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;

import controller.base.BaseController;


@Controller
@RequestMapping(value = "/orderInfo")
public class OrderInfoController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	
	private String REANDDE_ORDER_PAGE = "order/reAndDeOrder";
	private String DETECTION_ORDER_PAGE = "order/detectionOrder";
	private String COMPLETE_ORDER_PAGE = "order/completeOrder";
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private MapOrderProductService mapOrderProductService;
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/reAndDeOrderPage")
	public ModelAndView reAndDeOrderPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(REANDDE_ORDER_PAGE);
		return mv;
	}
	@RequestMapping(value="/detectionOrderPage")
	public ModelAndView detectionOrderPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(DETECTION_ORDER_PAGE);
		return mv;
	}
	@RequestMapping(value="/completeOrderPage")
	public ModelAndView completeOrderPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(COMPLETE_ORDER_PAGE);
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
		//查询订单状态
		Orders order = orderService.selectOrdersByOrderId(Integer.valueOf(orderId));
        try {
        	list = mapOrderProductService.selectOderAndProductByOrderId(Integer.valueOf(orderId));
        	for(OrderAndProductDTO orDTO: list){
        		orDTO.setOrdState(order.getOrdState());
        	}
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
	@RequestMapping(value = "/uploadReportPic")
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
			 if(list.size() >= proCount){
				 resModel.setSuccess(false);
				 resModel.setMsg("上传报告大于产品数量");
				 return resModel; 
			 }
		 }
		 try {
			 String filepath = FileUpload.upFileRename(file, request);
			 Report report = new Report();
			 report.setIsdelete(false);
			 report.setOrdId(mop.getOrdId());
			 report.setProId(mop.getProId());
			 report.setRepPdf(filepath);
			 report.setRepName(reportName);
			 //查询orders表的orderUser
			 Orders order =orderService.selectOrdersByOrderId(mop.getOrdId());
			 report.setUserId(order.getOrdUser());
			 report.setMapOrderProductId(Integer.valueOf(mapOrderProductId));
			 report.setRepResult(reportResult);
			 report.setCreateTime(new Date());
			 report.setLastModifiedTime(new Date());
			 Integer id = reportService.insertReportReturnId(report);
			 resModel.setReturnId(id);
			 //上传成功更改 map_order_product 中已上传报表的数量
			 mop.setReportCount(mop.getReportCount() + 1);
			 mapOrderProductService.updateMapOrderProduct(mop);
			 //更改订单表的状态
			 List<MapOrderProduct> listMp = mapOrderProductService.selectMapOrderProductByOrdId(mop.getOrdId());
			 //循环判断订单下的商品如果有一个商品上传的报告小于商品的数量不更改订单表的状态
			 boolean flag = true;
			 for(MapOrderProduct mopt: listMp){
				 if(mopt.getProCount() != mopt.getReportCount()){
					 flag = false;
					 break;
				 }
			 }
			 if(flag){
				 Orders orders = new Orders();
				 orders.setId(mop.getOrdId());
				 orders.setOrdState(Constant.ORDER_STATUS7);//已完成
				 orderService.updateOrderStatus(orders);
			 }
			 
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
		try {
			list = reportService.selectReportByParams(report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return list;
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
			bl = reportService.delReportByReportId(report);
			//删除成功更改 map_order_product 中已上传报表的数量
			if(bl){
				MapOrderProduct mapOrderProduct = mapOrderProductService.selectMapOrderProductById(reportDelBefor.getMapOrderProductId());
				mapOrderProduct.setReportCount(mapOrderProduct.getReportCount() - 1);
				mapOrderProductService.updateMapOrderProduct(mapOrderProduct);
			}
			
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
	
	@RequestMapping(value = "/reloadUploadRrportCount")
	@ResponseBody
	public ResModel reloadUploadRrportCount(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String mapOrderProductId = getParam("mapOrderProductId");
		if(ST.isNull(mapOrderProductId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Integer count = 0;
		try {
			MapOrderProduct mapOrderProduct = mapOrderProductService.selectMapOrderProductById(Integer.valueOf(mapOrderProductId));
			count = mapOrderProduct.getReportCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("reloadUploadRrportCount error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		resModel.setReturnId(count);
		return resModel;
	}
	@RequestMapping(value = "/saveDeliverProduct")
	@ResponseBody
	public ResModel saveDeliverProduct(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String orderId = getParam("orderId");
		String courierNum = getParam("courierNum");
		String courierName = getParam("courierName");
		String courierPhone = getParam("courierPhone");
		if(ST.isNull(orderId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Orders order = new Orders();
		order.setId(Integer.valueOf(orderId));
		order.setCourierNum(courierNum);
		order.setCourierName(courierName);
		order.setCourierPhone(courierPhone);
		order.setOrdState(Constant.ORDER_STATUS3);
		//order.setIsdelete(false);
		order.setLastModifiedTime(new Date());
		try {
			orderService.updateOrder(order);
		} catch (Exception e) {
			logger.error("saveDeliverProduct error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
	@RequestMapping(value = "/selectOrderStatus")
	@ResponseBody
	public ResModel selectOrderStatus(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			return resModel;
		}
		Orders order = orderService.selectOrdersByOrderId(Integer.valueOf(orderId));
		resModel.setReturnId(Integer.valueOf(order.getOrdState()));
		return resModel;
	}
	
	@RequestMapping(value = "/confirmRceliveProduct")
	@ResponseBody
	public ResModel confirmRceliveProduct(HttpServletRequest request,HttpServletResponse response){
		ResModel resModel = new ResModel();
		String orderId = getParam("orderId");
		if(ST.isNull(orderId)){
			resModel.setSuccess(false);
			return resModel;
		}
		Orders order = new Orders();
		order.setId(Integer.valueOf(orderId));
		order.setOrdState(Constant.ORDER_STATUS6);
		//order.setIsdelete(false);
		order.setLastModifiedTime(new Date());
		try {
			orderService.updateOrder(order);
		} catch (Exception e) {
			logger.error("confirmRceliveProduct error:" + e);
			resModel.setSuccess(false);
			return resModel;
		}
		resModel.setSuccess(true);
		return resModel;
	}
	
}
