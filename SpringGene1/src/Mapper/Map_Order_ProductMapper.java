package Mapper;

import java.util.List;

import po.MapOrderProduct;
import po.OrderAndProductDTO;
import po.Orders;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface Map_Order_ProductMapper extends Mapper<MapOrderProduct>,MySqlMapper<MapOrderProduct>{
    /* int deleteByPrimaryKey(Map_Order_ProductKey key); */

   /* int insert(MapOrderProduct record);

    int insertSelective(MapOrderProduct record);

     MapOrderProduct selectByPrimaryKey(Map_Order_ProductKey key); 

    int updateByPrimaryKeySelective(MapOrderProduct record);

    int updateByPrimaryKey(MapOrderProduct record);*/
    
    public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer oderId); 
}