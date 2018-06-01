package hwd.kuworuye.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ProductListBean implements Serializable {


    /**
     * data : [{"productList":[{"createDate":"2017-03-14 12:44:02","isNewRecord":true,"price":"50.00","productAttributeId":"846d22d8306e429c9570b3eac45e4c32","productName":"营养快线","productNumber":"201703140933523368","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","weight":"0.500"},{"createDate":"2017-03-09 16:04:34","isNewRecord":true,"price":"10.00","productAttributeId":"6646ac9b8f9045b3a0052b2fba208d29","productName":"营养快线","productNumber":"201703091264117392","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/05590b35cdee4778a4584638607e4e81.png","weight":"10.000"}],"size":"0","typeId":"a8b18ef268674543bffd1660a67b7b47","typeName":"营养快线"},{"productList":[{"createDate":"2017-03-14 12:44:02","isNewRecord":true,"price":"50.00","productAttributeId":"846d22d8306e429c9570b3eac45e4c32","productName":"营养快线","productNumber":"201703140933523368","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","weight":"0.500"},{"createDate":"2017-03-09 16:04:34","isNewRecord":true,"price":"10.00","productAttributeId":"6646ac9b8f9045b3a0052b2fba208d29","productName":"营养快线","productNumber":"201703091264117392","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/05590b35cdee4778a4584638607e4e81.png","weight":"10.000"}],"size":"2","typeId":"51ccc2dce2584f3189918680537a1f1e","typeName":"哇哈哈"},{"productList":[],"size":"0","typeId":"40fa103d9b5b46109c30007b5c10266c","typeName":"旺仔"}]
     * success : true
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * productList : [{"createDate":"2017-03-14 12:44:02","isNewRecord":true,"price":"50.00","productAttributeId":"846d22d8306e429c9570b3eac45e4c32","productName":"营养快线","productNumber":"201703140933523368","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png","weight":"0.500"},{"createDate":"2017-03-09 16:04:34","isNewRecord":true,"price":"10.00","productAttributeId":"6646ac9b8f9045b3a0052b2fba208d29","productName":"营养快线","productNumber":"201703091264117392","specificationsName":"100ml*50盒","tasteName":"原味","thumbnail":"http://img.sx985.com/persistence/app_avatar/05590b35cdee4778a4584638607e4e81.png","weight":"10.000"}]
         * size : 0
         * typeId : a8b18ef268674543bffd1660a67b7b47
         * typeName : 营养快线
         */

        private String size;
        private String typeId;
        private String typeName;
        private List<ProductDetailListBean> productList;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<ProductDetailListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductDetailListBean> productList) {
            this.productList = productList;
        }

        public static class ProductDetailListBean implements Serializable{
            /**
             * createDate : 2017-03-14 12:44:02
             * isNewRecord : true
             * price : 50.00
             * productAttributeId : 846d22d8306e429c9570b3eac45e4c32
             * productName : 营养快线
             * productNumber : 201703140933523368
             * specificationsName : 100ml*50盒
             * tasteName : 原味
             * thumbnail : http://img.sx985.com/persistence/app_avatar/4e6f29e441f34eccb52d34ce8817ede5.png
             * weight : 0.500
             */

            private String createDate;
            private boolean isNewRecord;
            private String price;
            private String productAttributeId;
            private String productName;
            private String productNumber;
            private String specificationsName;
            private String tasteName;
            private String thumbnail;
            private String weight;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

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

            public String getProductAttributeId() {
                return productAttributeId;
            }

            public void setProductAttributeId(String productAttributeId) {
                this.productAttributeId = productAttributeId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductNumber() {
                return productNumber;
            }

            public void setProductNumber(String productNumber) {
                this.productNumber = productNumber;
            }

            public String getSpecificationsName() {
                return specificationsName;
            }

            public void setSpecificationsName(String specificationsName) {
                this.specificationsName = specificationsName;
            }

            public String getTasteName() {
                return tasteName;
            }

            public void setTasteName(String tasteName) {
                this.tasteName = tasteName;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }
        }
    }
}
