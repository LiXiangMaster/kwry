package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/22.
 */

public class TargetDoneInfo {
    private String month;
    private double targetBackMoney;
    private double reallyBackMoney;
    private double percent;

    public TargetDoneInfo(String month, double targetBackMoney, double reallyBackMoney, double percent) {
        this.month = month;
        this.targetBackMoney = targetBackMoney;
        this.reallyBackMoney = reallyBackMoney;
        this.percent = percent;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTargetBackMoney() {
        return targetBackMoney;
    }

    public void setTargetBackMoney(double targetBackMoney) {
        this.targetBackMoney = targetBackMoney;
    }

    public double getReallyBackMoney() {
        return reallyBackMoney;
    }

    public void setReallyBackMoney(double reallyBackMoney) {
        this.reallyBackMoney = reallyBackMoney;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "TargetDoneInfo{" +
                "month='" + month + '\'' +
                ", targetBackMoney=" + targetBackMoney +
                ", reallyBackMoney=" + reallyBackMoney +
                ", percent=" + percent +
                '}';
    }
}

