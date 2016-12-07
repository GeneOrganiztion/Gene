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

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
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
    public PageInfo testStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
        //System.out.println("oneRecord="+oneRecord);
        int pageNo = Integer.valueOf(getParam("page"));// 第几页
       // System.out.println("pageNo="+pageNo);
        String name = getParam("name");
        String age = getParam("age");
        String phone = getParam("phone");
        map.put("sidx", sidx);// 排序字段
        map.put("sord", sord);// 升序降序
        map.put("rowCount", oneRecord);//一页几行
       // map.put("offset", (pageNo-1) * oneRecord);//从第几行开始
        map.put("pageNo", pageNo);
        map.put("name", name);
        map.put("age", age);
        map.put("phone", phone);
        PageInfo page= (PageInfo)studentService.selectPageDemo(map);
        /*ResponseModel<Student> rm = new ResponseModel<Student>();
 
        rm.setRows(page.getList());
        rm.setRecords(page.getTotal());
        System.out.println("gettotal="+page.getTotal());
        System.out.println("pageNumber="+page.getLastPage());
        rm.setTotal(page.getLastPage());*/
        return page;

    }
    
    @RequestMapping(value = "/testPage")
    public ModelAndView testPage(HttpServletRequest request,HttpServletResponse response) {
    	ModelAndView modelAndView = new ModelAndView("student");
       // modelAndView.setViewName("student");
        return modelAndView;

    }

}
