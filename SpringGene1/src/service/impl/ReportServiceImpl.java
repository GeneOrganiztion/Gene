package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import Mapper.ReportMapper;
import po.Report;
import service.ReportService;
@Transactional
public class ReportServiceImpl implements ReportService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	@Autowired
    private ReportMapper reportMapper;
	
	public List<Report> selectReportByParams(Report report){
		report.setIsdelete(false);
		return reportMapper.select(report);
	}
	
	public boolean insertReport(Report report){
		try {
			reportMapper.insertSelective(report);
		} catch (Exception e) {
			logger.error("insertReport error:" + e);
			return false;
		}
		return true;
	}
}
