package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/19.
 */

public class AppVerSionInfo {

    /**
     * data : {"id":"fd7f3abcbe97413786be919bfb1d768f","isNewRecord":false,"remarks":"","createDate":"2017-03-14 09:39:49","updateDate":"2017-03-14 09:39:49","varsionCode":"1.0","urls":"https://www.baidu.com","type":"1"}
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
         * id : fd7f3abcbe97413786be919bfb1d768f
         * isNewRecord : false
         * remarks :
         * createDate : 2017-03-14 09:39:49
         * updateDate : 2017-03-14 09:39:49
         * varsionCode : 1.0
         * urls : https://www.baidu.com
         * type : 1
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String varsionCode;
        private String urls;
        private String type;

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

        public String getVarsionCode() {
            return varsionCode;
        }

        public void setVarsionCode(String varsionCode) {
            this.varsionCode = varsionCode;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
