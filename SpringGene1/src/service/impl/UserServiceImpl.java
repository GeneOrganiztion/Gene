package service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.InfoPersonMapper;
import Mapper.UserMapper;
import po.InfoPerson;
import po.Product;
import po.User;
import service.UserService;

public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper UserMapper;

    @Override
    public User select(User user) throws Exception {
      /*  PageHelper.startPage(1, 10); // 核心分页代码
        InfoPerson ifp = new InfoPerson();
        ifp.setPersonName("4");
        List<InfoPerson> list = PersonMapper.select(ifp);
        PageInfo page = new PageInfo(list);*/
    	user.setIsdelete(false);
        return (User)UserMapper.selectOne(user);
    }


	@Override
	public int insertUser(User user) throws Exception {
		user.setIsdelete(false);
		user.setCreateTime(new Date());
		user.setLastModifiedTime(new Date());
		int newuserid=-1;
		try{
			UserMapper.insertUseGeneratedKeys(user);
			newuserid=user.getId();
		}catch(Exception e){
			logger.info("insertUser"+e);
		}
		return newuserid;
	}
	
	@Override
	public boolean updateUser(User user) throws Exception {
		boolean flag=false;
		user.setLastModifiedTime(new Date());
		try{
			UserMapper.updateByPrimaryKeySelective(user);
			flag=true;
		}catch(Exception e){
			logger.info("updateUser "+e);
		}
		return flag;
	}

}
