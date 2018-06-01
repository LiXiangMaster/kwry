package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BackProductNumAndPriceBean {
    private int totalBox;
    private double price;
    private String orderId;

    public BackProductNumAndPriceBean(int totalBox, double price, String orderId) {
        this.totalBox = totalBox;
        this.price = price;
        this.orderId = orderId;
    }

    public int getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(int totalBox) {
        this.totalBox = totalBox;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "BackProductNumAndPriceBean{" +
                "totalBox=" + totalBox +
                ", price=" + price +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
