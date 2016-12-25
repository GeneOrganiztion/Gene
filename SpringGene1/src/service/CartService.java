package service;

import po.Cart;
import po.MapProductCart;

public interface CartService<T> {
	
	public T selectCart(Cart cart) throws Exception;
	
	public boolean addProduct(MapProductCart mapproduct,int count) throws Exception;
	
	public boolean deleProduct(MapProductCart mapproduct)throws Exception;
	
	public int insertCart(Cart cart) throws Exception;

}
