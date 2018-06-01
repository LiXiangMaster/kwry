package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class OrderDetailBean {


    /**
     * giftlist : [{"isNewRecord":true,"quantity":6,"kwProductAttribute":{"isNewRecord":true,"product":{"isNewRecord":true,"productName":"农夫山泉"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}},{"isNewRecord":true,"quantity":5,"kwProductAttribute":{"isNewRecord":true,"product":{"isNewRecord":true,"productName":"旺仔"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}},{"isNewRecord":true,"quantity":4,"kwProductAttribute":{"isNewRecord":true,"product":{"isNewRecord":true,"productName":"AD"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}}]
     * kwOrder : {"id":"c66b33ea19d64af0868ef504900d6575","isNewRecord":false,"createDate":"2017-05-10 19:24:14","updateDate":"2017-05-10 19:24:15","orderNumber":"201705101213048978","user":{"id":"93bf5bce05eb4d808e2e9aee087cf58f","isNewRecord":false,"name":"111","loginFlag":"1","admin":false,"roleNames":""},"userName":"盲僧","userPhone":"18618618686","userAddress":"影流区召唤师峡谷上路中间防御塔野区小龙 光谷大道光谷广场步行街意澳大利亚大利风情街 不知道什么你猜哦哦哦噢噢噢也可以的 呵呵呵落寞now兔兔了我也来咯哦预计咯哦哦MVP涂涂抹抹哦哦咯噢噢噢哦哦老K咯了了了了来咯哦wwwYY咯哦哦哦www滚滚滚out咯噢噢噢哦哦我我我我我我下午拖拉据他来咯哦LIS","totalBox":60,"totalPrice":3840,"totalWeight":70,"driverName":"aaa","driverPhone":"13888888888","driverAddress":"gfdsjjglk","orderStatus":"7","orderMemo":"侧收了","approvalType":"0","refuseReason":"","paymentProof":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence%2FallImg%2FIMG20170510192328F91487B6-5AF3-4910-9CE9-FDAD1F512852.png"}
     * recordList : [{"id":"c0cebad5328e497abc2367e7277e0728","isNewRecord":false,"user":{"id":"cd1b1e6fc5424c7580b7197e456ab51b","isNewRecord":false,"name":"小C","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批驳回","approverTime":"2017-05-10 19:19:23"},{"id":"41aea9e5347242c58499d211bc45db16","isNewRecord":false,"user":{"id":"cd1b1e6fc5424c7580b7197e456ab51b","isNewRecord":false,"name":"小C","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批通过","approverTime":"2017-05-10 19:20:20"},{"id":"ee06b4a67c184106b263c9ff98e7e12b","isNewRecord":false,"user":{"id":"6","isNewRecord":false,"name":"发货员一","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批驳回","approverTime":"2017-05-10 19:20:45"},{"id":"35529d3f7d8e4067b79fb8e5ecb870d7","isNewRecord":false,"user":{"id":"cd1b1e6fc5424c7580b7197e456ab51b","isNewRecord":false,"name":"小C","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批通过","approverTime":"2017-05-10 19:21:41"},{"id":"3054a22b4ae04358baf73c595b2ed198","isNewRecord":false,"user":{"id":"6","isNewRecord":false,"name":"发货员一","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批通过","approverTime":"2017-05-10 19:22:08"},{"id":"f82815dbe0a1497f90996fa1bd8dc65e","isNewRecord":false,"user":{"id":"93bf5bce05eb4d808e2e9aee087cf58f","isNewRecord":false,"name":"111","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"已支付","approverTime":"2017-05-10 19:23:40"},{"id":"f16ffd48e2c8416eb318b87c63a94ae2","isNewRecord":false,"user":{"id":"6","isNewRecord":false,"name":"发货员一","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"审批通过","approverTime":"2017-05-10 19:24:05"},{"id":"8981ded8b3994759bc947de8da31e686","isNewRecord":false,"user":{"id":"93bf5bce05eb4d808e2e9aee087cf58f","isNewRecord":false,"name":"111","loginFlag":"1","admin":false,"roleNames":""},"orderId":"c66b33ea19d64af0868ef504900d6575","approverMemo":"订单完成","approverTime":"2017-05-10 19:24:15"}]
     * itemList : [{"isNewRecord":true,"productAttributeId":"01cafe4f84aa4bea9472b2757d7f2a9d","productName":"乳酸菌,原味,100ml*50盒","price":120,"quantity":30,"weight":0.3,"kwProductAttribute":{"isNewRecord":true,"thumbnail":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/3f3bd5e0c96a4e45a2fa884fe023a10d.png"}},{"isNewRecord":true,"productAttributeId":"43ff2d3abd2e46179b97cd171f79b0c4","productName":"红果果,原味,100ml*50盒","price":2,"quantity":20,"weight":2,"kwProductAttribute":{"isNewRecord":true,"thumbnail":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/6d70e9bf56bb42c79db3edc1462281cf.png"}},{"isNewRecord":true,"productAttributeId":"2bb60d85db15449b812a9e4fb1f4b667","productName":"AD,原味,100ml*50盒","price":20,"quantity":10,"weight":3,"kwProductAttribute":{"isNewRecord":true,"thumbnail":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/be9c12a982ab413db536a1b7d1614787.png"}}]
     * success : true
     */

    private KwOrderBean kwOrder;
    private boolean success;
    private List<GiftlistBean> giftlist;
    private List<RecordListBean> recordList;
    private List<ItemListBean> itemList;

    public KwOrderBean getKwOrder() {
        return kwOrder;
    }

    public void setKwOrder(KwOrderBean kwOrder) {
        this.kwOrder = kwOrder;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<GiftlistBean> getGiftlist() {
        return giftlist;
    }

    public void setGiftlist(List<GiftlistBean> giftlist) {
        this.giftlist = giftlist;
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

    public static class KwOrderBean {
        /**
         * id : c66b33ea19d64af0868ef504900d6575
         * isNewRecord : false
         * createDate : 2017-05-10 19:24:14
         * updateDate : 2017-05-10 19:24:15
         * orderNumber : 201705101213048978
         * user : {"id":"93bf5bce05eb4d808e2e9aee087cf58f","isNewRecord":false,"name":"111","loginFlag":"1","admin":false,"roleNames":""}
         * userName : 盲僧
         * userPhone : 18618618686
         * userAddress : 影流区召唤师峡谷上路中间防御塔野区小龙 光谷大道光谷广场步行街意澳大利亚大利风情街 不知道什么你猜哦哦哦噢噢噢也可以的 呵呵呵落寞now兔兔了我也来咯哦预计咯哦哦MVP涂涂抹抹哦哦咯噢噢噢哦哦老K咯了了了了来咯哦wwwYY咯哦哦哦www滚滚滚out咯噢噢噢哦哦我我我我我我下午拖拉据他来咯哦LIS
         * totalBox : 60
         * totalPrice : 3840
         * totalWeight : 70
         * driverName : aaa
         * driverPhone : 13888888888
         * driverAddress : gfdsjjglk
         * orderStatus : 7
         * orderMemo : 侧收了
         * approvalType : 0
         * refuseReason :
         * paymentProof : http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence%2FallImg%2FIMG20170510192328F91487B6-5AF3-4910-9CE9-FDAD1F512852.png
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String orderNumber;
        private UserBean user;
        private String userName;
        private String userPhone;
        private String userAddress;
        private int totalBox;
        private double totalPrice;
        private double totalWeight;
        private String driverName;
        private String driverPhone;
        private String driverAddress;
        private String orderStatus;
        private String orderMemo;
        private String approvalType;
        private String refuseReason;
        private String paymentProof;
        private String approverUserId;


        public boolean isNewRecord() {
            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

        public String getApproverUserId() {
            return approverUserId;
        }

        public void setApproverUserId(String approverUserId) {
            this.approverUserId = approverUserId;
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

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public int getTotalBox() {
            return totalBox;
        }

        public void setTotalBox(int totalBox) {
            this.totalBox = totalBox;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(int totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getDriverAddress() {
            return driverAddress;
        }

        public void setDriverAddress(String driverAddress) {
            this.driverAddress = driverAddress;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderMemo() {
            return orderMemo;
        }

        public void setOrderMemo(String orderMemo) {
            this.orderMemo = orderMemo;
        }

        public String getApprovalType() {
            return approvalType;
        }

        public void setApprovalType(String approvalType) {
            this.approvalType = approvalType;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public String getPaymentProof() {
            return paymentProof;
        }

        public void setPaymentProof(String paymentProof) {
            this.paymentProof = paymentProof;
        }

        public static class UserBean {
            /**
             * id : 93bf5bce05eb4d808e2e9aee087cf58f
             * isNewRecord : false
             * name : 111
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

    public static class GiftlistBean {
        /**
         * isNewRecord : true
         * quantity : 6
         * kwProductAttribute : {"isNewRecord":true,"product":{"isNewRecord":true,"productName":"农夫山泉"},"tasteStr":"原味","specificationsStr":"100ml*50盒"}
         */

        private boolean isNewRecord;
        private int quantity;
        private KwProductAttributeBean kwProductAttribute;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
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
             * product : {"isNewRecord":true,"productName":"农夫山泉"}
             * tasteStr : 原味
             * specificationsStr : 100ml*50盒
             */

            private boolean isNewRecord;
            private ProductBean product;
            private String tasteStr;
            private String specificationsStr;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
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
                 * productName : 农夫山泉
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

    public static class RecordListBean {
        /**
         * id : c0cebad5328e497abc2367e7277e0728
         * isNewRecord : false
         * user : {"id":"cd1b1e6fc5424c7580b7197e456ab51b","isNewRecord":false,"name":"小C","loginFlag":"1","admin":false,"roleNames":""}
         * orderId : c66b33ea19d64af0868ef504900d6575
         * approverMemo : 审批驳回
         * approverTime : 2017-05-10 19:19:23
         */

        private String id;
        private boolean isNewRecord;
        private UserBeanX user;
        private String orderId;
        private String approverMemo;
        private String approverTime;

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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getApproverMemo() {
            return approverMemo;
        }

        public void setApproverMemo(String approverMemo) {
            this.approverMemo = approverMemo;
        }

        public String getApproverTime() {
            return approverTime;
        }

        public void setApproverTime(String approverTime) {
            this.approverTime = approverTime;
        }

        public static class UserBeanX {
            /**
             * id : cd1b1e6fc5424c7580b7197e456ab51b
             * isNewRecord : false
             * name : 小C
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
         * productAttributeId : 01cafe4f84aa4bea9472b2757d7f2a9d
         * productName : 乳酸菌,原味,100ml*50盒
         * price : 120
         * quantity : 30
         * weight : 0.3
         * kwProductAttribute : {"isNewRecord":true,"thumbnail":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/3f3bd5e0c96a4e45a2fa884fe023a10d.png"}
         */

        private boolean isNewRecord;
        private String productAttributeId;
        private String productName;
        private double price;
        private int quantity;
        private double weight;
        private KwProductAttributeBeanX kwProductAttribute;

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public KwProductAttributeBeanX getKwProductAttribute() {
            return kwProductAttribute;
        }

        public void setKwProductAttribute(KwProductAttributeBeanX kwProductAttribute) {
            this.kwProductAttribute = kwProductAttribute;
        }

        public static class KwProductAttributeBeanX {
            /**
             * isNewRecord : true
             * thumbnail : http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/3f3bd5e0c96a4e45a2fa884fe023a10d.png
             */

            private boolean isNewRecord;
            private String thumbnail;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
