package po;

import java.util.Date;

public class Cart extends CartKey {
    private Integer cartProCount;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

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