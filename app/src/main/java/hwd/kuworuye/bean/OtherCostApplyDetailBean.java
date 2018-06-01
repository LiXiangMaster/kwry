package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class OtherCostApplyDetailBean {

    /**
     * data : {"id":"4a4d8607801b441cb5383d9a634c21da","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","approverUser":"0d23a676d8494af99a5380e4eee94238","title":"停车费用","explains":"停车","costs":"100","approvalStatus":"6","approvalType":"0","remarks":"","createDate":"2017-04-05 13:41:18","approvalList":[{"id":"fcb80508ee3d41849b065393cff1c24e","userId":"4","userName":"湖北营销中心","otherApplicationId":"4a4d8607801b441cb5383d9a634c21da","approvalMemo":"审批通过","approvalTime":"2017-04-05 13:41:57"},{"id":"3d0c1f9bbac84500aa9054c2cfcbfc09","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","otherApplicationId":"4a4d8607801b441cb5383d9a634c21da","approvalMemo":"待审批","approvalTime":"2017-04-05 13:41:18"}]}
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
         * id : 4a4d8607801b441cb5383d9a634c21da
         * userId : d77043a8d5fa4b8bbdfb4f54996f9610
         * userName : 小王
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * title : 停车费用
         * explains : 停车
         * costs : 100
         * approvalStatus : 6
         * approvalType : 0
         * remarks :
         * createDate : 2017-04-05 13:41:18
         * approvalList : [{"id":"fcb80508ee3d41849b065393cff1c24e","userId":"4","userName":"湖北营销中心","otherApplicationId":"4a4d8607801b441cb5383d9a634c21da","approvalMemo":"审批通过","approvalTime":"2017-04-05 13:41:57"},{"id":"3d0c1f9bbac84500aa9054c2cfcbfc09","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","otherApplicationId":"4a4d8607801b441cb5383d9a634c21da","approvalMemo":"待审批","approvalTime":"2017-04-05 13:41:18"}]
         */

        private String id;
        private String userId;
        private String userName;
        private String approverUser;
        private String title;
        private String explains;
        private String costs;
        private String approvalStatus;
        private String approvalType;
        private String remarks;
        private String createDate;
        private List<ApprovalListBean> approvalList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public List<ApprovalListBean> getApprovalList() {
            return approvalList;
        }

        public void setApprovalList(List<ApprovalListBean> approvalList) {
            this.approvalList = approvalList;
        }

        public static class ApprovalListBean {
            /**
             * id : fcb80508ee3d41849b065393cff1c24e
             * userId : 4
             * userName : 湖北营销中心
             * otherApplicationId : 4a4d8607801b441cb5383d9a634c21da
             * approvalMemo : 审批通过
             * approvalTime : 2017-04-05 13:41:57
             */

            private String id;
            private String userId;
            private String userName;
            private String otherApplicationId;
            private String approvalMemo;
            private String approvalTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getOtherApplicationId() {
                return otherApplicationId;
            }

            public void setOtherApplicationId(String otherApplicationId) {
                this.otherApplicationId = otherApplicationId;
            }

            public String getApprovalMemo() {
                return approvalMemo;
            }

            public void setApprovalMemo(String approvalMemo) {
                this.approvalMemo = approvalMemo;
            }

            public String getApprovalTime() {
                return approvalTime;
            }

            public void setApprovalTime(String approvalTime) {
                this.approvalTime = approvalTime;
            }
        }
    }
}
