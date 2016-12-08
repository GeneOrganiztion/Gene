package Mapper;

import po.Product;
import tk.mybatis.mapper.common.Mapper;

public interface ProductMapper extends Mapper<Product> {
   /* int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);*/
	
	int deleteByClassifyKey(Product record);
}