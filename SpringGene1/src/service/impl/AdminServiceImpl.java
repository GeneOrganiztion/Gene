package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.AdminMapper;
import controller.order.OrderDetailController;
import po.Admin;
import po.Student;
import service.AdminService;
import utils.MD5Util;
@Transactional
public class AdminServiceImpl implements AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	@Autowired
	private AdminMapper adminMapper;
	 
	public Admin login(String username,String password) throws Exception {
		Admin admin=new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		return adminMapper.selectOne(admin);
	}
	
	@Override
	public PageInfo selectAdminByParams(Map map) throws Exception {
		PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		List<Admin> list = adminMapper.selectAdminByParams(map);
	    PageInfo page = new PageInfo(list);
		return page;
	}
	
	@Override
    public boolean deleteAdminByIds(List<Integer> ids) {
		try {
			adminMapper.deleteAdminByIds(ids);
		} catch (Exception e) {
			logger.error("AdminServiceImpl deleteAdminByIds error:" + e);
			return false;
		}
		return true;
    }
	@Override
	public boolean saveAdmin(Admin admin){
		try {
			String passWordMD5 = MD5Util.getMD5(admin.getPassword());
			admin.setPassword(passWordMD5);
			adminMapper.insertSelective(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public boolean updateAdmin(Admin admin){
		try {
			//String passWordMD5 = MD5Util.getMD5(admin.getPassword());
			//admin.setPassword(passWordMD5);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adminId", admin.getId());
			List<Admin> adminList = adminMapper.selectAdminByParams(map);
			//如果前台传过来的密码没有更改，就不更改数据库中的密码
			if(adminList != null && adminList.size() > 0){
				Admin am = adminList.get(0);
				if(!am.getPassword().equals(admin.getPassword()) ){
					admin.setPassword(MD5Util.getMD5(admin.getPassword()));
				}
				admin.setLastModifiedTime(new Date());
				adminMapper.updateByPrimaryKeySelective(admin);
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			return false;
		}
		return true;
	}
	@Override
	public Admin selectAdminByAdminId(Map<String,Object> map){
		List<Admin> adminList = new ArrayList<Admin>();
		try {
			adminList = adminMapper.selectAdminByParams(map);
		} catch (Exception e) {
			logger.error("selectAdminByAdminId error:" + e);
		}
		if(adminList != null && adminList.size() > 0){
			return adminList.get(0);
		}else{
			return new Admin();
		}
	}
	@Override
	public List<Admin> validateAdmin(String name){
		Admin admin = new Admin();
		admin.setIsdelete(false);
		admin.setUsername(name);
		return adminMapper.select(admin);
	}

}
