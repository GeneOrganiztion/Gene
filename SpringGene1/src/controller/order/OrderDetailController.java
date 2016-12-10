package controller.order;

import java.util.ArrayList;
import java.util.Date;
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
import po.Order;
import service.OrderService;
import service.impl.OrderServiceImpl;
import utils.ST;

@Controller
@RequestMapping("/order")
public class OrderDetailController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
	@Autowired
	private OrderService<Order> orderService;
	/**
	 * 根据userId或其他属性查看订单的详情，以及订单下的商品
	 * @param request    user_id:用户ID   ord_state:订单状态（选填  不是必须的）
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneGetOrderSByUserId", method = RequestMethod.GET)
	@ResponseBody
	public List<Order> getOrderByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Order> listOrd = new ArrayList<Order>();
		String userId = getParam("user_id");
		String ordState = getParam("ord_state");
		try {
			Order order = new Order();
			order.setOrdUser(Integer.valueOf(userId));
			order.setOrdState(ordState);
			listOrd = orderService.getOrderByUserId(order);
		} catch (Exception e) {
			logger.error("getOrderByUserId error:" + e);
		}
		return listOrd;
	}
	/**
	 * 根据订单Id更改订单状态
	 * @param request   order_id：订单ID         ord_state：订单状态
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phoneUpdateOrderStatus", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateOrderStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String order_id = getParam("order_id");
		String ordState = getParam("ord_state");
		Order order = new Order();
		try {
			if(ST.getDefaultToInt(order_id, -1) != -1){
				order.setId(Integer.valueOf(order_id));
				order.setOrdState(ordState);
				order.setIsdelete(false);
				order.setLastModifiedTime(new Date());
				return orderService.updateOrderStatus(order);
			}
		} catch (Exception e) {
			logger.error("updateOrderStatus error :" + e);
		}
		return false;
	}
}
