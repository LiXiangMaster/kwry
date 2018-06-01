package hwd.kuworuye.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JoinSiteCostApplyDetailBean {

    /**
     * data : {"id":"4590c94759a142f48bd951c07f663246","approverUser":"0d23a676d8494af99a5380e4eee94238","name":"中百超市","address":"洪山区光谷大道","phone":"18056785478","purchase":"小王","storeNumber":10,"annualOperation":1000,"similarBrands":"乳酸菌","annualSales":2000,"totalExpenses":5000,"approvalStatus":"1","approvalType":"0","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","approvalList":[{"id":"82d9fb0da45e4b4caa2f8dc2a61172c8","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","approachApplicationId":"4590c94759a142f48bd951c07f663246","approvalMemo":"待审批","approvalTime":"2017-03-29 16:41:58"}],"photoList":[{"id":"bd9e0a99df6f408fa21f79c50ec31678","processId":"4590c94759a142f48bd951c07f663246","type":"7","title":"a1","imgs":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png","createDate":"2017-03-29 16:41:48"}]}
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
         * id : 4590c94759a142f48bd951c07f663246
         * approverUser : 0d23a676d8494af99a5380e4eee94238
         * name : 中百超市
         * address : 洪山区光谷大道
         * phone : 18056785478
         * purchase : 小王
         * storeNumber : 10
         * annualOperation : 1000
         * similarBrands : 乳酸菌
         * annualSales : 2000
         * totalExpenses : 5000
         * approvalStatus : 1
         * approvalType : 0
         * userId : d77043a8d5fa4b8bbdfb4f54996f9610
         * userName : 小王
         * approvalList : [{"id":"82d9fb0da45e4b4caa2f8dc2a61172c8","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","userName":"小王","approachApplicationId":"4590c94759a142f48bd951c07f663246","approvalMemo":"待审批","approvalTime":"2017-03-29 16:41:58"}]
         * photoList : [{"id":"bd9e0a99df6f408fa21f79c50ec31678","processId":"4590c94759a142f48bd951c07f663246","type":"7","title":"a1","imgs":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png","createDate":"2017-03-29 16:41:48"}]
         */

        private String id;
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
        private String userId;
        private String userName;
        private String remarks;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        private List<ApprovalListBean> approvalList;
        private List<PhotoListBean> photoList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setAnnualSales(int annualSales) {
            this.annualSales = annualSales;
        }

        public double getTotalExpenses() {
            return totalExpenses;
        }

        public void setTotalExpenses(int totalExpenses) {
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

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public static class ApprovalListBean {
            /**
             * id : 82d9fb0da45e4b4caa2f8dc2a61172c8
             * userId : d77043a8d5fa4b8bbdfb4f54996f9610
             * userName : 小王
             * approachApplicationId : 4590c94759a142f48bd951c07f663246
             * approvalMemo : 待审批
             * approvalTime : 2017-03-29 16:41:58
             */

            private String id;
            private String userId;
            private String userName;
            private String approachApplicationId;
            private String approvalMemo;
            private String approvalTime;

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
        }

        public static class PhotoListBean implements Parcelable{
            /**
             * id : bd9e0a99df6f408fa21f79c50ec31678
             * processId : 4590c94759a142f48bd951c07f663246
             * type : 7
             * title : a1
             * imgs : http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png
             * createDate : 2017-03-29 16:41:48
             */

            private String id;
            private String processId;
            private String type;
            private String title;
            private String imgs;
            private String createDate;

            protected PhotoListBean(Parcel in) {
                id = in.readString();
                processId = in.readString();
                type = in.readString();
                title = in.readString();
                imgs = in.readString();
                createDate = in.readString();
            }

            public static final Creator<PhotoListBean> CREATOR = new Creator<PhotoListBean>() {
                @Override
                public PhotoListBean createFromParcel(Parcel in) {
                    return new PhotoListBean(in);
                }

                @Override
                public PhotoListBean[] newArray(int size) {
                    return new PhotoListBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(id);
                parcel.writeString(processId);
                parcel.writeString(type);
                parcel.writeString(title);
                parcel.writeString(imgs);
                parcel.writeString(createDate);
            }
        }
    }
}
