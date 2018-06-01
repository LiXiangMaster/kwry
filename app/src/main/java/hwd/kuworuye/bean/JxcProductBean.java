package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/21.
 */

public class JxcProductBean {


    private String number;
    private String productAttributeId;

    public JxcProductBean(String number, String productAttributeId) {
        this.number = number;
        this.productAttributeId = productAttributeId;
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
}
