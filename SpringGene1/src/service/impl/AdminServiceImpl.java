package service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import po.Admin;
import Mapper.AdminMapper;
import service.AdminService;
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper AdminMapper;
	 
	public Admin login(String username,String password) throws Exception {
		Admin admin=new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		return AdminMapper.selectOne(admin);
	}
	
	

}
