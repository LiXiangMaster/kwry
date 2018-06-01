package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/27.
 */
public class CommHeXiaoRefrestEvent {
    private boolean isRefresh;

    public CommHeXiaoRefrestEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
