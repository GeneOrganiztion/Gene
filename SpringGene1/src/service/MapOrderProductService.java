package service;

import java.util.List;

import po.MapOrderProduct;
import po.OrderAndProductDTO;

public interface MapOrderProductService {
	
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer oderId);
	
	public MapOrderProduct selectMapOrderProductById(Integer mapOrderProductId);

	public boolean updateMapOrderProduct(MapOrderProduct mapOrderProduct);
	
	public List<MapOrderProduct>  selectMapOrderProductByOrdId(Integer oderId); 
	
	public boolean saveMapOderPro(MapOrderProduct mapOrderProduct);
}
