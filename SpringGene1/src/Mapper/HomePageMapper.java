package Mapper;

import java.util.List;
import java.util.Map;

import po.Classify;
import po.HomePage;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
public interface HomePageMapper extends Mapper<HomePage>,MySqlMapper<HomePage> {
	 /*   int deleteByPrimaryKey(Integer id);

	    int insert(HomePage record);

	    int insertSelective(HomePage record);

	    HomePage selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(HomePage record);

	    int updateByPrimaryKey(HomePage record);*/
		
		List<HomePage> selectHomePageParams(Map map) throws Exception;
}