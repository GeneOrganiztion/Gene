package service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import po.Admin;
import po.MapOrderProduct;
import po.OrderAndProductDTO;
import service.MapOrderProductService;
import utils.MD5Util;
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
	@Override
	public boolean updateMapOrderProduct(MapOrderProduct mapOrderProduct){
		try {
			mapOrderProductMapper.updateByPrimaryKey(mapOrderProduct);
		} catch (Exception e) {
			logger.error("updateMapOrderProduct error:" + e);
			return false;
		}
		return true;
		
	}
	
	

	@Override
	public List<MapOrderProduct> selectMapOrderProductByOrdId(Integer oderId) {
		MapOrderProduct mapOrderProduct = new MapOrderProduct();
		mapOrderProduct.setOrdId(oderId);
		mapOrderProduct.setIsdelete(false);
		return mapOrderProductMapper.select(mapOrderProduct);
	}
	
	@Override
	public boolean saveMapOderPro(MapOrderProduct mapOrderProduct){
		Date data=new Date();
		mapOrderProduct.setIsdelete(false);
		mapOrderProduct.setCreateTime(data);
		mapOrderProduct.setLastModifiedTime(data);
		try {
			mapOrderProductMapper.insertUseGeneratedKeys(mapOrderProduct);
		} catch (Exception e) {
			logger.error("saveMapOderPro error:" + e);
			return false;
		}
		return true;
	}
}
