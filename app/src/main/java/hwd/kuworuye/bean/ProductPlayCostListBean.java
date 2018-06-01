package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ProductPlayCostListBean {

    /**
     * displayAList : {"pageNo":1,"pageSize":5,"count":1,"list":[{"id":"e55cec41eae147c9b3c80a18527de95d","isNewRecord":false,"createDate":"2017-03-30 20:04:15","updateDate":"2017-03-30 20:04:15","approverUser":"0d23a676d8494af99a5380e4eee94238","name":"李翔","objective":"3","target":"十个亿","content":"你猜啊","city":"莫斯科","ranges":"白宫","executionTime":"36","shelfNumber":"22","shelfArea":"未知","shelfDescription":"3","shelfBudget":"3","stackingNumber":"3","stackingForm":"3","stackingArea":"3","stackingBudget":"3","otherNumber":"3","otherForm":"3","otherArea":"3","otherBudget":"3","salesVolume":3,"ratio":"3","approvalStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"objectiveList":["3"]}]}
     * success : true
     */

    private DisplayAListBean displayAList;
    private boolean success;

    public DisplayAListBean getDisplayAList() {
        return displayAList;
    }

    public void setDisplayAList(DisplayAListBean displayAList) {
        this.displayAList = displayAList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DisplayAListBean {
        /**
         * pageNo : 1
         * pageSize : 5
         * count : 1
         * list : [{"id":"e55cec41eae147c9b3c80a18527de95d","isNewRecord":false,"createDate":"2017-03-30 20:04:15","updateDate":"2017-03-30 20:04:15","approverUser":"0d23a676d8494af99a5380e4eee94238","name":"李翔","objective":"3","target":"十个亿","content":"你猜啊","city":"莫斯科","ranges":"白宫","executionTime":"36","shelfNumber":"22","shelfArea":"未知","shelfDescription":"3","shelfBudget":"3","stackingNumber":"3","stackingForm":"3","stackingArea":"3","stackingBudget":"3","otherNumber":"3","otherForm":"3","otherArea":"3","otherBudget":"3","salesVolume":3,"ratio":"3","approvalStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"objectiveList":["3"]}]
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : e55cec41eae147c9b3c80a18527de95d
             * isNewRecord : false
             * createDate : 2017-03-30 20:04:15
             * updateDate : 2017-03-30 20:04:15
             * approverUser : 0d23a676d8494af99a5380e4eee94238
             * name : 李翔
             * objective : 3
             * target : 十个亿
             * content : 你猜啊
             * city : 莫斯科
             * ranges : 白宫
             * executionTime : 36
             * shelfNumber : 22
             * shelfArea : 未知
             * shelfDescription : 3
             * shelfBudget : 3
             * stackingNumber : 3
             * stackingForm : 3
             * stackingArea : 3
             * stackingBudget : 3
             * otherNumber : 3
             * otherForm : 3
             * otherArea : 3
             * otherBudget : 3
             * salesVolume : 3
             * ratio : 3
             * approvalStatus : 1
             * approvalType : 0
             * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}
             * objectiveList : ["3"]
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String approverUser;
            private String name;
            private String objective;
            private String target;
            private String content;
            private String city;
            private String ranges;
            private String executionTime;
            private String shelfNumber;
            private String shelfArea;
            private String shelfDescription;
            private String shelfBudget;
            private String stackingNumber;
            private String stackingForm;
            private String stackingArea;
            private String stackingBudget;
            private String otherNumber;
            private String otherForm;
            private String otherArea;
            private String otherBudget;
            private double salesVolume;
            private String ratio;
            private String approvalStatus;
            private String approvalType;
            private UserBean user;
            private List<String> objectiveList;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getObjective() {
                return objective;
            }

            public void setObjective(String objective) {
                this.objective = objective;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getRanges() {
                return ranges;
            }

            public void setRanges(String ranges) {
                this.ranges = ranges;
            }

            public String getExecutionTime() {
                return executionTime;
            }

            public void setExecutionTime(String executionTime) {
                this.executionTime = executionTime;
            }

            public String getShelfNumber() {
                return shelfNumber;
            }

            public void setShelfNumber(String shelfNumber) {
                this.shelfNumber = shelfNumber;
            }

            public String getShelfArea() {
                return shelfArea;
            }

            public void setShelfArea(String shelfArea) {
                this.shelfArea = shelfArea;
            }

            public String getShelfDescription() {
                return shelfDescription;
            }

            public void setShelfDescription(String shelfDescription) {
                this.shelfDescription = shelfDescription;
            }

            public String getShelfBudget() {
                return shelfBudget;
            }

            public void setShelfBudget(String shelfBudget) {
                this.shelfBudget = shelfBudget;
            }

            public String getStackingNumber() {
                return stackingNumber;
            }

            public void setStackingNumber(String stackingNumber) {
                this.stackingNumber = stackingNumber;
            }

            public String getStackingForm() {
                return stackingForm;
            }

            public void setStackingForm(String stackingForm) {
                this.stackingForm = stackingForm;
            }

            public String getStackingArea() {
                return stackingArea;
            }

            public void setStackingArea(String stackingArea) {
                this.stackingArea = stackingArea;
            }

            public String getStackingBudget() {
                return stackingBudget;
            }

            public void setStackingBudget(String stackingBudget) {
                this.stackingBudget = stackingBudget;
            }

            public String getOtherNumber() {
                return otherNumber;
            }

            public void setOtherNumber(String otherNumber) {
                this.otherNumber = otherNumber;
            }

            public String getOtherForm() {
                return otherForm;
            }

            public void setOtherForm(String otherForm) {
                this.otherForm = otherForm;
            }

            public String getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(String otherArea) {
                this.otherArea = otherArea;
            }

            public String getOtherBudget() {
                return otherBudget;
            }

            public void setOtherBudget(String otherBudget) {
                this.otherBudget = otherBudget;
            }

            public double getSalesVolume() {
                return salesVolume;
            }

            public void setSalesVolume(int salesVolume) {
                this.salesVolume = salesVolume;
            }

            public String getRatio() {
                return ratio;
            }

            public void setRatio(String ratio) {
                this.ratio = ratio;
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

            public List<String> getObjectiveList() {
                return objectiveList;
            }

            public void setObjectiveList(List<String> objectiveList) {
                this.objectiveList = objectiveList;
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
}
