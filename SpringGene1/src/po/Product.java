package po;

import java.util.Date;

import javax.persistence.Table;
@Table(name = "product")
public class Product {
    private Integer id;

    private String proName;

    private String proHead;

    private Integer proPrice;

    private Integer proCount;

    private Integer proRateprice;

    private Boolean proOnline;

    private String proImage;

    private String proDetail;

    private String proArea;

    private Integer classifyId;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public String getProHead() {
        return proHead;
    }

    public void setProHead(String proHead) {
        this.proHead = proHead == null ? null : proHead.trim();
    }

    public Integer getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }

    public Integer getProRateprice() {
        return proRateprice;
    }

    public void setProRateprice(Integer proRateprice) {
        this.proRateprice = proRateprice;
    }

    public Boolean getProOnline() {
        return proOnline;
    }

    public void setProOnline(Boolean proOnline) {
        this.proOnline = proOnline;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage == null ? null : proImage.trim();
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail == null ? null : proDetail.trim();
    }

    public String getProArea() {
        return proArea;
    }

    public void setProArea(String proArea) {
        this.proArea = proArea == null ? null : proArea.trim();
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
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