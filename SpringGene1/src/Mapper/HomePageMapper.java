package Mapper;

import java.util.List;
import java.util.Map;

import po.HomePage;
import tk.mybatis.mapper.common.MySqlMapper;
public interface HomePageMapper extends MySqlMapper<HomePage> {
	 /*   int deleteByPrimaryKey(Integer id);

	    int insert(HomePage record);

	    int insertSelective(HomePage record);

	    HomePage selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(HomePage record);

	    int updateByPrimaryKey(HomePage record);*/
		
		List<HomePage> selectHomePageParams(Map map) throws Exception;
}