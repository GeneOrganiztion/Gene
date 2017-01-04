package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "report")
public class Report {
	@Id
    private Integer report_id;

    private Integer proId;

    private Integer userId;
    
    private Integer ordId;
    
    private Integer mapOrderProductId;

    private String repName;

    private String repResult;

    private Integer repState;

    private String repPdf;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public Integer getId() {
        return report_id;
    }

    public void setId(Integer id) {
        this.report_id = id;
    }

    public Integer getProId() {
        return proId;
    }

    public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getMapOrderProductId() {
		return mapOrderProductId;
	}

	public void setMapOrderProductId(Integer mapOrderProductId) {
		this.mapOrderProductId = mapOrderProductId;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName == null ? null : repName.trim();
    }

    public String getRepResult() {
        return repResult;
    }

    public void setRepResult(String repResult) {
        this.repResult = repResult == null ? null : repResult.trim();
    }

    public Integer getRepState() {
		return repState;
	}

	public void setRepState(Integer repState) {
		this.repState = repState;
	}

	public String getRepPdf() {
        return repPdf;
    }

    public void setRepPdf(String repPdf) {
        this.repPdf = repPdf == null ? null : repPdf.trim();
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}