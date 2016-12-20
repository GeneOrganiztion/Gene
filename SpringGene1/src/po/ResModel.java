package po;


public class ResModel {

	public boolean success;//返回前台  true or false
	public String msg;//返回前台的信息
	public Integer returnId; //返回前台 integer类型的字段
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getReturnId() {
		return returnId;
	}
	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}

	
}
