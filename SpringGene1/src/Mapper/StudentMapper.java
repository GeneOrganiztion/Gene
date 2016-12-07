package Mapper;

import java.util.List;
import java.util.Map;

import po.Student;
import tk.mybatis.mapper.common.Mapper;

public interface StudentMapper extends Mapper<Student> {

    List<Student> selectStudentByParams(Map<String, Object> map);

    Integer selctStdentCount(Map<String, Object> map);

}
