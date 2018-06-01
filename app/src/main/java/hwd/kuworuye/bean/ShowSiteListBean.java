package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class ShowSiteListBean {

    /**
     * data : [{"id":"1","isNewRecord":false,"createDate":"2017-03-15 15:50:07","updateDate":"2017-03-15 15:50:07","userId":"1","name":"程杰","phone":"18086302679","region":"湖北省武汉市洪山区","address":"光谷大道","isDefault":"0"}]
     * success : true
     * size : 1
     */

    private boolean success;
    private String size;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * isNewRecord : false
         * createDate : 2017-03-15 15:50:07
         * updateDate : 2017-03-15 15:50:07
         * userId : 1
         * name : 程杰
         * phone : 18086302679
         * region : 湖北省武汉市洪山区
         * address : 光谷大道
         * isDefault : 0
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String userId;
        private String name;
        private String phone;
        private String region;
        private String address;
        private String isDefault;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }
    }
}
