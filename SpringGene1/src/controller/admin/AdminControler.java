package controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
import controller.order.OrderDetailController;
import service.AdminService;
import utils.ST;

@Controller
@RequestMapping(value="/admin")
public class AdminControler extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
	
	private String ADMIN_PAGE = "admin/admin";
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/adminPage")
	public ModelAndView adminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ADMIN_PAGE);
		return mv;
	}
	@RequestMapping(value = "/selectAdmin")
    @ResponseBody
    public PageInfo selectAdminByParams(HttpServletRequest request, HttpServletResponse response)throws Exception{
	 	String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;
        PageInfo pageInfo = new PageInfo();
        try {
        	int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String userName = getParam("userName");
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);//一页几行
            map.put("pageNo", pageNo);
            map.put("userName", userName);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            pageInfo= (PageInfo)adminService.selectAdminByParams(map);
		} catch (Exception e) {
			logger.error("selectAdmin error:" + e);
		}
        return pageInfo;
	}
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(HttpServletRequest request,HttpServletResponse response){
		String adminIds = getParam("adminIds");
		try {
			List<Integer> list = ST.StringToList(adminIds);
			adminService.deleteAdminByIds(list);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
