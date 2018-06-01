package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/27.
 */

public class KwProductNatureBean {
    public KwProductNatureBean(String price, String productAttributeId, String quantity, String productName, String weight) {
        this.price = price;
        this.productAttributeId = productAttributeId;
        this.quantity = quantity;
        this.productName = productName;
        this.weight = weight;
    }

    /**
     * price : 50.00
     * productAttributeId : 846d22d8306e429c9570b3eac45e4c32
     * quantity : 2
     * productName : 原味,营养快线,100ml*50盒
     * weight : 0.500
     */

    private String price;
    private String productAttributeId;
    private String quantity;
    private String productName;
    private String weight;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
