package service;

import java.util.List;

import po.Product;

public interface ProductService<T> {
	
	public List<T> selectAll(Product Product) throws Exception;
	
	public int delProduct(Product Product) throws Exception;
	
	public int saveProduct(Product Product) throws Exception;
	
	public boolean updateProduct(Product Product) throws Exception;
	

}
