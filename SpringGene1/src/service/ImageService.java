package service;

import java.util.List;

import po.Classify;
import po.Image;

public interface ImageService<T> {
	
	public boolean addImage(Image image) throws Exception;
	
	public List<T> selectbyProduct(Image image) throws Exception;
	
	public boolean deleteImage(Image image)throws Exception;
	
	
	public List<T> ImagebyProductId(Image image) throws Exception;
	
	public T selectbyId(Image image)throws Exception;
	
}
