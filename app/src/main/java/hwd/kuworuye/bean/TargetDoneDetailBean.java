package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TargetDoneDetailBean {

    /**
     * data : [{"id":"12502ca454b941939a8a95f22401e6fc","isNewRecord":false,"createDate":"2017-03-27 17:46:32","updateDate":"2017-03-27 17:46:32","orderNumber":"201703200954083683","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false},"userName":"1111","userPhone":"111","userAddress":"11","totalBox":11,"totalPrice":11,"totalWeight":11,"orderStatus":"7","orderMemo":"1","approvalType":"0"}]
     * success : true
     * size : 1
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
         * id : 12502ca454b941939a8a95f22401e6fc
         * isNewRecord : false
         * createDate : 2017-03-27 17:46:32
         * updateDate : 2017-03-27 17:46:32
         * orderNumber : 201703200954083683
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false}
         * userName : 1111
         * userPhone : 111
         * userAddress : 11
         * totalBox : 11
         * totalPrice : 11
         * totalWeight : 11
         * orderStatus : 7
         * orderMemo : 1
         * approvalType : 0
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

        public boolean isNewRecord() {
            return isNewRecord;
        }

        public void setNewRecord(boolean newRecord) {
            isNewRecord = newRecord;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
        }

        private double totalWeight;
        private String orderStatus;
        private String orderMemo;
        private String approvalType;

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

        public static class UserBean {
            /**
             * id : d77043a8d5fa4b8bbdfb4f54996f9610
             * isNewRecord : false
             * name : 小王
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
