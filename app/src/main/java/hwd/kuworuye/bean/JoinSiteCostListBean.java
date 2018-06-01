package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JoinSiteCostListBean {

    /**
     * data : [{"id":"8fe0503734484b788ce661f90f891672","isNewRecord":false,"createDate":"2017-03-27 16:05:32","updateDate":"2017-03-27 16:25:18","approverUser":"","name":"中百超市1","address":"洪山区光谷大道1","phone":"18056785471","purchase":"小张","storeNumber":100,"annualOperation":10000,"similarBrands":"乳酸菌","annualSales":20000,"totalExpenses":50000,"approvalStatus":"3","approvalType":"0","user":{"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false}}]
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
         * id : 8fe0503734484b788ce661f90f891672
         * isNewRecord : false
         * createDate : 2017-03-27 16:05:32
         * updateDate : 2017-03-27 16:25:18
         * approverUser :
         * name : 中百超市1
         * address : 洪山区光谷大道1
         * phone : 18056785471
         * purchase : 小张
         * storeNumber : 100
         * annualOperation : 10000
         * similarBrands : 乳酸菌
         * annualSales : 20000
         * totalExpenses : 50000
         * approvalStatus : 3
         * approvalType : 0
         * user : {"id":"d77043a8d5fa4b8bbdfb4f54996f9610","isNewRecord":false,"name":"小王","loginFlag":"1","roleNames":"","admin":false}
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String approverUser;
        private String name;
        private String address;
        private String phone;
        private String purchase;
        private int storeNumber;
        private double annualOperation;
        private String similarBrands;
        private double annualSales;
        private double totalExpenses;
        private String approvalStatus;
        private String approvalType;
        private UserBean user;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPurchase() {
            return purchase;
        }

        public void setPurchase(String purchase) {
            this.purchase = purchase;
        }

        public int getStoreNumber() {
            return storeNumber;
        }

        public void setStoreNumber(int storeNumber) {
            this.storeNumber = storeNumber;
        }

        public double getAnnualOperation() {
            return annualOperation;
        }

        public void setAnnualOperation(int annualOperation) {
            this.annualOperation = annualOperation;
        }

        public String getSimilarBrands() {
            return similarBrands;
        }

        public void setSimilarBrands(String similarBrands) {
            this.similarBrands = similarBrands;
        }

        public double getAnnualSales() {
            return annualSales;
        }

        public void setAnnualSales(double annualSales) {
            this.annualSales = annualSales;
        }

        public double getTotalExpenses() {
            return totalExpenses;
        }

        public void setTotalExpenses(double totalExpenses) {
            this.totalExpenses = totalExpenses;
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
