package hwd.kuworuye.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/29.
 */

public class CommPassBean implements Parcelable {
    private String productId;
    private int itemNum;
    private double totalPrice;

    public CommPassBean(String productId, int itemNum, double totalPrice) {
        this.productId = productId;
        this.itemNum = itemNum;
        this.totalPrice = totalPrice;
    }

    protected CommPassBean(Parcel in) {
        productId = in.readString();
        itemNum = in.readInt();
        totalPrice = in.readDouble();
    }

    public static final Creator<CommPassBean> CREATOR = new Creator<CommPassBean>() {
        @Override
        public CommPassBean createFromParcel(Parcel in) {
            return new CommPassBean(in);
        }

        @Override
        public CommPassBean[] newArray(int size) {
            return new CommPassBean[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeInt(itemNum);
        parcel.writeDouble(totalPrice);
    }
}
