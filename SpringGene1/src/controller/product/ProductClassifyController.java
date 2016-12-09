
package controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.BaseController;
import controller.base.LoginController;
import po.Admin;
import po.Classify;
import po.ClassifyModel;
import po.row;
import service.ClassifyService;
import utils.ST;

@Controller
@RequestMapping("/classify")
public class ProductClassifyController extends BaseController {
	@Autowired
	private ClassifyService classifyService;
	
	 @RequestMapping(value = "/phoneclsall", method = RequestMethod.GET)
	 @ResponseBody
	 public ClassifyModel clsall(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		List<Classify> classifyall=classifyService.selectAll();
		ClassifyModel classifymodel=new ClassifyModel();
		List<row> rows=new ArrayList<row>();
		for(int i=0;i<classifyall.size();i=i+2){
			List<Classify> classify2=new ArrayList<Classify>();
			row ro=new row();
				classify2.add(classifyall.get(i));
			if(i+1==classifyall.size()){
			}else{
			classify2.add(classifyall.get(i+1));
			}	
			ro.setRow(classify2);
			rows.add(ro);
		}
		classifymodel.setClassifies(rows);
		return classifymodel;
	 }  
	 
	 @RequestMapping(value = "/clsave", method = RequestMethod.GET)
	 @ResponseBody
	 public boolean clssave(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		String clsname = getParam("clsname");
	    String clscontent = getParam("clscontent");
	    String cla_pid = getParam("cla_pid");
	    if(ST.getDefaultToInt(cla_pid, -1)!=-1){
	 	    Classify cls=new Classify();
	 	    cls.setClaName(clsname);
	 	    cls.setClaContent(clscontent);
	    	cls.setClaPid(ST.getDefaultToInt(cla_pid, -1));
	  		return classifyService.saveClassify(cls);
	    }
	  return false;
	 }
	 
	 /**
	 *
	 * @author chenzhangsheng
	 * @date 2016-12-7 18:31:52
	 * 返回0表示删除成功 返回状态1表示删除失败,2表示还有以存在的商品未下架
	 */
	 @RequestMapping(value = "/clsdel", method = RequestMethod.GET)
	 @ResponseBody
	 public int clsdel(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		   String clsId = getParam("clsId");
		   if(ST.getDefaultToInt(clsId, -1)!=-1){
				Classify cls=new Classify();
				cls.setId(ST.getDefaultToInt(clsId, -1));
				cls.setIsdelete(true);
				return classifyService.delClassify(cls);
		    }
		  return 1;
	 }    	
	 
	 @RequestMapping(value = "/clsupdate", method = RequestMethod.GET)
	 @ResponseBody
	 public boolean clsupdate(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		String clsId = getParam("clsId");
		String clsname = getParam("clsname");
	    String clscontent = getParam("clscontent");
	    String cla_pid = getParam("cla_pid");
	    if(ST.getDefaultToInt(clsId, -1)!=-1){
	        Date date=new Date();
		    Classify cls=new Classify();
	    	cls.setId(Integer.parseInt(clsId));
	    	cls.setClaPid(3);
	  	    cls.setClaName(clsname);
	  	    cls.setClaContent(clscontent);
	  	    if(ST.getDefaultToInt(cla_pid, -1)!=-1){
	  	 	    cls.setLastModifiedTime(date);
	  	 		return classifyService.updateClassify(cls);
	  	    }
	    }
	  
	    return false;
	 }

	
}

