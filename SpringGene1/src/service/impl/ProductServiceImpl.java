package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import po.Admin;
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
	public List<Product> selectWebAll(Product product) throws Exception {
		 product.setIsdelete(false);
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
		Product.setIsdelete(false);
		return productMapper.SelectProductAndImage(Product);
	}

	@Override
	public PageInfo selectProductByParams(Map map) throws Exception {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Product> list = productMapper.selectProductByParams(map);
	    PageInfo page = new PageInfo(list);
		return page;
	}

	@Override
	public boolean deleteProductIds(List ids) throws Exception {
		try {
			productMapper.deleteProductByIds(ids);
		} catch (Exception e) {
			logger.error("AdminServiceImpl deleteAdminByIds error:" + e);
			return false;
		}
		return true;
	}
	
}
