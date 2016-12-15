package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import po.Product;
import service.ProductService;
import Mapper.AdminMapper;
import Mapper.ProductMapper;

@Transactional
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> selectAll(Product product) throws Exception {
		 product.setIsdelete(false);
		 product.setProOnline(true);
		return productMapper.selectbyClassify(product);
	}

	@Override
	public int delProduct(Product Product) throws Exception {
		int statcode=0;
		try{
			productMapper.updateByPrimaryKeySelective(Product);
		}catch(Exception e){
			logger.info("productMapperdel"+e);
		}
		return 0;
	}

	@Override
	public int saveProduct(Product Product) throws Exception {
		int newproductid=-1;
		try{
			newproductid=productMapper.insert(Product);
			System.out.println("productid="+newproductid);
		}catch(Exception e){
			logger.info("productMappersave"+e);
		}
		return newproductid;
	}

	@Override
	public boolean updateProduct(Product Product) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
}
