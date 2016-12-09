package po;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "map_product_cart")
public class MapProductCart {
	@Id
    private Integer map_product_cart_id;

    private Integer proId;

    private Integer cartId;

    private Integer proSize;

    private Integer proColor;

    private Integer proCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;
   
    public Integer getId() {
        return map_product_cart_id;
    }

    public void setId(Integer id) {
        this.map_product_cart_id = id;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

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