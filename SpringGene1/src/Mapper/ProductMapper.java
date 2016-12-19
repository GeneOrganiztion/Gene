package Mapper;

import java.util.List;
import java.util.Map;

import po.Admin;
import po.Product;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ProductMapper extends Mapper<Product>,MySqlMapper<Product> {
   /* int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);*/
	
	int deleteByClassifyKey(Product product);
	public List<Product> selectbyClassify(Product product);
	
	List<Product> selectProductByParams(Map<String, Object> map);
}