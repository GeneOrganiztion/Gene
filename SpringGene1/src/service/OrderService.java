package service;

import java.util.List;
import java.util.Map;

import po.Orders;

import com.github.pagehelper.PageInfo;

public interface OrderService<T> {
	
	//根据userId或其他属性查看订单的详情，以及订单下的商品
	List<T> getOrderByUserId(Map map);
	
	boolean updateOrderStatus(Orders order);
	
	public PageInfo selectOrderByParams(Map map);
	
	public Orders selectOrdersByOrderId(Integer orderId);

	public Orders getOrderByOrderId(Integer orderId);
	
	public boolean updateOrder(Orders order)throws Exception;
	
	public int insertOrder(Map map)throws Exception;
	
	public boolean cancelOrder(Orders order)throws Exception;

}
