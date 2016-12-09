package po;

import java.util.Date;

import javax.persistence.Table;

@Table(name = "cart")
public class Cart {

    private Integer cartid;

    private Integer userId;

    private Integer cartProCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public Integer getId() {
        return cartid;
    }

    public void setId(Integer id) {
        this.cartid = id;
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