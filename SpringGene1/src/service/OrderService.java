package service;

import java.util.List;

import po.Order;

public interface OrderService<T> {
	
	//根据userId或其他属性查看订单的详情，以及订单下的商品
	List<T> getOrderByUserId(Order order);
	
	boolean updateOrderStatus(Order order);

}
