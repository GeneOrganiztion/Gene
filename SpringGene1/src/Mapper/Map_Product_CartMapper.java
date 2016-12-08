package Mapper;

import po.MapProductCart;

public interface Map_Product_CartMapper {
    /* int deleteByPrimaryKey(Map_Product_CartKey key); */

    int insert(MapProductCart record);

    int insertSelective(MapProductCart record);

    /* MapProductCart selectByPrimaryKey(Map_Product_CartKey key); */

    int updateByPrimaryKeySelective(MapProductCart record);

    int updateByPrimaryKey(MapProductCart record);
}