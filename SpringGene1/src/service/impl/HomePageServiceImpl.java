package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import po.HomePage;
import Mapper.HomePageMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import service.HomePageService;

public class HomePageServiceImpl implements HomePageService {
	private static final Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);
	@Autowired
	private HomePageMapper hompageMapper;

	@Override
	public PageInfo selectHomePageParams(Map map) {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<HomePage> list = new ArrayList<HomePage>();
		
		try {
			list = hompageMapper.selectHomePageParams(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("selectHomePageParams error:" + e);
		}
	    PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
		
	}

	@Override
	public int delHomePage(HomePage hop) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean saveHomePage(HomePage hop) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateHomePage(HomePage hop) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
