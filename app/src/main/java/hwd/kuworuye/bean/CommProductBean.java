package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/21.
 */

public class CommProductBean {

    public CommProductBean(String productAttributeId, String quantity) {
        this.productAttributeId = productAttributeId;
        this.quantity = quantity;
    }

    /**
     * productAttributeId : 598ab0dd553d465da82960046c3dd4ff
     * quantity : 5
     */


    private String quantity;
    private String productAttributeId;

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String number) {
        this.quantity = quantity;
    }
}
