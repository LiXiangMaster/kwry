package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/4/27.
 */

public class TotalNumAndPriceIdEvent {
    private int totalNum;
    private double singlePrice;
    private  String orderId;

    public TotalNumAndPriceIdEvent(int totalNum, double singlePrice, String orderId) {
        this.totalNum = totalNum;
        this.singlePrice = singlePrice;
        this.orderId = orderId;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
