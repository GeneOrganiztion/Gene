package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import Mapper.ImageMapper;
import po.Image;
import service.ImageService;

public class ImageServiceImpl implements ImageService{

	private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	@Autowired
    private ImageMapper imageMapper;
	@Override
	public boolean addImage(Image image) throws Exception {
		boolean flag=false;
		try{
			imageMapper.insertSelective(image);
			flag=true;
		}catch(Exception e){
			logger.info("addImage="+e);
		}
		return flag;
	}
	@Override
	public List<Image> selectbyProduct(Image image) throws Exception {
		return imageMapper.select(image);
	}
	@Override
	public boolean deleteImage(Image image) throws Exception {
		boolean flag=false;
		try{
			imageMapper.delete(image);
			flag=true;
		}catch(Exception e){
			logger.info("deleteImage="+e);
		}
		return flag;
	}
	
	

}
