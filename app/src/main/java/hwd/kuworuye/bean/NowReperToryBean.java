package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class NowReperToryBean {

    /**
     * data : {"id":"1","isNewRecord":false,"createDate":"2017-03-13 14:52:53","updateDate":"2017-03-13 14:52:53","userId":"9","inventoryTotal":"10","inventoryProductList":[{"id":"1","isNewRecord":false,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","totalBox":"10","productName":"哇哈哈","tasteName":"原味","specificationsName":"100ml*50盒","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","price":"5.00"}]}
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
         * id : 1
         * isNewRecord : false
         * createDate : 2017-03-13 14:52:53
         * updateDate : 2017-03-13 14:52:53
         * userId : 9
         * inventoryTotal : 10
         * inventoryProductList : [{"id":"1","isNewRecord":false,"productAttributeId":"598ab0dd553d465da82960046c3dd4ff","totalBox":"10","productName":"哇哈哈","tasteName":"原味","specificationsName":"100ml*50盒","thumbnail":"http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png","price":"5.00"}]
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String userId;
        private String inventoryTotal;
        private List<InventoryProductListBean> inventoryProductList;

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

        public String getInventoryTotal() {
            return inventoryTotal;
        }

        public void setInventoryTotal(String inventoryTotal) {
            this.inventoryTotal = inventoryTotal;
        }

        public List<InventoryProductListBean> getInventoryProductList() {
            return inventoryProductList;
        }

        public void setInventoryProductList(List<InventoryProductListBean> inventoryProductList) {
            this.inventoryProductList = inventoryProductList;
        }

        public static class InventoryProductListBean {
            /**
             * id : 1
             * isNewRecord : false
             * productAttributeId : 598ab0dd553d465da82960046c3dd4ff
             * totalBox : 10
             * productName : 哇哈哈
             * tasteName : 原味
             * specificationsName : 100ml*50盒
             * thumbnail : http://img.sx985.com/persistence/app_avatar/d4521cb16df44f7ab1001c01e32e6ade.png
             * price : 5.00
             */

            private String id;
            private boolean isNewRecord;
            private String productAttributeId;
            private String totalBox;
            private String productName;
            private String tasteName;
            private String specificationsName;
            private String thumbnail;
            private String price;

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

            public String getProductAttributeId() {
                return productAttributeId;
            }

            public void setProductAttributeId(String productAttributeId) {
                this.productAttributeId = productAttributeId;
            }

            public String getTotalBox() {
                return totalBox;
            }

            public void setTotalBox(String totalBox) {
                this.totalBox = totalBox;
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

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
