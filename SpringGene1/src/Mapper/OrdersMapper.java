package Mapper;

import java.util.List;
import java.util.Map;

import po.Orders;
import po.Product;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface OrdersMapper extends Mapper<Orders>,MySqlMapper<Orders>{
    /* int deleteByPrimaryKey(OrderKey key); */

   /* int insert(Orders record);

    int insertSelective(Orders record);
*/
    /* Order selectByPrimaryKey(OrderKey key); */

   /* int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);*/
    
    List<Orders> getOrderByUserId(Map map);
    
    List<Orders> selectOrderByParams(Map map);
    
    Orders getOrderByOrderId(Integer orderId);
}