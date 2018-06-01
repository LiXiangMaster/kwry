package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class JoinSiteHeXiaoBean {

    /**
     * data : [{"id":"49094f08f95843dea27e0962c3cc41de","isNewRecord":false,"remarks":"不想通过","createDate":"2017-03-15 15:14:57","updateDate":"2017-03-15 15:14:56","approachApplicationId":"49094f08f95843dea27e0962c3cc41de","name":"2","address":"2","phone":"2","purchase":"2","storeNumber":2,"annualOperation":2,"similarBrands":"2","annualSales":2,"totalExpenses":2,"examineStatus":"0","approvalType":"0","user":{"id":"10","isNewRecord":false,"name":"武昌区经销商","loginFlag":"1","admin":false,"roleNames":""}}]
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
         * id : 49094f08f95843dea27e0962c3cc41de
         * isNewRecord : false
         * remarks : 不想通过
         * createDate : 2017-03-15 15:14:57
         * updateDate : 2017-03-15 15:14:56
         * approachApplicationId : 49094f08f95843dea27e0962c3cc41de
         * name : 2
         * address : 2
         * phone : 2
         * purchase : 2
         * storeNumber : 2
         * annualOperation : 2
         * similarBrands : 2
         * annualSales : 2
         * totalExpenses : 2
         * examineStatus : 0
         * approvalType : 0
         * user : {"id":"10","isNewRecord":false,"name":"武昌区经销商","loginFlag":"1","admin":false,"roleNames":""}
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String approachApplicationId;
        private String name;
        private String address;
        private String phone;
        private String purchase;
        private int storeNumber;
        private double annualOperation;
        private String similarBrands;
        private double annualSales;
        private double totalExpenses;
        private String examineStatus;
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

        public String getApproachApplicationId() {
            return approachApplicationId;
        }

        public void setApproachApplicationId(String approachApplicationId) {
            this.approachApplicationId = approachApplicationId;
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

        public void setAnnualSales(int annualSales) {
            this.annualSales = annualSales;
        }

        public double getTotalExpenses() {
            return totalExpenses;
        }

        public void setTotalExpenses(int totalExpenses) {
            this.totalExpenses = totalExpenses;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 10
             * isNewRecord : false
             * name : 武昌区经销商
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
