package service;

import java.util.List;

import po.Report;

public interface ReportService<T> {
	
	List<T> selectReportByParams(Report report);

}