package hwd.kuworuye.bean;

/**
 * Created by Lixiang on 2017/3/16 0016.
 */
public class UserLoginBean {


    /**
     * data : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"remarks":"","createDate":"","updateDate":"","loginName":"18088888888","userName":"川普","userPassword":"a49d2ae49e83b2dd8a431b57c038cf55e17b54290f91988d8b046667","userType":"1","loginFlag":"1","isCertification":"1","isInit":"1","areaName":"洪山区","signCode":true}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * id : d77043a8d5fa4b8bbdfb4f54996f9610
         * isNewRecord : false
         * remarks :
         * createDate :
         * updateDate :
         * loginName : 18088888888
         * userName : 川普
         * userPassword : a49d2ae49e83b2dd8a431b57c038cf55e17b54290f91988d8b046667
         * userType : 1
         * loginFlag : 1
         * isCertification : 1
         * isInit : 1
         * areaName : 洪山区
         * signCode : true
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String loginName;
        private String userName;
        private String userPassword;
        private String userType;
        private String loginFlag;
        private String isCertification;
        private String isInit;
        private String areaName;
        private boolean signCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getIsCertification() {
            return isCertification;
        }

        public void setIsCertification(String isCertification) {
            this.isCertification = isCertification;
        }

        public String getIsInit() {
            return isInit;
        }

        public void setIsInit(String isInit) {
            this.isInit = isInit;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public boolean isSignCode() {
            return signCode;
        }

        public void setSignCode(boolean signCode) {
            this.signCode = signCode;
        }
    }
}
