package Mapper;

import java.util.List;
import java.util.Map;

import po.Classify;
import po.Report;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ClassifyMapper extends Mapper<Classify>,MySqlMapper<Classify>{
  /*  int deleteByPrimaryKey(Integer id);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);*/
	
	List<Classify> selectClassifyParams(Map map) throws Exception;
}