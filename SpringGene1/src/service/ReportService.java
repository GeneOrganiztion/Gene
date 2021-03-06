package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Report;

public interface ReportService<T> {
	
	List<T> selectReportByParams(Report report)throws Exception;
	
	Report selectReportByReportId(Integer reportId)throws Exception;
	
	public boolean insertReport(Report report)throws Exception;
	
	public boolean delReportByRepUrl(Report report)throws Exception;
	
	public boolean delReportByReportId(Report report)throws Exception;
	
	public Integer insertReportReturnId(Report report)throws Exception;
	
	public Integer selectCountByMapOrderProductId(Integer mapOrderProductId)throws Exception;
	
	public boolean updateReportById(Report report)throws Exception;
	
	public PageInfo selectReportParams(Map map)throws Exception;

}
