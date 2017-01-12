package service;

import java.util.Map;

import po.HomePage;

import com.github.pagehelper.PageInfo;

public interface HomePageService {
	
	public PageInfo selectHomePageParams(Map map);
	
	public int delHomePage(HomePage hop) throws Exception;
	
	public boolean saveHomePage(HomePage hop) throws Exception;
	
	public boolean updateHomePage(HomePage hop) throws Exception;

}
