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
	@Override
	public List<Report> selectReportByParams(Report report)throws Exception{
		report.setIsdelete(false);
		return reportMapper.select(report);
	}
	@Override
	public boolean insertReport(Report report)throws Exception{
		try {
			reportMapper.insertSelective(report);
		} catch (Exception e) {
			logger.error("insertReport error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public boolean delReportByRepUrl(Report report)throws Exception{
		try {
			reportMapper.delete(report);
		} catch (Exception e) {
			logger.error("delReportByRepUrl error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public Integer insertReportReturnId(Report report)throws Exception{
		reportMapper.insertUseGeneratedKeys(report);
		return report.getId();
	}
	@Override
	public boolean delReportByReportId(Report report)throws Exception{
		try {
			reportMapper.delete(report);
		} catch (Exception e) {
			logger.error("delReportByOrderId error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public Integer selectCountByMapOrderProductId(Integer mapOrderProductId) throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectCountByMapOrderProductId(mapOrderProductId);
	}
	@Override
	public Report selectReportByReportId(Integer reportId) throws Exception {
		Report report = new Report();
		report.setIsdelete(false);
		report.setId(reportId);
		return reportMapper.selectByPrimaryKey(report);
	}
	
}
