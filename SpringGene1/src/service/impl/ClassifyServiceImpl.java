package service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import controller.base.LoginController;
import Mapper.ClassifyMapper;
import po.Classify;
import service.ClassifyService;

public class ClassifyServiceImpl implements ClassifyService {
	private static final Logger logger = LoggerFactory.getLogger(ClassifyServiceImpl.class);
	@Autowired
    private ClassifyMapper classifyMapper;

	@Override
	public List<Classify> selectAll() throws Exception {
		Classify cls=new Classify();
		cls.setIsdelete(false);
		return classifyMapper.select(cls);
	}

	@Override
	public boolean delClassify(Classify cls) throws Exception {
		try{
		classifyMapper.updateByPrimaryKeySelective(cls);
		}catch(Exception e){
			logger.info("classifyMapperdel.error="+e);
			e.printStackTrace();
			return false;
		}
		return true;
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
