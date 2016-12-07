package service;

import java.util.List;
import java.util.Map;

import po.Student;

public interface StudentService {
    List<Student> selectStudentByParams(Map<String, Object> map);

    Integer selctStdentCount(Map<String, Object> map);
}
