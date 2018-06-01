package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */

public class OtherCostHeXiaoDetailBean {


    /**
     * data : {"id":"0bae379e8d304af6bab86bd9775bef16","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","otherApplicationId":"336e3738dbe44a39828a7c6546d46046","approverUser":"0d23a676d8494af99a5380e4eee94238","title":"各种哈哈哈","explains":"停车","costs":"100","examineStatus":"1","approvalType":"0","remarks":"","createDate":"2017-04-05 13:42:01","photoList":[{"id":"1d02f8e930b442f19d8497b3850b88de","processId":"3a73223e95a04e1e99c63e915a5eb38c","type":"10","title":"222","imgs":"200","createDate":"2017-03-30 15:10:27"},{"id":"e466b58d4d334a9c98636cf706452b25","processId":"3a73223e95a04e1e99c63e915a5eb38c","type":"10","title":"222","imgs":"100","createDate":"2017-03-30 15:10:27"}],"approvalList":[{"id":"d2ccd8ddf54546b596cc1252cc222af1","isNewRecord":false,"user":{"id":"0d23a676d8494af99a5380e4eee94238","isNewRecord":false,"name":"区域经理","loginFlag":"1","roleNames":"","admin":false},"otherExamineId":"3a73223e95a04e1e99c63e915a5eb38c","approvalMemo":"审批通过","approvalTime":"2017-03-30 14:39:18"}]}
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
         * id : 0bae379e8d304af6bab86bd9775bef16
         * userId : d77043a8d5fa4b8bbdfb4f54996f9610
         * userName : 小王
         * otherApplicationId : 336e3738dbe44a39828a7c6546d46046
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * title : 各种哈哈哈
         * explains : 停车
         * costs : 100
         * examineStatus : 1
         * approvalType : 0
         * remarks :
         * createDate : 2017-04-05 13:42:01
         * photoList : [{"id":"1d02f8e930b442f19d8497b3850b88de","processId":"3a73223e95a04e1e99c63e915a5eb38c","type":"10","title":"222","imgs":"200","createDate":"2017-03-30 15:10:27"},{"id":"e466b58d4d334a9c98636cf706452b25","processId":"3a73223e95a04e1e99c63e915a5eb38c","type":"10","title":"222","imgs":"100","createDate":"2017-03-30 15:10:27"}]
         * approvalList : [{"id":"d2ccd8ddf54546b596cc1252cc222af1","isNewRecord":false,"user":{"id":"0d23a676d8494af99a5380e4eee94238","isNewRecord":false,"name":"区域经理","loginFlag":"1","roleNames":"","admin":false},"otherExamineId":"3a73223e95a04e1e99c63e915a5eb38c","approvalMemo":"审批通过","approvalTime":"2017-03-30 14:39:18"}]
         */

        private String id;
        private String userId;
        private String userName;
        private String otherApplicationId;
        private String approverUser;
        private String title;
        private String explains;
        private String costs;
        private String examineStatus;
        private String approvalType;
        private String remarks;
        private String createDate;
        private List<PhotoListBean> photoList;
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

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public List<ApprovalListBean> getApprovalList() {
            return approvalList;
        }

        public void setApprovalList(List<ApprovalListBean> approvalList) {
            this.approvalList = approvalList;
        }

        public static class PhotoListBean {
            /**
             * id : 1d02f8e930b442f19d8497b3850b88de
             * processId : 3a73223e95a04e1e99c63e915a5eb38c
             * type : 10
             * title : 222
             * imgs : 200
             * createDate : 2017-03-30 15:10:27
             */

            private String id;
            private String processId;
            private String type;
            private String title;
            private String imgs;
            private String createDate;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }

        public static class ApprovalListBean {
            /**
             * id : d2ccd8ddf54546b596cc1252cc222af1
             * isNewRecord : false
             * user : {"id":"0d23a676d8494af99a5380e4eee94238","isNewRecord":false,"name":"区域经理","loginFlag":"1","roleNames":"","admin":false}
             * otherExamineId : 3a73223e95a04e1e99c63e915a5eb38c
             * approvalMemo : 审批通过
             * approvalTime : 2017-03-30 14:39:18
             */

            private String id;
            private boolean isNewRecord;
            private UserBean user;
            private String otherExamineId;
            private String approvalMemo;
            private String approvalTime;

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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getOtherExamineId() {
                return otherExamineId;
            }

            public void setOtherExamineId(String otherExamineId) {
                this.otherExamineId = otherExamineId;
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

            public static class UserBean {
                /**
                 * id : 0d23a676d8494af99a5380e4eee94238
                 * isNewRecord : false
                 * name : 区域经理
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
}
