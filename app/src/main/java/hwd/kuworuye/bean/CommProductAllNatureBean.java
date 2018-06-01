package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/28.
 */

public class CommProductAllNatureBean {
    private String number;
    private String productAttributeId;
    private String picUrl;
    private String smellStr;
    private String productName;
    private String normals;
    private String price;
    private String weight;

    public CommProductAllNatureBean(String number, String productAttributeId, String picUrl, String smellStr, String productName, String normals, String price, String weight) {

        this.number = number;
        this.productAttributeId = productAttributeId;
        this.picUrl = picUrl;
        this.smellStr = smellStr;
        this.productName = productName;
        this.normals = normals;
        this.price = price;
        this.weight = weight;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSmellStr() {
        return smellStr;
    }

    public void setSmellStr(String smellStr) {
        this.smellStr = smellStr;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNormals() {
        return normals;
    }

    public void setNormals(String normals) {
        this.normals = normals;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "CommProductAllNatureBean{" +
                "number='" + number + '\'' +
                ", productAttributeId='" + productAttributeId + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", smellStr='" + smellStr + '\'' +
                ", productName='" + productName + '\'' +
                ", normals='" + normals + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}