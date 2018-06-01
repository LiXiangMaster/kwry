package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SaleApplyDetailBean {

    /**
     * kwPromotionApplication : {"id":"e701947e2e8c4b919e8a5695b4e3fce3","isNewRecord":false,"createDate":"2017-04-10 09:40:38","updateDate":"2017-04-10 09:40:38","approverUser":"0d23a676d8494af99a5380e4eee94238","name":"空","objective":"提升认知,推动渠道","target":"你现在","executionTime":"嗯啦","city":"洪山区","ranges":"模型","content":"根据","materiel":"你现在","salesVolume":1,"ratio":"1","approvalStatus":"1","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""},"objectiveList":["提升认知","推动渠道"]}
     * recordList : [{"id":"1","isNewRecord":false,"user":{"id":"9","isNewRecord":false,"name":"洪山区经销商","loginFlag":"1","roleNames":"","admin":false},"promotionApplicationId":"68df7a1a8ea648239374c242b7e46193","approvalMemo":"通过","approvalTime":"2017-03-20 16:56:38"},{"id":"2","isNewRecord":false,"user":{"id":"9","isNewRecord":false,"name":"洪山区经销商","loginFlag":"1","roleNames":"","admin":false},"promotionApplicationId":"68df7a1a8ea648239374c242b7e46193","approvalMemo":"驳回","approvalTime":"2017-03-20 16:57:00"}]
     * itemList : [{"isNewRecord":true,"productAttributeId":"846d22d8306e429c9570b3eac45e4c32","quantity":2,"kwProductAttribute":{"isNewRecord":true,"number":"201703140933523368","taste":"1","price":"50.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","product":{"isNewRecord":true,"productName":"营养快线"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}}]
     * success : true
     */

    private KwPromotionApplicationBean kwPromotionApplication;
    private boolean success;
    private List<RecordListBean> recordList;
    private List<ItemListBean> itemList;

    public KwPromotionApplicationBean getKwPromotionApplication() {
        return kwPromotionApplication;
    }

    public void setKwPromotionApplication(KwPromotionApplicationBean kwPromotionApplication) {
        this.kwPromotionApplication = kwPromotionApplication;
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

    public static class KwPromotionApplicationBean {
        /**
         * id : e701947e2e8c4b919e8a5695b4e3fce3
         * isNewRecord : false
         * createDate : 2017-04-10 09:40:38
         * updateDate : 2017-04-10 09:40:38
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * name : 空
         * objective : 提升认知,推动渠道
         * target : 你现在
         * executionTime : 嗯啦
         * city : 洪山区
         * ranges : 模型
         * content : 根据
         * materiel : 你现在
         * salesVolume : 1
         * ratio : 1
         * approvalStatus : 1
         * approvalType : 0
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","admin":false,"roleNames":""}
         * objectiveList : ["提升认知","推动渠道"]
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String approverUser;
        private String name;
        private String objective;
        private String target;
        private String executionTime;
        private String city;
        private String ranges;
        private String content;
        private String materiel;
        private double salesVolume;
        private String ratio;
        private String approvalStatus;
        private String approvalType;
        private String activitySales;
        private String activityRatio;

        public String getActivitySales() {
            return activitySales;
        }

        public void setActivitySales(String activitySales) {
            this.activitySales = activitySales;
        }

        public void setSalesVolume(double salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getActivityRatio() {
            return activityRatio;
        }

        public void setActivityRatio(String activityRatio) {
            this.activityRatio = activityRatio;
        }

        public String getApprovalMemo() {
            return approvalMemo;
        }

        public void setApprovalMemo(String approvalMemo) {
            this.approvalMemo = approvalMemo;
        }

        public boolean isNewRecord() {
            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

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

        public String getExecutionTime() {
            return executionTime;
        }

        public void setExecutionTime(String executionTime) {
            this.executionTime = executionTime;
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

    public static class RecordListBean {
        /**
         * id : 1
         * isNewRecord : false
         * user : {"id":"9","isNewRecord":false,"name":"洪山区经销商","loginFlag":"1","roleNames":"","admin":false}
         * promotionApplicationId : 68df7a1a8ea648239374c242b7e46193
         * approvalMemo : 通过
         * approvalTime : 2017-03-20 16:56:38
         */

        private String id;
        private boolean isNewRecord;
        private UserBeanX user;
        private String promotionApplicationId;
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

        public String getPromotionApplicationId() {
            return promotionApplicationId;
        }

        public void setPromotionApplicationId(String promotionApplicationId) {
            this.promotionApplicationId = promotionApplicationId;
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
             * id : 9
             * isNewRecord : false
             * name : 洪山区经销商
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

    public static class ItemListBean {
        /**
         * isNewRecord : true
         * productAttributeId : 846d22d8306e429c9570b3eac45e4c32
         * quantity : 2
         * kwProductAttribute : {"isNewRecord":true,"number":"201703140933523368","taste":"1","price":"50.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","product":{"isNewRecord":true,"productName":"营养快线"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}
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
             * number : 201703140933523368
             * taste : 1
             * price : 50.00
             * thumbnail : http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png
             * product : {"isNewRecord":true,"productName":"营养快线"}
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
                 * productName : 营养快线
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
