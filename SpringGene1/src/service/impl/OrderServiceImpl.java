package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import Mapper.OrderMapper;
import po.Order;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrderMapper ordeMapper;
    
    public List<Order> getOrderByUserId(Order order){
    	return ordeMapper.getOrderByUserId(order);
    }
    public boolean updateOrderStatus(Order order){
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
