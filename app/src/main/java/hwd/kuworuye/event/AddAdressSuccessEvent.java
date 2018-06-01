package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/18.
 */

public class AddAdressSuccessEvent {
    private boolean isAddSuccess;

    public AddAdressSuccessEvent(boolean isAddSuccess) {
        this.isAddSuccess = isAddSuccess;
    }

    public boolean isAddSuccess() {
        return isAddSuccess;
    }

    public void setAddSuccess(boolean addSuccess) {
        isAddSuccess = addSuccess;
    }
}
