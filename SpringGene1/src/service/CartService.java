package service;

import java.util.List;

import po.Cart;
import po.MapProductCart;

public interface CartService<T> {
	
	public T selectCart(Cart cart) throws Exception;
	
	public boolean addProduct(MapProductCart mapproduct,int count) throws Exception;
	
	public int insertCart(Cart cart) throws Exception;
	
	public T selectCartByUserId(Cart cart) throws Exception;

	public boolean deleProducts(List<Integer> ids) throws Exception;

}
