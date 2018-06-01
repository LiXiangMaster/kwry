package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class OtherCostApplyListBean {

    /**
     * data : [{"id":"59f1deed28bf440f9c0dec5a9453b678","isNewRecord":false,"createDate":"2017-04-02 21:59:07","updateDate":"2017-04-02 21:59:07","approverUser":"0d23a676d8494af99a5380e4eee94238","title":"吃饭费用","explains":"吃饭","costs":"80","approvalStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}},{"id":"f56dca17ac7643979d9e0c53748ee149","isNewRecord":false,"createDate":"2017-04-02 21:58:33","updateDate":"2017-04-02 21:58:33","approverUser":"0d23a676d8494af99a5380e4eee94238","title":"停车费用","explains":"停车","costs":"100","approvalStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}}]
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
         * id : 59f1deed28bf440f9c0dec5a9453b678
         * isNewRecord : false
         * createDate : 2017-04-02 21:59:07
         * updateDate : 2017-04-02 21:59:07
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * title : 吃饭费用
         * explains : 吃饭
         * costs : 80
         * approvalStatus : 1
         * approvalType : 0
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String approverUser;
        private String title;
        private String explains;
        private String costs;
        private String approvalStatus;
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

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
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
             * admin : false
             * roleNames :
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private String loginFlag;
            private boolean admin;
            private String roleNames;

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

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }
        }
    }
}
