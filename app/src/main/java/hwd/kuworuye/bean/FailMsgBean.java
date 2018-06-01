package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/18.
 */

public class FailMsgBean {

    /**
     * msg : 账号不存在
     * success : false
     */

    private String msg;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
