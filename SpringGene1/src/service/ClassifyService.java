package service;

import java.sql.SQLException;
import java.util.List;

import po.Classify;

public interface ClassifyService<T> {
	
	public List<T> selectAll() throws Exception;
	
	public boolean delClassify(Classify cls) throws Exception;
	
	public boolean saveClassify(Classify cls) throws Exception;
	
	public boolean updateClassify(Classify cls) throws Exception;
	
}
