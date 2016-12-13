package po;


public class ResModel {

	public boolean success;//返回前台  true or false
	public String msg;//返回前台的信息
	
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

}
