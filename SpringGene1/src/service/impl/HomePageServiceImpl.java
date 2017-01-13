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
	public boolean delHomePage(HomePage hop) throws Exception {
		try{
			hop.setIsdelete(true);
			hompageMapper.updateByPrimaryKeySelective(hop);
		}catch(Exception e){
			logger.error("delHomePage error:" + e);
			return false;
		}
		return true;
	}

	@Override
	public Integer saveHomePage(HomePage hop) throws Exception {
		try{
			hompageMapper.insertUseGeneratedKeys(hop);
			}catch(Exception e){
			
			e.printStackTrace();
			return -1;
		}
		return hop.getId();
	}

	@Override
	public boolean updateHomePage(HomePage hop) throws Exception {
		try{
			hompageMapper.updateByPrimaryKeySelective(hop);
			}catch(Exception e){
			logger.info("updateHomePage.update="+e);
			e.printStackTrace();
			return false;
			}
			return true;
	}

	@Override
	public HomePage selectOneHomePageById(HomePage hop) throws Exception {
		hop.setIsdelete(false);
		return hompageMapper.selectOne(hop);
	}




	@Override
	public List<HomePage> selectAll(HomePage hop) throws Exception {
		hop.setIsdelete(false);
		return 	hompageMapper.select(hop);
	}

}
