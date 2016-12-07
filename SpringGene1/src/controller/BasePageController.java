package controller;

import controller.base.BaseController;

public class BasePageController extends BaseController {
	
	 	protected String sidx ;// 排序字段;
	    protected String sord ;// 升序降序;
	    protected int oneRecord ;// 一页几行
	    protected int pageNo ;// 第几页
	    
	    
	    //分页中从第几行开始
	    protected Integer getOffset(){
	         return (pageNo -1) * oneRecord;
	    }


		public String getSidx() {
			String a = getParam("sidx");
			return a;
		}


		public void setSidx(String sidx) {
			this.sidx = sidx;
		}


		public String getSord() {
			return getParam("sord");
		}


		public void setSord(String sord) {
			this.sord = sord;
		}


		public int getOneRecord() {
			return Integer.valueOf(getParam("rows"));
		}


		public void setOneRecord(int oneRecord) {
			this.oneRecord = oneRecord;
		}


		public int getPageNo() {
			return Integer.valueOf(getParam("page"));
		}


		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}
	    

}
