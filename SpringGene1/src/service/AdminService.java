package service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import po.Admin;

public interface AdminService{
	
	public Admin login(String username,String password) throws Exception;
	
	public PageInfo selectAdminByParams(Map<String, Object> map) throws Exception;
	
	//public void deleteAdminByIds(List<Integer> ids);

	public boolean deleteAdminByIds(List<Integer> ids);
	
	public boolean saveAdmin(Admin admin);
	
	public boolean updateAdmin(Admin admin);
	
	public Admin selectAdminByAdminId(Map<String,Object> map);

}
