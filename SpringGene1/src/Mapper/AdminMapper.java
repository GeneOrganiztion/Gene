package Mapper;

import java.util.List;
import java.util.Map;

import po.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {
/*    int deleteByPrimaryKey(Integer id);
    int insert(Admin record);
    int insertSelective(Admin record);
    Admin selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Admin record);
    int updateByPrimaryKey(Admin record);*/
	
	List<Admin> selectAdminByParams(Map<String, Object> map);
	
	void deleteAdminByIds(List<Integer> ids);
}