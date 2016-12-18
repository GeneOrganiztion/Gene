package service;

import java.util.List;

import po.MapOrderProduct;
import po.OrderAndProductDTO;

public interface MapOrderProductService {
	
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer oderId);
	
	public MapOrderProduct selectMapOrderProductById(Integer mapOrderProductId);

}
