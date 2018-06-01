package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class QueryAllOrderBean {

    /**
     * success : true
     * orderList : {"pageNo":1,"pageSize":10,"count":2,"list":[{"id":"12502ca454b941939a8a95f22401e6fc","isNewRecord":false,"createDate":"2017-03-23 09:58:38","updateDate":"2017-03-23 09:58:38","orderNumber":"201703200954083683","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false},"userName":"1111","userPhone":"111","userAddress":"11","totalBox":11,"totalPrice":11,"totalWeight":11,"orderStatus":"7","orderMemo":"1","approvalType":"0"},{"id":"604a5ce883cf430e9abb6d7c681f83bd","isNewRecord":false,"createDate":"2017-03-22 19:12:10","updateDate":"2017-03-22 19:12:10","orderNumber":"201703220401887104","user":{"id":"10","isNewRecord":false,"name":"武昌区经销商","loginFlag":"1","roleNames":"","admin":false},"userName":"1111","userPhone":"111","userAddress":"11","totalBox":11,"totalPrice":11,"totalWeight":11,"approverUserId":"11","orderStatus":"1","orderMemo":"1","approvalType":"0"}]}
     */

    private boolean success;
    private OrderListBean orderList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public OrderListBean getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderListBean orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 2
         * list : [{"id":"12502ca454b941939a8a95f22401e6fc","isNewRecord":false,"createDate":"2017-03-23 09:58:38","updateDate":"2017-03-23 09:58:38","orderNumber":"201703200954083683","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false},"userName":"1111","userPhone":"111","userAddress":"11","totalBox":11,"totalPrice":11,"totalWeight":11,"orderStatus":"7","orderMemo":"1","approvalType":"0"},{"id":"604a5ce883cf430e9abb6d7c681f83bd","isNewRecord":false,"createDate":"2017-03-22 19:12:10","updateDate":"2017-03-22 19:12:10","orderNumber":"201703220401887104","user":{"id":"10","isNewRecord":false,"name":"武昌区经销商","loginFlag":"1","roleNames":"","admin":false},"userName":"1111","userPhone":"111","userAddress":"11","totalBox":11,"totalPrice":11,"totalWeight":11,"approverUserId":"11","orderStatus":"1","orderMemo":"1","approvalType":"0"}]
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
             * id : 12502ca454b941939a8a95f22401e6fc
             * isNewRecord : false
             * createDate : 2017-03-23 09:58:38
             * updateDate : 2017-03-23 09:58:38
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
             * approverUserId : 11
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
            private int totalPrice;
            private double totalWeight;
            private String orderStatus;
            private String orderMemo;
            private String approvalType;
            private String approverUserId;

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

            public int getTotalPrice() {
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

            public String getApproverUserId() {
                return approverUserId;
            }

            public void setApproverUserId(String approverUserId) {
                this.approverUserId = approverUserId;
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
}
