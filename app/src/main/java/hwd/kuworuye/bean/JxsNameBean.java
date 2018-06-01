package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class JxsNameBean {

    /**
     * data : [{"isNewRecord":true,"userId":"9","userName":"洪山区经销商"},{"isNewRecord":true,"userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王"},{"isNewRecord":true,"userId":"10","userName":"武昌区经销商"},{"isNewRecord":true,"userId":"f33e21f0c93946ffb2a70855686ee8fd","userName":"湖北省经销商(小三)"}]
     * success : true
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * isNewRecord : true
         * userId : 9
         * userName : 洪山区经销商
         */

        private boolean isNewRecord;
        private String userId;
        private String userName;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
