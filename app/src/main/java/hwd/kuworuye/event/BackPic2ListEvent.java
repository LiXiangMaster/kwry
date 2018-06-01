package hwd.kuworuye.event;

import java.util.List;

import hwd.kuworuye.bean.ImageDataUrlBean;

/**
 * Created by Administrator on 2017/4/1.
 */

public class BackPic2ListEvent {
    private List<ImageDataUrlBean> backPicList;
    private int backType;

    public BackPic2ListEvent(List<ImageDataUrlBean> backPicList, int backType) {
        this.backPicList = backPicList;
        this.backType = backType;
    }

    public int getBackType() {
        return backType;
    }

    public void setBackType(int backType) {
        this.backType = backType;
    }

    public List<ImageDataUrlBean> getBackPicList() {
        return backPicList;
    }

    public void setBackPicList(List<ImageDataUrlBean> backPicList) {
        this.backPicList = backPicList;
    }

}
