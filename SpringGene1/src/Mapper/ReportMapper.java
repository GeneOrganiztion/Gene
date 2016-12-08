package Mapper;

import po.Report;

public interface ReportMapper {
    /* int deleteByPrimaryKey(ReportKey key); */

    int insert(Report record);

    int insertSelective(Report record);

    /* Report selectByPrimaryKey(ReportKey key); */

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}