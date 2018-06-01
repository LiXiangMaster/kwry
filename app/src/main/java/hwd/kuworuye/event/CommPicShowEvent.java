package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/27.
 */
public class CommPicShowEvent {
    private boolean isRefresh;

    public CommPicShowEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
