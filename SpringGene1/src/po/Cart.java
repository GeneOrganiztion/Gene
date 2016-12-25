package po;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "cart")
public class Cart {
	@Id
    private Integer cart_id;

    private Integer userId;

    private Integer cartProCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
   
    @Transient
    private List<MapProductCartDemo> listOrderProduct;
    
    public List<MapProductCartDemo> getListOrderProduct() {
		return listOrderProduct;
	}

	public void setListOrderProduct(List<MapProductCartDemo> listOrderProduct) {
		this.listOrderProduct = listOrderProduct;
	}

	

    public Integer getId() {
        return cart_id;
    }

    public void setId(Integer id) {
        this.cart_id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCartProCount() {
        return cartProCount;
    }

    public void setCartProCount(Integer cartProCount) {
        this.cartProCount = cartProCount;
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