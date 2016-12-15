package service.impl;

import java.util.Date;
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
	public List<Product> selectbyClassify(Product product) throws Exception {
		 product.setIsdelete(false);
		 product.setProOnline(true);
		return productMapper.selectbyClassify(product);
	}
	
	@Override
	public List<Product> selectAll(Product product) throws Exception {
		 product.setIsdelete(false);
		 product.setProOnline(true);
		return productMapper.select(product);
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
		Product.setIsdelete(false);
		Product.setCreateTime(new Date());
		Product.setLastModifiedTime(new Date());
		int newproductid=-1;
		try{
			productMapper.insertUseGeneratedKeys(Product);
			newproductid=Product.getId();
		}catch(Exception e){
			logger.info("productMappersave"+e);
		}
		return newproductid;
	}

	
	@Override
	public boolean updateProduct(Product Product) throws Exception {
		boolean flag=false;
		try{
			productMapper.updateByPrimaryKeySelective(Product);
			flag=true;
		}catch(Exception e){
			logger.info("productMappersave"+e);
		}
		return flag;
	}

	@Override
	public Product selectOne(Product Product) throws Exception {

		return productMapper.selectByPrimaryKey(Product);
	}
	
}
