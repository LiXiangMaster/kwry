package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AskGoodsDetailBean {

    /**
     * total : 4
     * data : [{"id":"435556fba45445b280aa0968b6ffb0d2","isNewRecord":false,"createDate":"2017-03-23 17:05:48","updateDate":"2017-03-23 17:05:48","cargoPlanId":"80fc63fbf9ad47ea9bbb0457df0cf819","productAttributeId":"6646ac9b8f9045b3a0052b2fba208d29","quantity":"1","productName":"营养快线","tasteName":"原味","specificationsName":"100ml*50盒","price":"10.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/05590b35cdee4778a4584638607e4e81.png"},{"id":"db81b0cd71264468b65406bf833b04f7","isNewRecord":false,"createDate":"2017-03-23 17:05:48","updateDate":"2017-03-23 17:05:48","cargoPlanId":"80fc63fbf9ad47ea9bbb0457df0cf819","productAttributeId":"01cafe4f84aa4bea9472b2757d7f2a9d","quantity":"1","productName":"乳酸菌","tasteName":"原味","specificationsName":"100ml*50盒","price":"120.00","thumbnail":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/product/3f3bd5e0c96a4e45a2fa884fe023a10d.png"},{"id":"f2c1f92535e74667b287e9131e4ace01","isNewRecord":false,"createDate":"2017-03-23 17:05:48","updateDate":"2017-03-23 17:05:48","cargoPlanId":"80fc63fbf9ad47ea9bbb0457df0cf819","productAttributeId":"846d22d8306e429c9570b3eac45e4c32","quantity":"2","productName":"营养快线","tasteName":"原味","specificationsName":"100ml*50盒","price":"50.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png"}]
     * success : true
     * size : 3
     */

    private int total;
    private boolean success;
    private String size;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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
         * id : 435556fba45445b280aa0968b6ffb0d2
         * isNewRecord : false
         * createDate : 2017-03-23 17:05:48
         * updateDate : 2017-03-23 17:05:48
         * cargoPlanId : 80fc63fbf9ad47ea9bbb0457df0cf819
         * productAttributeId : 6646ac9b8f9045b3a0052b2fba208d29
         * quantity : 1
         * productName : 营养快线
         * tasteName : 原味
         * specificationsName : 100ml*50盒
         * price : 10.00
         * thumbnail : http://img.sx985.com/persistence/app_avatar/05590b35cdee4778a4584638607e4e81.png
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String cargoPlanId;
        private String productAttributeId;
        private String quantity;
        private String productName;
        private String tasteName;
        private String specificationsName;
        private String price;
        private String thumbnail;

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

        public String getCargoPlanId() {
            return cargoPlanId;
        }

        public void setCargoPlanId(String cargoPlanId) {
            this.cargoPlanId = cargoPlanId;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getTasteName() {
            return tasteName;
        }

        public void setTasteName(String tasteName) {
            this.tasteName = tasteName;
        }

        public String getSpecificationsName() {
            return specificationsName;
        }

        public void setSpecificationsName(String specificationsName) {
            this.specificationsName = specificationsName;
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
    }
}
