package Mapper;

import po.Order;

public interface OrderMapper {
    /* int deleteByPrimaryKey(OrderKey key); */

    int insert(Order record);

    int insertSelective(Order record);

    /* Order selectByPrimaryKey(OrderKey key); */

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}