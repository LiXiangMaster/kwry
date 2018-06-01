package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class MathNumPriceBean {
    private int num;
    private double price;

    public MathNumPriceBean(int num, double price) {
        this.num = num;
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MathNumPriceBean{" +
                "num=" + num +
                ", price=" + price +
                '}';
    }
}
