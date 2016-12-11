package Mapper;

import java.util.List;

import po.Orders;
import tk.mybatis.mapper.common.Mapper;

public interface OrdersMapper extends Mapper<Orders>{
    /* int deleteByPrimaryKey(OrderKey key); */

   /* int insert(Orders record);

    int insertSelective(Orders record);
*/
    /* Order selectByPrimaryKey(OrderKey key); */

   /* int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);*/
    
    List<Orders> getOrderByUserId(Orders order);
}