package po;

import javax.persistence.Table;

@Table(name = "image")
public class Image {
    private Integer imageid;

    private Integer proId;

    private String url;

    public Integer getId() {
        return imageid;
    }

    public void setId(Integer id) {
        this.imageid = id;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}