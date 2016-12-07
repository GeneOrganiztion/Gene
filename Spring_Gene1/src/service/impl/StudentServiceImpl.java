package service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import Mapper.StudentMapper;
import po.Student;
import service.StudentService;

public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> selectStudentByParams(Map<String, Object> map) {

        return studentMapper.selectStudentByParams(map);
    }

    @Override
    public Integer selctStdentCount(Map<String, Object> map) {
        return studentMapper.selctStdentCount(map);
    }
}
