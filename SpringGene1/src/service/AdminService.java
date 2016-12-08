package service;

import java.sql.SQLException;

public interface AdminService<T>{
	
	public T login(String username,String password) throws Exception;

}
