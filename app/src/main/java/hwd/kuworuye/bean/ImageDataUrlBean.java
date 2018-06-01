package hwd.kuworuye.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/1.
 */

public class ImageDataUrlBean implements Parcelable {
    private String imgs;
    private String title;

    public ImageDataUrlBean(String picUrl, String title) {
        this.imgs = picUrl;
        this.title = title;
    }


    protected ImageDataUrlBean(Parcel in) {
        imgs = in.readString();
        title = in.readString();
    }

    public static final Creator<ImageDataUrlBean> CREATOR = new Creator<ImageDataUrlBean>() {
        @Override
        public ImageDataUrlBean createFromParcel(Parcel in) {
            return new ImageDataUrlBean(in);
        }

        @Override
        public ImageDataUrlBean[] newArray(int size) {
            return new ImageDataUrlBean[size];
        }
    };

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ImageDataUrlBean{" +
                "imgs='" + imgs + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgs);
        parcel.writeString(title);
    }
}
