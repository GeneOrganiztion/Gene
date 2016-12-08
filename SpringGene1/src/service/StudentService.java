package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import po.Student;

public interface StudentService<T> {
    List<Student> selectStudentByParams(Map<String, Object> map);
    Integer selctStdentCount(Map<String, Object> map);
    public T selectPageDemo(Map<String, Object> map) throws Exception;
}
