package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class QueryAllSaleHeXiaoListBean {

    /**
     * PromotionEList : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"ee7f6019c3ca44dfa24126e9cfaefc5a","isNewRecord":false,"createDate":"2017-03-15 15:31:52","updateDate":"2017-03-21 16:10:19","promotionApplicationId":"4590c94759a142f48bd951c07f663246","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"name":"2","objective":"2","target":"2","city":"2","ranges":"2","executionTime":"2","content":"2","materiel":2,"salesVolume":2,"ratio":"2","examineStatus":"0","approvalType":"0","objectiveList":["2"]}]}
     * success : true
     */

    private PromotionEListBean PromotionEList;
    private boolean success;

    public PromotionEListBean getPromotionEList() {
        return PromotionEList;
    }

    public void setPromotionEList(PromotionEListBean PromotionEList) {
        this.PromotionEList = PromotionEList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class PromotionEListBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 1
         * list : [{"id":"ee7f6019c3ca44dfa24126e9cfaefc5a","isNewRecord":false,"createDate":"2017-03-15 15:31:52","updateDate":"2017-03-21 16:10:19","promotionApplicationId":"4590c94759a142f48bd951c07f663246","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"name":"2","objective":"2","target":"2","city":"2","ranges":"2","executionTime":"2","content":"2","materiel":2,"salesVolume":2,"ratio":"2","examineStatus":"0","approvalType":"0","objectiveList":["2"]}]
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
             * id : ee7f6019c3ca44dfa24126e9cfaefc5a
             * isNewRecord : false
             * createDate : 2017-03-15 15:31:52
             * updateDate : 2017-03-21 16:10:19
             * promotionApplicationId : 4590c94759a142f48bd951c07f663246
             * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}
             * name : 2
             * objective : 2
             * target : 2
             * city : 2
             * ranges : 2
             * executionTime : 2
             * content : 2
             * materiel : 2
             * salesVolume : 2
             * ratio : 2
             * examineStatus : 0
             * approvalType : 0
             * objectiveList : ["2"]
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String promotionApplicationId;
            private UserBean user;
            private String name;
            private String objective;
            private String target;
            private String city;
            private String ranges;
            private String executionTime;
            private String content;
            private String materiel;
            private double salesVolume;
            private String ratio;
            private String examineStatus;
            private String approvalType;
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

            public String getPromotionApplicationId() {
                return promotionApplicationId;
            }

            public void setPromotionApplicationId(String promotionApplicationId) {
                this.promotionApplicationId = promotionApplicationId;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMateriel() {
                return materiel;
            }

            public void setMateriel(String materiel) {
                this.materiel = materiel;
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
