package po;

import java.util.List;

public class ResponseModel<T> {

    public List<T> rows;// 查询记录
    public Integer total;// 总页数
    public Long records;// 总计记录数

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}


}
