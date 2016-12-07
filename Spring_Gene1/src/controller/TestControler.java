package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import po.ResponseModel;
import po.Student;
import service.StudentService;

@Controller
@RequestMapping(value="/testStudent")
public class TestControler extends BaseController {

    @SuppressWarnings("unused")
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseModel<Student> testStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
        int pageNo = Integer.valueOf(getParam("page"));// 第几页
        String name = getParam("name");
        String age = getParam("age");
        String phone = getParam("phone");
        map.put("sidx", sidx);// 排序字段
        map.put("sord", sord);// 升序降序
        map.put("rowCount", oneRecord);//一页几行
        map.put("offset", (pageNo-1) * oneRecord);//从第几行开始
        map.put("name", name);
        map.put("age", age);
        map.put("phone", phone);
        List<Student> list = studentService.selectStudentByParams(map);// 查询数据
        Integer count = studentService.selctStdentCount(map);// 查询记录总数
        ResponseModel<Student> rm = new ResponseModel<Student>();
        rm.setRows(list);
        rm.setRecords(count);
        int total = (count % oneRecord) == 0 ? (count / oneRecord) : (count / oneRecord) + 1;
        rm.setTotal(total);
        return rm;

    }
    
    @RequestMapping(value = "/testPage")
    public ModelAndView testPage(HttpServletRequest request,HttpServletResponse response) {
    	ModelAndView modelAndView = new ModelAndView("student");
       // modelAndView.setViewName("student");
        return modelAndView;

    }

}
