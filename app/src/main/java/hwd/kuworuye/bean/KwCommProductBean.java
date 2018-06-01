package hwd.kuworuye.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/30.
 */

public class KwCommProductBean implements Serializable {
    private String productAttributeId;
    private String productName;
    private String tasteName;
    private String specificationsName;
    private String price;
    private String itemNum;
    private String thumbnail;
    private String weight;

    public KwCommProductBean(String productAttributeId, String productName, String tasteName, String specificationsName, String price, String itemNum, String thumbnail, String weight) {
        this.productAttributeId = productAttributeId;
        this.productName = productName;
        this.tasteName = tasteName;
        this.specificationsName = specificationsName;
        this.price = price;
        this.itemNum = itemNum;
        this.thumbnail = thumbnail;
        this.weight = weight;
    }

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTasteName() {
        return tasteName;
    }

    public void setTasteName(String tasteName) {
        this.tasteName = tasteName;
    }

    public String getSpecificationsName() {
        return specificationsName;
    }

    public void setSpecificationsName(String specificationsName) {
        this.specificationsName = specificationsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "KwCommProductBean{" +
                "productAttributeId='" + productAttributeId + '\'' +
                ", productName='" + productName + '\'' +
                ", tasteName='" + tasteName + '\'' +
                ", specificationsName='" + specificationsName + '\'' +
                ", price='" + price + '\'' +
                ", itemNum='" + itemNum + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
