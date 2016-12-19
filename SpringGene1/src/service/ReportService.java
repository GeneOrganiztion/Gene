package service;

import java.util.List;

import po.Report;

public interface ReportService<T> {
	
	List<T> selectReportByParams(Report report);
	
	public boolean insertReport(Report report);
	
	public boolean delReportByRepUrl(Report report);

}
