package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/17.
 */

public class OrderWaitSubmitBean {
    private String productAttributeId;
    private String productName;
    private String price;
    private String quantity;
    private String weight;

    public OrderWaitSubmitBean(String productAttributeId, String productName, String price, String quantity, String weight) {
        this.productAttributeId = productAttributeId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "OrderWaitSubmitBean{" +
                "productAttributeId='" + productAttributeId + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
