package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import Mapper.OrdersMapper;
import po.Orders;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrdersMapper ordeMapper;
    
    public List<Orders> getOrderByUserId(Orders order){
    	System.out.println("mapperSize="+ordeMapper.getOrderByUserId(order).size());
    	return ordeMapper.getOrderByUserId(order);
    }
    public boolean updateOrderStatus(Orders order){
    	try {
    		ordeMapper.updateByPrimaryKeySelective(order);
    		//ordeMapper.updateByPrimaryKey(order);
		} catch (Exception e) {
			logger.error("updateOrderStatus error:" + e);
			return false;
		}
    	return true;
    }

}
