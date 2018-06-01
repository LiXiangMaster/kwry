package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/20.
 */

public class AddAskGoodsPlanBean {
    private String productAttributeId;
    private String number;

    public AddAskGoodsPlanBean(String productAttributeId, String number) {
        this.productAttributeId = productAttributeId;
        this.number = number;
    }

    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AddAskGoodsPlanBean{" +
                "productAttributeId='" + productAttributeId + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
