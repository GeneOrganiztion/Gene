package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Classify;
import po.Report;

public interface ClassifyService<T> {
	
	public List<T> selectAll(Classify cls) throws Exception;
	
	public int delClassify(Classify cls) throws Exception;
	
	public boolean saveClassify(Classify cls) throws Exception;
	
	public boolean updateClassify(Classify cls) throws Exception;
	
	public PageInfo selectClassifyParams(Map map);
	
	public Integer insertOneClassifyReturnId(Classify cls)throws Exception;
	
	public boolean delOneClassifyById(Classify cls)throws Exception;
	
	public List<T> selectTwoClassify(Classify cls) throws Exception;
	
	public List<T> selectAllOneClassify(Classify cls) throws Exception;
	
	public Classify selectOneClassify(Classify cls) throws Exception;
	
}
