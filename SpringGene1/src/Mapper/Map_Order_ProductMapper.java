package Mapper;

import po.Map_Order_Product;
import po.Map_Order_ProductKey;

public interface Map_Order_ProductMapper {
    int deleteByPrimaryKey(Map_Order_ProductKey key);

    int insert(Map_Order_Product record);

    int insertSelective(Map_Order_Product record);

    Map_Order_Product selectByPrimaryKey(Map_Order_ProductKey key);

    int updateByPrimaryKeySelective(Map_Order_Product record);

    int updateByPrimaryKey(Map_Order_Product record);
}