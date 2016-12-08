package service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import controller.base.LoginController;
import Mapper.ClassifyMapper;
import Mapper.ProductMapper;
import po.Classify;
import po.Product;
import service.ClassifyService;

public class ClassifyServiceImpl implements ClassifyService {
	private static final Logger logger = LoggerFactory.getLogger(ClassifyServiceImpl.class);
	@Autowired
    private ClassifyMapper classifyMapper;
	@Autowired
	private ProductMapper productMapper;
	@Override
	public List<Classify> selectAll() throws Exception {
		Classify cls=new Classify();
		cls.setIsdelete(false);
		return classifyMapper.select(cls);
	}

	/**
	 *
	 * @author chenzhangsheng
	 * @date 2016-12-7 18:31:52
	 * 返回0表示删除成功 返回状态1表示删除失败,2表示还有以存在的商品未下架
	 */

	@Override
	public int delClassify(Classify cls) throws Exception {
		try{
		Product product=new Product();
		product.setClassifyId(cls.getId());
		product.setProOnline(true);
		if(productMapper.selectCount(product)>0){
			return 2;
		}
		product.setProOnline(false);
		//删除分类会删除分类下的商品
		productMapper.deleteByClassifyKey(product);
		classifyMapper.updateByPrimaryKeySelective(cls);
		}catch(Exception e){
			logger.info("classifyMapperdel.error="+e);
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	@Override
	public boolean saveClassify(Classify cls) throws Exception {
		try{
			classifyMapper.insertSelective(cls);
			}catch(Exception e){
			logger.info("classifyMappersave.error="+e);
			e.printStackTrace();
			return false;
		}
			return true;
	}

	@Override
	public boolean updateClassify(Classify cls) throws Exception {
		try{
			classifyMapper.updateByPrimaryKeySelective(cls);
			}catch(Exception e){
			logger.info("classifyMappersave.update="+e);
			e.printStackTrace();
			return false;
			}
			return true;
	}

}
