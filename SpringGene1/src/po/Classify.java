package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name ="classify")
public class Classify {
	@Id
    private Integer classify_id;
   
    private String claName;

    private String claContent;

    private Integer claPid;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    //二级分类所属一级分类的名称
    @Transient
    private String oneClassName;
    
    public Integer getId() {
        return classify_id;
    }

    public void setId(Integer id) {
        this.classify_id = id;
    }

    public String getClaName() {
        return claName;
    }

    public void setClaName(String claName) {
        this.claName = claName == null ? null : claName.trim();
    }

    public String getClaContent() {
        return claContent;
    }

    public void setClaContent(String claContent) {
        this.claContent = claContent == null ? null : claContent.trim();
    }

    public Integer getClaPid() {
        return claPid;
    }

    public void setClaPid(Integer claPid) {
        this.claPid = claPid;
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

	public String getOneClassName() {
		return oneClassName;
	}

	public void setOneClassName(String oneClassName) {
		this.oneClassName = oneClassName;
	}
    
}