package po;

import javax.persistence.Table;

@Table(name ="image")
public class Image extends ImageKey {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}