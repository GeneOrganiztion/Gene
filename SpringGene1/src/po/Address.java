package po;

import java.util.Date;

import javax.persistence.Table;
@Table(name ="address")
public class Address extends AddressKey {
	
    private String addrName;

    private String addrPhone;

    private String addrContent;

    private Boolean addrDefault;

    private Boolean isdelete;

    private Date createTime;

    private Date lastModifiedTime;

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName == null ? null : addrName.trim();
    }

    public String getAddrPhone() {
        return addrPhone;
    }

    public void setAddrPhone(String addrPhone) {
        this.addrPhone = addrPhone == null ? null : addrPhone.trim();
    }

    public String getAddrContent() {
        return addrContent;
    }

    public void setAddrContent(String addrContent) {
        this.addrContent = addrContent == null ? null : addrContent.trim();
    }

    public Boolean getAddrDefault() {
        return addrDefault;
    }

    public void setAddrDefault(Boolean addrDefault) {
        this.addrDefault = addrDefault;
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