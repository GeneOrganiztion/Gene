package Mapper;

import java.util.List;
import java.util.Map;

import po.Report;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ReportMapper extends Mapper<Report>,MySqlMapper<Report>{
    /* int deleteByPrimaryKey(ReportKey key); */

  /*  int insert(Report record);

    int insertSelective(Report record);

     Report selectByPrimaryKey(ReportKey key); 

    int updateByPrimaryKeySelective(Report record);*/
/*
    int updateByPrimaryKey(Report record);*/
	public Integer selectCountByMapOrderProductId(Integer mapOrderProductId)throws Exception;
	List<Report> selectReportParams(Map map)throws Exception;
}