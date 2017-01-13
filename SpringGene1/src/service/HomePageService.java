package service;

import java.util.List;
import java.util.Map;

import po.HomePage;

import com.github.pagehelper.PageInfo;

public interface HomePageService {
	
	public PageInfo selectHomePageParams(Map map);
	
	public boolean delHomePage(HomePage hop) throws Exception;
	
	public Integer saveHomePage(HomePage hop) throws Exception;
	
	public boolean updateHomePage(HomePage hop) throws Exception;
	public HomePage selectOneHomePageById(HomePage hop) throws Exception;
	
	public List<HomePage> selectAll(HomePage hop)throws Exception;
}
