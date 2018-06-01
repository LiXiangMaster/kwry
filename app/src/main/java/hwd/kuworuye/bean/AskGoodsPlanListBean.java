package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class AskGoodsPlanListBean {


    /**
     * data : [{"id":"9a45ebaace024833bec32a7ea0a081e8","isNewRecord":false,"remarks":"咯哦哦哦哦","createDate":"2017-04-24 15:15:26","updateDate":"","statuslist":"","distributorId":"59c80fa6ceb14964be4246d5ff8248f8","distributorName":"张曼","userId":"83fcdc2ea95d44e39c69f5933f3baeea","cargoDate":"2017-04","cargoWeeks":"5","totalBox":"4"},{"id":"7eff65f7ec764986aa43711b99665a99","isNewRecord":false,"remarks":"急急急","createDate":"2017-04-24 15:08:46","updateDate":"","statuslist":"","distributorId":"d77043a8d5fa4b8bbdfb4f54996f9610","distributorName":"小王","userId":"83fcdc2ea95d44e39c69f5933f3baeea","cargoDate":"2017-04","cargoWeeks":"5","totalBox":"6"}]
     * success : true
     * size : 2
     */

    private boolean success;
    private int size;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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
         * id : 9a45ebaace024833bec32a7ea0a081e8
         * isNewRecord : false
         * remarks : 咯哦哦哦哦
         * createDate : 2017-04-24 15:15:26
         * updateDate :
         * statuslist :
         * distributorId : 59c80fa6ceb14964be4246d5ff8248f8
         * distributorName : 张曼
         * userId : 83fcdc2ea95d44e39c69f5933f3baeea
         * cargoDate : 2017-04
         * cargoWeeks : 5
         * totalBox : 4
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String statuslist;
        private String distributorId;
        private String distributorName;
        private String userId;
        private String cargoDate;
        private String cargoWeeks;
        private String totalBox;

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

        public String getStatuslist() {
            return statuslist;
        }

        public void setStatuslist(String statuslist) {
            this.statuslist = statuslist;
        }

        public String getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(String distributorId) {
            this.distributorId = distributorId;
        }

        public String getDistributorName() {
            return distributorName;
        }

        public void setDistributorName(String distributorName) {
            this.distributorName = distributorName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCargoDate() {
            return cargoDate;
        }

        public void setCargoDate(String cargoDate) {
            this.cargoDate = cargoDate;
        }

        public String getCargoWeeks() {
            return cargoWeeks;
        }

        public void setCargoWeeks(String cargoWeeks) {
            this.cargoWeeks = cargoWeeks;
        }

        public String getTotalBox() {
            return totalBox;
        }

        public void setTotalBox(String totalBox) {
            this.totalBox = totalBox;
        }
    }
}
