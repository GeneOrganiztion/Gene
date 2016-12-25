package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Product;

public interface ProductService<T> {
	
	public List<T> selectbyClassify(Product Product) throws Exception;
	
	public List<T> selectAll(Product Product) throws Exception;
	public List<T> selectWebAll(Product Product) throws Exception;
	
	public int delProduct(Product Product) throws Exception;
	
	public int saveProduct(Product Product) throws Exception;
	
	public boolean updateProduct(Product Product) throws Exception;
	
	public T selectOne(Product Product)throws Exception;
	
	public PageInfo selectProductByParams(Map<String, Object> map) throws Exception;
	
	public boolean deleteProductIds(List<Integer> ids)throws Exception;

}
