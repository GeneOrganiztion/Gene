package po;

import java.util.Date;

import javax.persistence.Table;

@Table(name ="map_product_cart")
public class Map_Product_Cart extends Map_Product_CartKey {
    private Integer proSize;

    private Integer proColor;

    private Integer proCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public Integer getProSize() {
        return proSize;
    }

    public void setProSize(Integer proSize) {
        this.proSize = proSize;
    }

    public Integer getProColor() {
        return proColor;
    }

    public void setProColor(Integer proColor) {
        this.proColor = proColor;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
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