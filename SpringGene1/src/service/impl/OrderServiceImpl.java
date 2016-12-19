package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.OrdersMapper;
import po.Orders;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrdersMapper ordesMapper;
    
    @Override
    public List<Orders> getOrderByUserId(Orders order){
    	System.out.println("mapperSize="+ordesMapper.getOrderByUserId(order).size());
    	return ordesMapper.getOrderByUserId(order);
    }
    @Override
    public boolean updateOrderStatus(Orders order){
    	try {
    		ordesMapper.updateByPrimaryKeySelective(order);
    		//ordeMapper.updateByPrimaryKey(order);
		} catch (Exception e) {
			logger.error("updateOrderStatus error:" + e);
			return false;
		}
    	return true;
    }
    @Override
    public PageInfo selectOrderByParams(Map map){
    	PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Orders> list = ordesMapper.selectOrderByParams(map);
	    PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
    }
	@Override
	public Orders selectOrdersByOrderId(Integer orderId) {
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setIsdelete(false);
		return ordesMapper.selectOne(orders);
	}
	@Override
	public Orders getOrderByOrderId(Integer orderId){
		return ordesMapper.getOrderByOrderId(orderId);
	}
	
	
}
