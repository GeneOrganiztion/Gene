package po;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "orders")
public class Orders {
	@Id
    private Integer order_id;

    private Integer productId;

    private String ordNum;

    private Integer ordProCount;

    private String ordState;

    private String courierNum;

    private String courierName;

    private String courierPhone;

    private Integer ordPrice;

    private String ordPay;

    private Integer ordUser;

    private String userName;

    private String userPhone;

    private String userAddress;

    private String userPostal;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
    
    private List<Product> productList;

    public String getOrdNum() {
        return ordNum;
    }

    public Integer getId() {
        return order_id;
    }

    public void setId(Integer id) {
        this.order_id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setOrdNum(String ordNum) {
        this.ordNum = ordNum == null ? null : ordNum.trim();
    }

    public Integer getOrdProCount() {
        return ordProCount;
    }

    public void setOrdProCount(Integer ordProCount) {
        this.ordProCount = ordProCount;
    }

    public String getOrdState() {
        return ordState;
    }

    public void setOrdState(String ordState) {
        this.ordState = ordState == null ? null : ordState.trim();
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum == null ? null : courierNum.trim();
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName == null ? null : courierName.trim();
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone == null ? null : courierPhone.trim();
    }

    public Integer getOrdPrice() {
        return ordPrice;
    }

    public void setOrdPrice(Integer ordPrice) {
        this.ordPrice = ordPrice;
    }

    public String getOrdPay() {
        return ordPay;
    }

    public void setOrdPay(String ordPay) {
        this.ordPay = ordPay == null ? null : ordPay.trim();
    }

    public Integer getOrdUser() {
        return ordUser;
    }

    public void setOrdUser(Integer ordUser) {
        this.ordUser = ordUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getUserPostal() {
        return userPostal;
    }

    public void setUserPostal(String userPostal) {
        this.userPostal = userPostal == null ? null : userPostal.trim();
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

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

    
}