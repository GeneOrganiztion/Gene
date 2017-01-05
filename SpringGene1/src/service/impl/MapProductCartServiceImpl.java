package service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import Mapper.ClassifyMapper;
import Mapper.Map_Product_CartMapper;
import po.MapProductCart;
import service.MapProductCartService;

public class MapProductCartServiceImpl implements MapProductCartService {
	private static final Logger logger = LoggerFactory.getLogger(MapProductCartServiceImpl.class);
	
	@Autowired
    private Map_Product_CartMapper mapProductCartMapper;

	@Override
	public boolean updateMapProductCart(MapProductCart mapProductCart) {
		try{
			mapProductCartMapper.updateByPrimaryKeySelective(mapProductCart);
		}catch(Exception e){
			logger.error("updateMapProductCart error:" + e);
			return false;
		}
		return true;
	}

}
