package service.impl;

import java.util.List;
import java.util.Map;

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

}
