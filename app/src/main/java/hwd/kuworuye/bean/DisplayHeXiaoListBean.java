package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class DisplayHeXiaoListBean {

    /**
     * displayEList : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"115f22c9276046e8be240ac3ffe85824","isNewRecord":false,"remarks":"2","createDate":"2017-03-15 15:50:07","updateDate":"2017-03-22 15:00:19","displayApplicationId":"7203fd9f960545129a10f7b4c8f1103b","name":"23","objective":",123,","target":"113","content":"13","city":"123","ranges":"123","executionTime":"1123","shelfNumber":"13","shelfArea":"133","shelfDescription":"123","shelfBudget":"123","stackingNumber":"13","stackingForm":"131","stackingArea":"123","stackingBudget":"13","otherNumber":"123","otherForm":"123","otherArea":"3213","otherBudget":"132","salesVolume":13,"ratio":"132","examineStatus":"0","approvalType":"1","approvalMemo":"嗷嗷待食","user":{"id":"9","isNewRecord":false,"loginFlag":"1","roleNames":"","admin":false},"objectiveList":["123"]}]}
     * success : true
     */

    private DisplayEListBean displayEList;
    private boolean success;

    public DisplayEListBean getDisplayEList() {
        return displayEList;
    }

    public void setDisplayEList(DisplayEListBean displayEList) {
        this.displayEList = displayEList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DisplayEListBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 1
         * list : [{"id":"115f22c9276046e8be240ac3ffe85824","isNewRecord":false,"remarks":"2","createDate":"2017-03-15 15:50:07","updateDate":"2017-03-22 15:00:19","displayApplicationId":"7203fd9f960545129a10f7b4c8f1103b","name":"23","objective":",123,","target":"113","content":"13","city":"123","ranges":"123","executionTime":"1123","shelfNumber":"13","shelfArea":"133","shelfDescription":"123","shelfBudget":"123","stackingNumber":"13","stackingForm":"131","stackingArea":"123","stackingBudget":"13","otherNumber":"123","otherForm":"123","otherArea":"3213","otherBudget":"132","salesVolume":13,"ratio":"132","examineStatus":"0","approvalType":"1","approvalMemo":"嗷嗷待食","user":{"id":"9","isNewRecord":false,"loginFlag":"1","roleNames":"","admin":false},"objectiveList":["123"]}]
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
             * id : 115f22c9276046e8be240ac3ffe85824
             * isNewRecord : false
             * remarks : 2
             * createDate : 2017-03-15 15:50:07
             * updateDate : 2017-03-22 15:00:19
             * displayApplicationId : 7203fd9f960545129a10f7b4c8f1103b
             * name : 23
             * objective : ,123,
             * target : 113
             * content : 13
             * city : 123
             * ranges : 123
             * executionTime : 1123
             * shelfNumber : 13
             * shelfArea : 133
             * shelfDescription : 123
             * shelfBudget : 123
             * stackingNumber : 13
             * stackingForm : 131
             * stackingArea : 123
             * stackingBudget : 13
             * otherNumber : 123
             * otherForm : 123
             * otherArea : 3213
             * otherBudget : 132
             * salesVolume : 13
             * ratio : 132
             * examineStatus : 0
             * approvalType : 1
             * approvalMemo : 嗷嗷待食
             * user : {"id":"9","isNewRecord":false,"loginFlag":"1","roleNames":"","admin":false}
             * objectiveList : ["123"]
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String displayApplicationId;
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
            private String examineStatus;
            private String approvalType;
            private String approvalMemo;
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

            public String getDisplayApplicationId() {
                return displayApplicationId;
            }

            public void setDisplayApplicationId(String displayApplicationId) {
                this.displayApplicationId = displayApplicationId;
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

            public String getApprovalMemo() {
                return approvalMemo;
            }

            public void setApprovalMemo(String approvalMemo) {
                this.approvalMemo = approvalMemo;
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
                 * id : 9
                 * isNewRecord : false
                 * loginFlag : 1
                 * roleNames :
                 * admin : false
                 */

                private String id;
                private boolean isNewRecord;
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
