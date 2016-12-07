package service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import po.Admin;
import Mapper.AdminMapper;
import service.AdminService;

public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper AdminMapper;
	 
	public Admin login(String username,String password) throws SQLException {
		Admin admin=new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		return AdminMapper.selectOne(admin);
	}

}
