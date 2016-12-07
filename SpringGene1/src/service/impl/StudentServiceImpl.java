package service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Mapper.StudentMapper;
import po.InfoPerson;
import po.Student;
import service.StudentService;

public class StudentServiceImpl implements StudentService {
	 @Autowired
	 private StudentMapper studentMapper;
	 
	@Override
	public List<Student> selectStudentByParams(Map map) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentByParams(map);
	}

	@Override
	public Integer selctStdentCount(Map map) {
		// TODO Auto-generated method stub
		 return studentMapper.selctStdentCount(map);
	}

	@Override
	public PageInfo selectPageDemo(Map map) throws SQLException {
		 PageHelper.startPage((Integer)map.get("pageNo"),(Integer)map.get("rowCount"));
		 List<Student> list = studentMapper.selectStudentByParams(map);
	     PageInfo page = new PageInfo(list);
		 return page;
	}
   
}
