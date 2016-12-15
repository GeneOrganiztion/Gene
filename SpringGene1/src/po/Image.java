package po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "image")
public class Image {
	@Id
    private Integer image_id;

    private Integer proId;

    private String url;
    
 
    public Integer getId() {
        return image_id;
    }


	public void setId(Integer id) {
        this.image_id = id;
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