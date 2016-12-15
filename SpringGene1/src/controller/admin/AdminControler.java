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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import po.Admin;
import po.ResModel;
import service.AdminService;
import utils.ST;

import com.github.pagehelper.PageInfo;

import controller.base.BaseController;
import controller.order.OrderDetailController;

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
            if(!ST.isNull(beginTime)){
            	map.put("beginTime", beginTime + " 00:00:00");
            }
            if(!ST.isNull(endTime)){
            	map.put("endTime", endTime + " 59:59:59");
            }
            map.put("userName", userName);
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
			logger.error("delete error:" + e);
			return false;
		}
		return true;
	}
	@RequestMapping(value="/saveAdmin")
	@ResponseBody
	public ResModel saveAdmin(@ModelAttribute Admin admin,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = adminService.saveAdmin(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		resModel.setMsg("插入成功！");
		return resModel;
	}
	@RequestMapping(value="/selectAdminByAdminId")
	@ResponseBody
	public Admin selectAdminByAdminId(HttpServletRequest request,HttpServletResponse response){
		String adminId = getParam("adminId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adminId", adminId);
		Admin admin = new Admin();
		try {
			admin = adminService.selectAdminByAdminId(map);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
		}
		return admin;
	}
	@RequestMapping(value="/updateAdmin")
	@ResponseBody
	public ResModel updateAdmin(@ModelAttribute Admin admin,
			HttpServletRequest request,HttpServletResponse response){
		ResModel  resModel = new ResModel();
		boolean bl = false;
		try {
			bl = adminService.updateAdmin(admin);
		} catch (Exception e) {
			logger.error("saveAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		resModel.setSuccess(bl);
		return resModel;
	}
	/**
	 * 验证是否存在此用户名
	 * @param request
	 * @param response    resModel    true:存在重复的用户名      false:不存在重复的用户名
	 * @return
	 */
	@RequestMapping(value="/validateAdmin")
	@ResponseBody
	public ResModel validateAdmin(HttpServletRequest request,HttpServletResponse response){
		String name = getParam("name");
		ResModel  resModel = new ResModel();
		boolean bl = false;
		List<Admin> listAdmin = null;
		try {
			listAdmin = adminService.validateAdmin(name);
		} catch (Exception e) {
			logger.error("validateAdmin error:" + e);
			resModel.setSuccess(bl);
			return resModel;
		}
		if(listAdmin != null && listAdmin.size() > 0){
			resModel.setSuccess(true);
		}else{
			resModel.setSuccess(bl);
		}
		
		return resModel;
	}
	
	
}
