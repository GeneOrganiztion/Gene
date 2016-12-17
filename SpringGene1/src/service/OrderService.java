package service;

import java.util.List;
import java.util.Map;

import po.Orders;

import com.github.pagehelper.PageInfo;

public interface OrderService<T> {
	
	//根据userId或其他属性查看订单的详情，以及订单下的商品
	List<T> getOrderByUserId(Orders order);
	
	boolean updateOrderStatus(Orders order);
	
	public PageInfo selectOrderByParams(Map map);

}
