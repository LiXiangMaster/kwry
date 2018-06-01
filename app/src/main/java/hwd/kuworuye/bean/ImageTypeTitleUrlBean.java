package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/2.
 */

public class ImageTypeTitleUrlBean {
    private String type;
    private String title;
    private String imgs;

    public ImageTypeTitleUrlBean(String type, String describle, String url) {
        this.type = type;
        this.title = describle;
        this.imgs = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "ImageTypeTitleUrlBean{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}
