package service;

import java.util.List;

import po.OrderAndProductDTO;

public interface MapOrderProductService {
	
	public List<OrderAndProductDTO> selectOderAndProductByOrderId(Integer oderId);

}
