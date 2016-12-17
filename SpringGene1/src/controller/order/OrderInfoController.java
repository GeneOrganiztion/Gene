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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import po.OrderAndProductDTO;
import service.MapOrderProductService;
import service.OrderService;
import utils.ST;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;


@Controller
@RequestMapping(value = "/orderInfo")
public class OrderInfoController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	
	private String ORDER_PAGE = "order/orderList";
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private MapOrderProductService mapOrderProductService;
	
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

}
