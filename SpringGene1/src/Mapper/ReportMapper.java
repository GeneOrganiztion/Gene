package Mapper;

import po.Orders;
import po.Report;
import tk.mybatis.mapper.common.Mapper;

public interface ReportMapper extends Mapper<Report>{
    /* int deleteByPrimaryKey(ReportKey key); */

    int insert(Report record);

    int insertSelective(Report record);

    /* Report selectByPrimaryKey(ReportKey key); */

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}