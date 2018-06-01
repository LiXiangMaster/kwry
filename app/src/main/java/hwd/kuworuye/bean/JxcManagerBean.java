package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class JxcManagerBean {

    /**
     * data : [{"id":"234f6111bbe042afb56e88451ca06ec4","isNewRecord":false,"createDate":"2017-04-21 10:35:20","updateDate":"2017-04-21 10:35:20","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"58","totalBox":"2"},{"id":"d51899c3fbb24878b2d0e69934ec9ab2","isNewRecord":false,"createDate":"2017-04-21 10:33:39","updateDate":"2017-04-21 10:33:39","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"56","totalBox":"2"},{"id":"19b4563d54d940f285bfcc443ad76189","isNewRecord":false,"createDate":"2017-04-21 10:33:02","updateDate":"2017-04-21 10:33:02","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"54","totalBox":"2"},{"id":"454f3308366e40229a6a82a7a3af5c33","isNewRecord":false,"createDate":"2017-04-21 10:30:43","updateDate":"2017-04-21 10:30:43","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"52","totalBox":"3"},{"id":"0cda54cd79404ed1859ce11fa23b0aa3","isNewRecord":false,"createDate":"2017-04-20 10:56:14","updateDate":"2017-04-20 10:56:14","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"49","totalBox":"5"},{"id":"ddc9589894c0475fb1a37b2132c1b629","isNewRecord":false,"createDate":"2017-04-18 15:01:03","updateDate":"2017-04-18 15:01:03","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"44","totalBox":"4"},{"id":"8e6cedd857ef49ca844d95c04cf943ff","isNewRecord":false,"createDate":"2017-04-18 14:54:20","updateDate":"2017-04-18 14:54:20","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"40","totalBox":"6"},{"id":"d00021e6a89f406186426b4538306656","isNewRecord":false,"createDate":"2017-04-18 14:50:27","updateDate":"2017-04-18 14:50:27","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"34","totalBox":"7"},{"id":"c9ac46be96624cb5aae76c5f211918c0","isNewRecord":false,"createDate":"2017-04-18 14:17:13","updateDate":"2017-04-18 14:17:13","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"27","totalBox":"4"},{"id":"0cd99961304448a0888bd20809df9294","isNewRecord":false,"createDate":"2017-04-18 14:17:08","updateDate":"2017-04-18 14:17:08","userId":"d77043a8d5fa4b8bbdfb4f54996f9610","invoicingDate":"2017-04","invoicingWeeks":"4","invoicingType":"1","surplusBox":"23","totalBox":"4"}]
     * success : true
     * size : 16
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
         * id : 234f6111bbe042afb56e88451ca06ec4
         * isNewRecord : false
         * createDate : 2017-04-21 10:35:20
         * updateDate : 2017-04-21 10:35:20
         * userId : d77043a8d5fa4b8bbdfb4f54996f9610
         * invoicingDate : 2017-04
         * invoicingWeeks : 4
         * invoicingType : 1
         * surplusBox : 58
         * totalBox : 2
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String userId;
        private String invoicingDate;
        private String invoicingWeeks;
        private String invoicingType;
        private String surplusBox;
        private String totalBox;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getInvoicingDate() {
            return invoicingDate;
        }

        public void setInvoicingDate(String invoicingDate) {
            this.invoicingDate = invoicingDate;
        }

        public String getInvoicingWeeks() {
            return invoicingWeeks;
        }

        public void setInvoicingWeeks(String invoicingWeeks) {
            this.invoicingWeeks = invoicingWeeks;
        }

        public String getInvoicingType() {
            return invoicingType;
        }

        public void setInvoicingType(String invoicingType) {
            this.invoicingType = invoicingType;
        }

        public String getSurplusBox() {
            return surplusBox;
        }

        public void setSurplusBox(String surplusBox) {
            this.surplusBox = surplusBox;
        }

        public String getTotalBox() {
            return totalBox;
        }

        public void setTotalBox(String totalBox) {
            this.totalBox = totalBox;
        }
    }
}
