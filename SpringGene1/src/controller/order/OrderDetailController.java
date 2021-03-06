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
import org.springframework.web.bind.annotation.ResponseBody;

import po.Orders;
import service.OrderService;
import utils.Constant;
import utils.ST;
import controller.base.BaseController;

@Controller
@RequestMapping("/order")
public class OrderDetailController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
	@Autowired
	private OrderService<Orders> orderService;
	/**
	 * 根据userId或其他属性查看订单的详情，以及订单下的商品
	 * @param request    userId:用户ID   ordState:订单状态（选填  不是必须的）
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneGetOrdersByUserId")
	@ResponseBody
	public List<Orders> getOrderByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Orders> listOrd = new ArrayList<Orders>();
		String userId = getParam("userId");
		String ordState = getParam("ordState");
		String orderId = getParam("orderId");
		
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			if(!ST.isNull(userId)){
				map.put("ordUser", userId);
			}
			if(!ST.isNull(ordState)){
				List<Integer> list = ST.StringToList(ordState);
				map.put("stateList", list);
			}
			if(!ST.isNull(orderId)){
				map.put("orderId", orderId);
			}
			listOrd = orderService.getOrderByUserId(map);
		} catch (Exception e) {
			logger.error("getOrderByUserId error:" + e);
		}
		return listOrd;
	}
	/**
	 *  根据oerderId 查询订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneGetOrdersByOrderId")
	@ResponseBody
	public Orders getOrdersByOrderId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String orderId = getParam("orderId");
		Orders order = new Orders();
		if(ST.isNull(orderId)){
			return 	order;
		}
		try {
			order = orderService.getOrderByOrderId(Integer.valueOf(orderId));
		} catch (Exception e) {
			logger.error("getOrderByUserId error:" + e);
		}
		return order;
	}
	/**
	 * 根据订单Id更改订单状态
	 * @param request   order_id：订单ID         ord_state：订单状态
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneUpdateOrderStatus")
	@ResponseBody
	public boolean updateOrderStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String orderId = getParam("orderId");
		String ordState = getParam("ordState");
		Orders order = new Orders();
		try {
			if(!ST.isNull(orderId)){
				order.setId(Integer.valueOf(orderId));
				order.setOrdState(ordState);
				//order.setIsdelete(false);
				order.setLastModifiedTime(new Date());
				return orderService.updateOrderStatus(order);
			}
		} catch (Exception e) {
			logger.error("updateOrderStatus error :" + e);
		}
		return false;
	}
	/**
	 * 根据orderID插入买家发给卖家的物流信息
	 * @param request     orderId：订单Id（必须传入）     userCourierNum：买家寄给卖家的物流单号      userCourierName：买家寄给卖家的物流名称
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneInsertCourierinfo")
	@ResponseBody
	public boolean insertCourierinfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String orderId = getParam("orderId");
		String userCourierNum = getParam("userCourierNum");
		String userCourierName = getParam("userCourierName");
		Orders order = new Orders();
		if(!ST.isNull(orderId)){
			order.setId(Integer.valueOf(orderId));
			order.setUserCourierName(userCourierName);
			order.setUserCourierNum(userCourierNum);
			order.setOrdState(Constant.ORDER_STATUS5);
		}else{
			logger.error("insertCourierinfo orderId is null ");
			return false;
		}
		try {
			return orderService.updateOrder(order);
		} catch (Exception e) {
			logger.error("insertCourierinfo error :" + e);
			return false;
		}
	}
	
}
