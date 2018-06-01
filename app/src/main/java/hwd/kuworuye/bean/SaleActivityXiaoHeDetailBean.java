package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SaleActivityXiaoHeDetailBean {


    /**
     * kwPromotionExamine : {"id":"ee7f6019c3ca44dfa24126e9cfaefc5a","isNewRecord":false,"createDate":"2017-03-15 15:31:52","updateDate":"2017-03-30 11:28:07","promotionApplicationId":"4590c94759a142f48bd951c07f663246","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"approverUser":"8","name":"2","objective":"2","target":"2","city":"2","ranges":"2","executionTime":"2","content":"2","materiel":"2.00","salesVolume":2,"ratio":"2","examineStatus":"2","approvalType":"0","approvalMemo":"啊大大大","objectiveList":["2"]}
     * recordList : [{"id":"d04733cc1aaa4ccda3118f7c174194ed","isNewRecord":false,"user":{"id":"8","isNewRecord":false,"name":"湖北省区经理","loginFlag":"1","admin":false,"roleNames":""},"promotionExamineId":"ee7f6019c3ca44dfa24126e9cfaefc5a","approvalMemo":"审批驳回","approvalTime":"2017-03-21 16:10:19"},{"id":"9dbc95b2ed994a3e92a6598930cf010b","isNewRecord":false,"user":{"id":"8","isNewRecord":false,"name":"湖北省区经理","loginFlag":"1","admin":false,"roleNames":""},"promotionExamineId":"ee7f6019c3ca44dfa24126e9cfaefc5a","approvalMemo":"审批通过","approvalTime":"2017-03-30 11:22:32"},{"id":"5bae76025216495781ac17ee025844f7","isNewRecord":false,"user":{"id":"0d23a676d8494af99a5380e4eee94238","isNewRecord":false,"name":"区域经理","loginFlag":"1","admin":false,"roleNames":""},"promotionExamineId":"ee7f6019c3ca44dfa24126e9cfaefc5a","approvalMemo":"审批驳回","approvalTime":"2017-03-30 11:27:11"},{"id":"ddce8b3aa4dc4b5f87a0cfece05952d2","isNewRecord":false,"user":{"id":"0d23a676d8494af99a5380e4eee94238","isNewRecord":false,"name":"区域经理","loginFlag":"1","admin":false,"roleNames":""},"promotionExamineId":"ee7f6019c3ca44dfa24126e9cfaefc5a","approvalMemo":"审批通过","approvalTime":"2017-03-30 11:28:07"}]
     * itemList : [{"isNewRecord":true,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","quantity":200,"kwProductAttribute":{"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}},{"isNewRecord":true,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","quantity":100,"kwProductAttribute":{"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}}]
     * success : true
     */

    private KwPromotionExamineBean kwPromotionExamine;
    private boolean success;
    private List<RecordListBean> recordList;
    private List<ItemListBean> itemList;

    public KwPromotionExamineBean getKwPromotionExamine() {
        return kwPromotionExamine;
    }

    public void setKwPromotionExamine(KwPromotionExamineBean kwPromotionExamine) {
        this.kwPromotionExamine = kwPromotionExamine;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class KwPromotionExamineBean {
        /**
         * id : ee7f6019c3ca44dfa24126e9cfaefc5a
         * isNewRecord : false
         * createDate : 2017-03-15 15:31:52
         * updateDate : 2017-03-30 11:28:07
         * promotionApplicationId : 4590c94759a142f48bd951c07f663246
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}
         * approverUser : 8
         * name : 2
         * objective : 2
         * target : 2
         * city : 2
         * ranges : 2
         * executionTime : 2
         * content : 2
         * materiel : 2.00
         * salesVolume : 2
         * ratio : 2
         * examineStatus : 2
         * approvalType : 0
         * approvalMemo : 啊大大大
         * objectiveList : ["2"]
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String promotionApplicationId;
        private UserBean user;
        private String approverUser;
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
        private String approvalMemo;
        private List<String> objectiveList;
        private String activitySales;
        private String activityRatio;

        public boolean isNewRecord() {

            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

        public void setSalesVolume(double salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getActivitySales() {
            return activitySales;
        }

        public void setActivitySales(String activitySales) {
            this.activitySales = activitySales;
        }

        public String getActivityRatio() {
            return activityRatio;
        }

        public void setActivityRatio(String activityRatio) {
            this.activityRatio = activityRatio;
        }

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

        public String getApprovalMemo() {
            return approvalMemo;
        }

        public void setApprovalMemo(String approvalMemo) {
            this.approvalMemo = approvalMemo;
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

    public static class RecordListBean {
        /**
         * id : d04733cc1aaa4ccda3118f7c174194ed
         * isNewRecord : false
         * user : {"id":"8","isNewRecord":false,"name":"湖北省区经理","loginFlag":"1","admin":false,"roleNames":""}
         * promotionExamineId : ee7f6019c3ca44dfa24126e9cfaefc5a
         * approvalMemo : 审批驳回
         * approvalTime : 2017-03-21 16:10:19
         */

        private String id;
        private boolean isNewRecord;
        private UserBeanX user;
        private String promotionExamineId;
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

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public String getPromotionExamineId() {
            return promotionExamineId;
        }

        public void setPromotionExamineId(String promotionExamineId) {
            this.promotionExamineId = promotionExamineId;
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

        public static class UserBeanX {
            /**
             * id : 8
             * isNewRecord : false
             * name : 湖北省区经理
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

    public static class ItemListBean {
        /**
         * isNewRecord : true
         * productAttributeId : 598ab0dd553d465da82960046c3dd4ff
         * quantity : 200
         * kwProductAttribute : {"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}
         */

        private boolean isNewRecord;
        private String productAttributeId;
        private int quantity;
        private KwProductAttributeBean kwProductAttribute;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getProductAttributeId() {
            return productAttributeId;
        }

        public void setProductAttributeId(String productAttributeId) {
            this.productAttributeId = productAttributeId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public KwProductAttributeBean getKwProductAttribute() {
            return kwProductAttribute;
        }

        public void setKwProductAttribute(KwProductAttributeBean kwProductAttribute) {
            this.kwProductAttribute = kwProductAttribute;
        }

        public static class KwProductAttributeBean {
            /**
             * isNewRecord : true
             * number : 201703091969546842
             * taste : 1
             * price : 5.00
             * thumbnail : http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png
             * product : {"isNewRecord":true,"productName":"哇哈哈"}
             * tasteStr : 原味
             * specificationsStr : 100ml*50盒
             */

            private boolean isNewRecord;
            private String number;
            private String taste;
            private String price;
            private String thumbnail;
            private ProductBean product;
            private String tasteStr;
            private String specificationsStr;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getTaste() {
                return taste;
            }

            public void setTaste(String taste) {
                this.taste = taste;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public String getTasteStr() {
                return tasteStr;
            }

            public void setTasteStr(String tasteStr) {
                this.tasteStr = tasteStr;
            }

            public String getSpecificationsStr() {
                return specificationsStr;
            }

            public void setSpecificationsStr(String specificationsStr) {
                this.specificationsStr = specificationsStr;
            }

            public static class ProductBean {
                /**
                 * isNewRecord : true
                 * productName : 哇哈哈
                 */

                private boolean isNewRecord;
                private String productName;

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }
            }
        }
    }
}
