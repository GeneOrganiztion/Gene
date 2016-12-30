package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import po.Orders;
import service.OrderService;
import utils.DateUtil;
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
	
	@Override
	public boolean cancelOrder(Orders order) throws Exception{
		order.setIsdelete(true);
		try {
			ordesMapper.updateByPrimaryKeySelective(order);
		} catch (Exception e) {
			logger.error("cancelOrder error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public int insertOrder(Map map) throws Exception {
		String prepayid=(String)map.get("package");
		String timestamp=(String)map.get("timeStamp");
		String nonceStr=(String)map.get("nonceStr");
		String finalsign=(String)map.get("paySign");
		String finalmoney=(String)map.get("finalmoney");
		String userId=(String)map.get("userId");
		int resultid=-1;
		try {
			Orders orders = new Orders();
			orders.setOrdPrice(Integer.valueOf(finalmoney));
			orders.setOrdNum(DateUtil.format(new Date()));
			orders.setOrdState("1");
			orders.setOrdPay("1");
			orders.setOrdUser(Integer.valueOf(userId));
			orders.setPrepayId(prepayid);
			orders.setTimestamp(timestamp);
			orders.setNonceStr(nonceStr);
			orders.setFinalsign(finalsign);
			orders.setIsdelete(false);
			orders.setCreateTime(new Date());
			orders.setLastModifiedTime(new Date());
			ordesMapper.insertUseGeneratedKeys(orders);
			resultid=orders.getId();
		} catch (Exception e) {
			logger.error("insertOrder error:" + e);
			return -1;
		}
		return resultid;
	
	}
	
}
