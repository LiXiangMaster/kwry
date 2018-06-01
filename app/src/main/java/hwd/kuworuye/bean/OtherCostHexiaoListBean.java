package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class OtherCostHexiaoListBean {

    /**
     * data : [{"id":"3a73223e95a04e1e99c63e915a5eb38c","isNewRecord":false,"remarks":"哈哈哈哈","createDate":"2017-03-15 15:28:12","updateDate":"2017-03-30 15:10:41","otherApplicationId":"5bba5588202f459c9e15f0d387277ce3","approverUser":"0d23a676d8494af99a5380e4eee94238","title":"中百超市","explains":"珞喻东路","costs":"100","examineStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false}}]
     * success : true
     * size : 1
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
         * id : 3a73223e95a04e1e99c63e915a5eb38c
         * isNewRecord : false
         * remarks : 哈哈哈哈
         * createDate : 2017-03-15 15:28:12
         * updateDate : 2017-03-30 15:10:41
         * otherApplicationId : 5bba5588202f459c9e15f0d387277ce3
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * title : 中百超市
         * explains : 珞喻东路
         * costs : 100
         * examineStatus : 1
         * approvalType : 0
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false}
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String otherApplicationId;
        private String approverUser;
        private String title;
        private String explains;
        private String costs;
        private String examineStatus;
        private String approvalType;
        private UserBean user;

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

        public String getOtherApplicationId() {
            return otherApplicationId;
        }

        public void setOtherApplicationId(String otherApplicationId) {
            this.otherApplicationId = otherApplicationId;
        }

        public String getApproverUser() {
            return approverUser;
        }

        public void setApproverUser(String approverUser) {
            this.approverUser = approverUser;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getCosts() {
            return costs;
        }

        public void setCosts(String costs) {
            this.costs = costs;
        }

        public String getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(String examineStatus) {
            this.examineStatus = examineStatus;
        }

        public String getApprovalType() {
            return approvalType;
        }

        public void setApprovalType(String approvalType) {
            this.approvalType = approvalType;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : d77043a8d5fa4b8bbdfb4f54996f9610
             * isNewRecord : false
             * name : 小王
             * loginFlag : 1
             * roleNames :
             * admin : false
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private String loginFlag;
            private String roleNames;
            private boolean admin;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }
        }
    }
}
