package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/16.
 */

public class CommUpdateProductBean {
    private String productId;
    private String quantity;
    private String price;
    private String tasteStr;
    private String productName;
    private String specificationsStr;
    private String thumbnail;

    public CommUpdateProductBean(String productId, String quantity, String price, String tasteStr, String productName, String specificationsStr, String thumbnail) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tasteStr = tasteStr;
        this.productName = productName;
        this.specificationsStr = specificationsStr;
        this.thumbnail = thumbnail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTasteStr() {
        return tasteStr;
    }

    public void setTasteStr(String tasteStr) {
        this.tasteStr = tasteStr;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecificationsStr() {
        return specificationsStr;
    }

    public void setSpecificationsStr(String specificationsStr) {
        this.specificationsStr = specificationsStr;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "CommUpdateProductBean{" +
                "productId='" + productId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", tasteStr='" + tasteStr + '\'' +
                ", productName='" + productName + '\'' +
                ", specificationsStr='" + specificationsStr + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
