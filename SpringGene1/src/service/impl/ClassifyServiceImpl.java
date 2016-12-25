package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.ClassifyMapper;
import Mapper.ProductMapper;
import po.Classify;
import po.Product;
import service.ClassifyService;


@Transactional
public class ClassifyServiceImpl implements ClassifyService {
	private static final Logger logger = LoggerFactory.getLogger(ClassifyServiceImpl.class);
	@Autowired
    private ClassifyMapper classifyMapper;
	@Autowired
	private ProductMapper productMapper;
	@Override
	public List<Classify> selectAll(Classify cls) throws Exception {
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
		cls.setIsdelete(true);
		int statcode=0;
		try{
		Product product=new Product();
		product.setClassifyId(cls.getId());
		product.setProOnline(true);
		if(productMapper.selectCount(product)>0){
			statcode=2;
			return statcode;
		}
		product.setProOnline(false);
		product.setIsdelete(true);
		//删除分类会删除分类下的商品
		productMapper.deleteByClassifyKey(product);
		classifyMapper.updateByPrimaryKeySelective(cls);
		}catch(Exception e){
			logger.info("classifyMapperdel.error="+e);
			e.printStackTrace();
			statcode=1;
		}
		return statcode;
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
	@Override
    public PageInfo selectClassifyParams(Map map){
    	PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Classify> list = new ArrayList<Classify>();
		try {
			list = classifyMapper.selectClassifyParams(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("selectClassifyParams error:" + e);
		}
	    PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
    }
	@Override
	public Integer insertOneClassifyReturnId(Classify cls)throws Exception{
		classifyMapper.insertUseGeneratedKeys(cls);
		return cls.getId();
	}
	@Override
	public boolean delOneClassifyById(Classify cls)throws Exception{
		try {
			classifyMapper.delete(cls);
		} catch (Exception e) {
			logger.error("delOneClassifyById error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public List<Classify> selectTwoClassify(Classify cls) throws Exception{
		cls.setIsdelete(false);
		return classifyMapper.select(cls);
	}
	@Override
	public List<Classify> selectAllOneClassify(Classify cls) throws Exception{
		cls.setIsdelete(false);
		return classifyMapper.select(cls);
	}
	@Override
	public Classify selectOneClassify(Classify cls) throws Exception{
		cls.setIsdelete(false);
		return classifyMapper.selectOne(cls);
	}
}
