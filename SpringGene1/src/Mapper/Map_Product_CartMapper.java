package Mapper;

import po.Map_Product_Cart;
import po.Map_Product_CartKey;

public interface Map_Product_CartMapper {
    int deleteByPrimaryKey(Map_Product_CartKey key);

    int insert(Map_Product_Cart record);

    int insertSelective(Map_Product_Cart record);

    Map_Product_Cart selectByPrimaryKey(Map_Product_CartKey key);

    int updateByPrimaryKeySelective(Map_Product_Cart record);

    int updateByPrimaryKey(Map_Product_Cart record);
}