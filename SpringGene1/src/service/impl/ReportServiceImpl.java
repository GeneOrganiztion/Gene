package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import Mapper.ReportMapper;
import po.Report;
import service.ReportService;
@Transactional
public class ReportServiceImpl implements ReportService {
	@Autowired
    private ReportMapper reportMapper;
	
	public List<Report> selectReportByParams(Report report){
		return reportMapper.select(report);
	}
}
