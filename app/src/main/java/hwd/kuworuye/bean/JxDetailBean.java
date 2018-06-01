package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class JxDetailBean {

    /**
     * total : 10
     * data : [{"id":"b07e1ed219cf48649a87fe2d2c8cefaf","isNewRecord":false,"remarks":"","createDate":"2017-03-14 15:37:30","updateDate":"2017-03-14 15:37:30","invoicingId":"1de4eedcf50b47b586c89464872a6a10","productAttributeId":"846d22d8306e429c9570b3eac45e4c32","quantity":"10","productAttribute":{"isNewRecord":true,"price":"50.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","tasteStr":"原味","specificationsStr":"100ml*50盒"},"product":{"isNewRecord":true,"productName":"营养快线"}}]
     * success : true
     * size : 1
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
         * id : b07e1ed219cf48649a87fe2d2c8cefaf
         * isNewRecord : false
         * remarks :
         * createDate : 2017-03-14 15:37:30
         * updateDate : 2017-03-14 15:37:30
         * invoicingId : 1de4eedcf50b47b586c89464872a6a10
         * productAttributeId : 846d22d8306e429c9570b3eac45e4c32
         * quantity : 10
         * productAttribute : {"isNewRecord":true,"price":"50.00","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","tasteStr":"原味","specificationsStr":"100ml*50盒"}
         * product : {"isNewRecord":true,"productName":"营养快线"}
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String invoicingId;
        private String productAttributeId;
        private String quantity;
        private ProductAttributeBean productAttribute;
        private ProductBean product;

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

        public String getInvoicingId() {
            return invoicingId;
        }

        public void setInvoicingId(String invoicingId) {
            this.invoicingId = invoicingId;
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

        public ProductAttributeBean getProductAttribute() {
            return productAttribute;
        }

        public void setProductAttribute(ProductAttributeBean productAttribute) {
            this.productAttribute = productAttribute;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductAttributeBean {
            /**
             * isNewRecord : true
             * price : 50.00
             * thumbnail : http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png
             * tasteStr : 原味
             * specificationsStr : 100ml*50盒
             */

            private boolean isNewRecord;
            private String price;
            private String thumbnail;
            private String tasteStr;
            private String specificationsStr;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
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
        }

        public static class ProductBean {
            /**
             * isNewRecord : true
             * productName : 营养快线
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
