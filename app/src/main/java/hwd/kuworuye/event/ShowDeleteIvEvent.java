package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ShowDeleteIvEvent {
    public   boolean isDelete;

    public ShowDeleteIvEvent(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean getIsDelete() {
        return isDelete;
    }
}
