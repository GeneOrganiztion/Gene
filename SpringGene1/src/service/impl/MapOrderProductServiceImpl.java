package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import po.MapOrderProduct;
import po.OrderAndProductDTO;
import service.MapOrderProductService;
import Mapper.Map_Order_ProductMapper;

@Transactional
public class MapOrderProductServiceImpl implements MapOrderProductService {

	private static final Logger logger = LoggerFactory.getLogger(MapOrderProductServiceImpl.class);
	
	@Autowired
	private Map_Order_ProductMapper mapOrderProductMapper;
	
	@Override
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer oderId) {
		return mapOrderProductMapper.selectOderAndProductByOrderId(oderId);
	}

	@Override
	public MapOrderProduct selectMapOrderProductById(Integer mapOrderProductId){
		MapOrderProduct mapOrderProduct = new MapOrderProduct();
		mapOrderProduct.setId(mapOrderProductId);
		mapOrderProduct.setIsdelete(false);
		return mapOrderProductMapper.selectOne(mapOrderProduct);
		
	}
}
