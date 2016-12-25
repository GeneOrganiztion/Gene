package po;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer product_id;

    private String proName;

    private String proHead;

    private Integer productPrice;

    private Integer proSum;

    private Integer proRateprice;

    private Boolean proOnline;

    private String proRemark;

    private String proDetail;

    private String proArea;

    private Integer classifyId;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    
    @Transient
    private List<Image> imagelist;
    
    public List<Image> getImagelist() {
		return imagelist;
	}

	public void setImagelist(List<Image> imagelist) {
		this.imagelist = imagelist;
	}

	public Integer getId() {
        return product_id;
    }

    public void setId(Integer id) {
        this.product_id = id;
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

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProSum() {
        return proSum;
    }

    public void setProSum(Integer proSum) {
        this.proSum = proSum;
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

    public String getproRemark() {
        return proRemark;
    }

    public void setproRemark(String proRemark) {
        this.proRemark = proRemark == null ? null : proRemark.trim();
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