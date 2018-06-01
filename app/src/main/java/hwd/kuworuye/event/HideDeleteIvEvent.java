package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/13.
 */

public class HideDeleteIvEvent {
    private  boolean isHideDeleIv;

    public HideDeleteIvEvent(boolean isHideDeleIv) {
        this.isHideDeleIv = isHideDeleIv;
    }

    public boolean getIsHideDeleIv() {
        return isHideDeleIv;
    }
}
