package hwd.kuworuye.event;

import java.util.List;

import hwd.kuworuye.bean.CommProductAllNatureBean;

/**
 * Created by Administrator on 2017/3/28.
 */

public class KwChooseProductListEvent {
    private List<CommProductAllNatureBean> mList;

    public KwChooseProductListEvent(List<CommProductAllNatureBean> list) {
        mList = list;
    }

    public List<CommProductAllNatureBean> getList() {
        return mList;
    }

    public void setList(List<CommProductAllNatureBean> list) {
        mList = list;
    }

    @Override
    public String toString() {
        return "KwChooseProductListEvent{" +
                "mList=" + mList +
                '}';
    }
}
