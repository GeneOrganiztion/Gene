package service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import po.Orders;
import service.OrderService;
import Mapper.OrdersMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrdersMapper ordesMapper;
    
    @Override
    public List<Orders> getOrderByUserId(Map map){
    	return ordesMapper.getOrderByUserId(map);
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
	@Override
	public boolean updateOrder(Orders order) throws Exception{
		try {
			ordesMapper.updateByPrimaryKeySelective(order);
		} catch (Exception e) {
			logger.error("updateOrderStatus error:" + e);
			return false;
		}
		return true;
	}
	
}
