package Mapper;

import po.MapOrderProduct;

public interface Map_Order_ProductMapper {
    /* int deleteByPrimaryKey(Map_Order_ProductKey key); */

    int insert(MapOrderProduct record);

    int insertSelective(MapOrderProduct record);

    /* MapOrderProduct selectByPrimaryKey(Map_Order_ProductKey key); */

    int updateByPrimaryKeySelective(MapOrderProduct record);

    int updateByPrimaryKey(MapOrderProduct record);
}