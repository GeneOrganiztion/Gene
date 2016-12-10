package Mapper;

import java.util.List;

import po.Order;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order>{
    /* int deleteByPrimaryKey(OrderKey key); */

    int insert(Order record);

    int insertSelective(Order record);

    /* Order selectByPrimaryKey(OrderKey key); */

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    List<Order> getOrderByUserId(Order order);
}