package service;

import po.BaseEntity;
import po.InfoPerson;
import po.User;

import java.sql.SQLException;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * Created by i-zhangshengchen on 2016/11/25.
 * @param <T>
 */
public interface UserService<T> {

	public T select(User user) throws Exception;
    
  /*  public List<T> selectall()  throws Exception;*/
    
    public int insertUser(User user)throws Exception;
    
    public boolean updateUser(User user)throws Exception;

}
