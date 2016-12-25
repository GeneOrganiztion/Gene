package po;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
public class OrderAndProductDTO {
	
	//order 状态
	private String ordState;
	//此订单已上传报告数量
	private Integer reportCount;
	
	// map_order_product 表属性
	private Integer map_order_product_id;
	    
    private Integer proId;
    
    private Integer ordId;

    private Integer proClassifyId;

    private Integer proPrice;

    private Integer proCount;

    private Integer reportIsUpload;
	
	//product 表属性
    private Integer product_id;

    private String proName;

    private String proHead;

    private Integer productPrice;

    private Integer proSum;

    private Integer proRateprice;

    private Boolean proOnline;

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

	public String getOrdState() {
		return ordState;
	}

	public void setOrdState(String ordState) {
		this.ordState = ordState;
	}

	public Integer getReportCount() {
		return reportCount;
	}

	public void setReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}

	public Integer getMap_order_product_id() {
		return map_order_product_id;
	}

	public void setMap_order_product_id(Integer map_order_product_id) {
		this.map_order_product_id = map_order_product_id;
	}

	public Integer getReportIsUpload() {
		return reportIsUpload;
	}

	public void setReportIsUpload(Integer reportIsUpload) {
		this.reportIsUpload = reportIsUpload;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getProClassifyId() {
		return proClassifyId;
	}

	public void setProClassifyId(Integer proClassifyId) {
		this.proClassifyId = proClassifyId;
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

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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