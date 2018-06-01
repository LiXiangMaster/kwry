package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class JoinSiteHxDetailBean {

    /**
     * data : {"address":"1","annualOperation":1,"annualSales":1,"approachApplicationId":"1a406497f2b7449b86064ad2371a2f27","approvalList":[{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"待审批","approvalTime":"2017-03-27 16:05:33","id":"4c04707c7b574544a0ea1baa10744e64","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王"},{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"审批通过","id":"257c53c71a624968be7471ae19cfad6c","userId":"0d23a676d8494af99a5380e4eee94238","userName":"区域经理"},{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"审批通过","id":"2e5f9d7d5e42420c9b1fbd4c1a76f159","userId":"8","userName":"湖北省区经理"}],"approvalType":"0","approverUser":"","examineStatus":"0","id":"1a406497f2b7449b86064ad2371a2f27","name":"1","paperPhotoList":[{"createDate":"2017-03-27 16:12:31","id":"5fcc6296021c4189a0e0b0e6f96f696e","imgs":"200","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"},{"createDate":"2017-03-27 16:12:31","id":"afa2fa5d33304f18b16dbf06dd5cf60e","imgs":"100","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"}],"phone":"1","purchase":"1","remarks":"","scenePhotoList":[{"createDate":"2017-03-27 16:12:31","id":"5fcc6296021c4189a0e0b0e6f96f696e","imgs":"200","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"},{"createDate":"2017-03-27 16:12:31","id":"afa2fa5d33304f18b16dbf06dd5cf60e","imgs":"100","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"}],"similarBrands":"1","storeNumber":1,"totalExpenses":1,"userId":"9","userName":"洪山区经销商"}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * address : 1
         * annualOperation : 1
         * annualSales : 1
         * approachApplicationId : 1a406497f2b7449b86064ad2371a2f27
         * approvalList : [{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"待审批","approvalTime":"2017-03-27 16:05:33","id":"4c04707c7b574544a0ea1baa10744e64","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王"},{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"审批通过","id":"257c53c71a624968be7471ae19cfad6c","userId":"0d23a676d8494af99a5380e4eee94238","userName":"区域经理"},{"approachApplicationId":"8fe0503734484b788ce661f90f891672","approvalMemo":"审批通过","id":"2e5f9d7d5e42420c9b1fbd4c1a76f159","userId":"8","userName":"湖北省区经理"}]
         * approvalType : 0
         * approverUser :
         * examineStatus : 0
         * id : 1a406497f2b7449b86064ad2371a2f27
         * name : 1
         * paperPhotoList : [{"createDate":"2017-03-27 16:12:31","id":"5fcc6296021c4189a0e0b0e6f96f696e","imgs":"200","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"},{"createDate":"2017-03-27 16:12:31","id":"afa2fa5d33304f18b16dbf06dd5cf60e","imgs":"100","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"}]
         * phone : 1
         * purchase : 1
         * remarks :
         * scenePhotoList : [{"createDate":"2017-03-27 16:12:31","id":"5fcc6296021c4189a0e0b0e6f96f696e","imgs":"200","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"},{"createDate":"2017-03-27 16:12:31","id":"afa2fa5d33304f18b16dbf06dd5cf60e","imgs":"100","processId":"8fe0503734484b788ce661f90f891672","title":"2","type":"7"}]
         * similarBrands : 1
         * storeNumber : 1
         * totalExpenses : 1
         * userId : 9
         * userName : 洪山区经销商
         */

        private String address;
        private double annualOperation;
        private double annualSales;
        private String approachApplicationId;
        private String approvalType;
        private String approverUser;
        private String examineStatus;
        private String id;
        private String name;
        private String phone;
        private String purchase;
        private String remarks;
        private String similarBrands;
        private int storeNumber;
        private double totalExpenses;
        private String userId;
        private String userName;
        private List<ApprovalListBean> approvalList;
        private List<PaperPhotoListBean> paperPhotoList;
        private List<ScenePhotoListBean> scenePhotoList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getAnnualOperation() {
            return annualOperation;
        }

        public void setAnnualOperation(int annualOperation) {
            this.annualOperation = annualOperation;
        }

        public double getAnnualSales() {
            return annualSales;
        }

        public void setAnnualSales(int annualSales) {
            this.annualSales = annualSales;
        }

        public String getApproachApplicationId() {
            return approachApplicationId;
        }

        public void setApproachApplicationId(String approachApplicationId) {
            this.approachApplicationId = approachApplicationId;
        }

        public String getApprovalType() {
            return approvalType;
        }

        public void setApprovalType(String approvalType) {
            this.approvalType = approvalType;
        }

        public String getApproverUser() {
            return approverUser;
        }

        public void setApproverUser(String approverUser) {
            this.approverUser = approverUser;
        }

        public String getExamineStatus() {
            return examineStatus;
        }

        public void setExamineStatus(String examineStatus) {
            this.examineStatus = examineStatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSimilarBrands() {
            return similarBrands;
        }

        public void setSimilarBrands(String similarBrands) {
            this.similarBrands = similarBrands;
        }

        public int getStoreNumber() {
            return storeNumber;
        }

        public void setStoreNumber(int storeNumber) {
            this.storeNumber = storeNumber;
        }

        public double getTotalExpenses() {
            return totalExpenses;
        }

        public void setTotalExpenses(int totalExpenses) {
            this.totalExpenses = totalExpenses;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<ApprovalListBean> getApprovalList() {
            return approvalList;
        }

        public void setApprovalList(List<ApprovalListBean> approvalList) {
            this.approvalList = approvalList;
        }

        public List<PaperPhotoListBean> getPaperPhotoList() {
            return paperPhotoList;
        }

        public void setPaperPhotoList(List<PaperPhotoListBean> paperPhotoList) {
            this.paperPhotoList = paperPhotoList;
        }

        public List<ScenePhotoListBean> getScenePhotoList() {
            return scenePhotoList;
        }

        public void setScenePhotoList(List<ScenePhotoListBean> scenePhotoList) {
            this.scenePhotoList = scenePhotoList;
        }

        public static class ApprovalListBean {
            /**
             * approachApplicationId : 8fe0503734484b788ce661f90f891672
             * approvalMemo : 待审批
             * approvalTime : 2017-03-27 16:05:33
             * id : 4c04707c7b574544a0ea1baa10744e64
             * userId : d77043a8d5fa4b8bbdfb4f54996f9610
             * userName : 小王
             */

            private String approachApplicationId;
            private String approvalMemo;
            private String approvalTime;
            private String id;
            private String userId;
            private String userName;

            public String getApproachApplicationId() {
                return approachApplicationId;
            }

            public void setApproachApplicationId(String approachApplicationId) {
                this.approachApplicationId = approachApplicationId;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class PaperPhotoListBean {
            /**
             * createDate : 2017-03-27 16:12:31
             * id : 5fcc6296021c4189a0e0b0e6f96f696e
             * imgs : 200
             * processId : 8fe0503734484b788ce661f90f891672
             * title : 2
             * type : 7
             */

            private String createDate;
            private String id;
            private String imgs;
            private String processId;
            private String title;
            private String type;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ScenePhotoListBean {
            /**
             * createDate : 2017-03-27 16:12:31
             * id : 5fcc6296021c4189a0e0b0e6f96f696e
             * imgs : 200
             * processId : 8fe0503734484b788ce661f90f891672
             * title : 2
             * type : 7
             */

            private String createDate;
            private String id;
            private String imgs;
            private String processId;
            private String title;
            private String type;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
