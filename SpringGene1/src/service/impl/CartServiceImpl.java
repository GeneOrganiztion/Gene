package service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import Mapper.CartMapper;
import Mapper.Map_Product_CartMapper;
import po.Cart;
import po.MapProductCart;
import service.CartService;
@Transactional
public class CartServiceImpl implements CartService {
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	@Autowired
    private CartMapper cartMapper;
	@Autowired
	private Map_Product_CartMapper mapper_Product_CartMapper;
	@Override
	public Cart selectCart(Cart cart) throws Exception {
		return cartMapper.selectbyUser(cart);
	}
	
	@Override
	public boolean addProduct(MapProductCart mapproduct,int count) throws Exception {
		try {
		mapproduct.setIsdelete(false);
		MapProductCart mapProductCart=mapper_Product_CartMapper.selectOne(mapproduct);
		//System.out.println("mapProductCartId="+mapProductCart.getId());
		if(null==mapProductCart){
			mapproduct.setProCount(count);
			mapper_Product_CartMapper.insertSelective(mapproduct);
		}else{
			mapproduct.setId(mapProductCart.getId());
			System.out.println("mapProductCart.getId()="+mapProductCart.getId());
			mapproduct.setProCount(mapProductCart.getProCount()+count);
			System.out.println("mapProductCart.getProCount()+mapproduct.getProCount()="+mapProductCart.getProCount()+mapproduct.getProCount());
			mapper_Product_CartMapper.updateByPrimaryKeySelective(mapproduct);	
		}
		}catch(Exception e){
			logger.info("cartMapperaddProduct"+e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean deleProduct(MapProductCart mapproduct) throws Exception {
		try{
		mapproduct.setIsdelete(true);
		mapper_Product_CartMapper.updateByPrimaryKeySelective(mapproduct);
		}catch(Exception e){
			logger.info("cartMapperdeleproduct"+e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
