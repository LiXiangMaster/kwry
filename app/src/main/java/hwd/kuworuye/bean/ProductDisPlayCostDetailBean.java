package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ProductDisPlayCostDetailBean {


    /**
     * kwDisplayApplication : {"id":"7203fd9f960545129a10f7b4c8f1103b","isNewRecord":false,"remarks":"2","createDate":"2017-03-15 10:31:00","updateDate":"2017-03-15 15:50:07","approverUser":"11","name":"2","objective":"2","target":"2","content":"2","city":"2","ranges":"2","executionTime":"2","shelfNumber":"2","shelfArea":"2","shelfDescription":"2","shelfBudget":"2","stackingNumber":"2","stackingForm":"2","stackingArea":"2","stackingBudget":"2","otherNumber":"2","otherForm":"2","otherArea":"2","otherBudget":"2","salesVolume":2,"ratio":"2","approvalStatus":"6","approvalType":"1","approvalMemo":"ggggggggg","user":{"id":"9","isNewRecord":false,"name":"洪山区经销商","loginFlag":"1","roleNames":"","admin":false},"objectiveList":["2"]}
     * recordList : [{"id":"0677da676aaa4d79bd092d98495ca554","isNewRecord":false,"user":{"id":"4","isNewRecord":false,"name":"湖北营销中心","loginFlag":"1","roleNames":"","admin":false},"displayApplicationId":"7203fd9f960545129a10f7b4c8f1103b","approvalMemo":"审批通过","approvalTime":"2017-03-15 15:50:07"},{"id":"79c43a224390417895b34aefe4b75d40","isNewRecord":false,"user":{"id":"4","isNewRecord":false,"name":"湖北营销中心","loginFlag":"1","roleNames":"","admin":false},"displayApplicationId":"7203fd9f960545129a10f7b4c8f1103b","approvalMemo":"审批通过","approvalTime":"2017-03-15 15:47:55"}]
     * itemList : [{"isNewRecord":true,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","quantity":"700","kwProductAttribute":{"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}},{"isNewRecord":true,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","quantity":"340","kwProductAttribute":{"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}}]
     * success : true
     */

    private KwDisplayApplicationBean kwDisplayApplication;
    private boolean success;
    private List<RecordListBean> recordList;
    private List<ItemListBean> itemList;

    public KwDisplayApplicationBean getKwDisplayApplication() {
        return kwDisplayApplication;
    }

    public void setKwDisplayApplication(KwDisplayApplicationBean kwDisplayApplication) {
        this.kwDisplayApplication = kwDisplayApplication;
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

    public static class KwDisplayApplicationBean {
        /**
         * id : 7203fd9f960545129a10f7b4c8f1103b
         * isNewRecord : false
         * remarks : 2
         * createDate : 2017-03-15 10:31:00
         * updateDate : 2017-03-15 15:50:07
         * approverUser : 11
         * name : 2
         * objective : 2
         * target : 2
         * content : 2
         * city : 2
         * ranges : 2
         * executionTime : 2
         * shelfNumber : 2
         * shelfArea : 2
         * shelfDescription : 2
         * shelfBudget : 2
         * stackingNumber : 2
         * stackingForm : 2
         * stackingArea : 2
         * stackingBudget : 2
         * otherNumber : 2
         * otherForm : 2
         * otherArea : 2
         * otherBudget : 2
         * salesVolume : 2
         * ratio : 2
         * approvalStatus : 6
         * approvalType : 1
         * approvalMemo : ggggggggg
         * user : {"id":"9","isNewRecord":false,"name":"洪山区经销商","loginFlag":"1","roleNames":"","admin":false}
         * objectiveList : ["2"]
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
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
        private String approvalMemo;
        private UserBean user;
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

    public static class RecordListBean {
        /**
         * id : 0677da676aaa4d79bd092d98495ca554
         * isNewRecord : false
         * user : {"id":"4","isNewRecord":false,"name":"湖北营销中心","loginFlag":"1","roleNames":"","admin":false}
         * displayApplicationId : 7203fd9f960545129a10f7b4c8f1103b
         * approvalMemo : 审批通过
         * approvalTime : 2017-03-15 15:50:07
         */

        private String id;
        private boolean isNewRecord;
        private UserBeanX user;
        private String displayApplicationId;
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

        public String getDisplayApplicationId() {
            return displayApplicationId;
        }

        public void setDisplayApplicationId(String displayApplicationId) {
            this.displayApplicationId = displayApplicationId;
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
             * id : 4
             * isNewRecord : false
             * name : 湖北营销中心
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
         * productAttributeId : 598ab0dd553d465da82960046c3dd4ff
         * quantity : 700
         * kwProductAttribute : {"isNewRecord":true,"number":"201703091969546842","taste":"1","price":"5.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","product":{"isNewRecord":true,"productName":"哇哈哈"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}
         */

        private boolean isNewRecord;
        private String productAttributeId;
        private String quantity;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
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
