package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/27.
 */
public class CommRefreshListEvent {
    private boolean isRefresh;

    public CommRefreshListEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
