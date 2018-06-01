package hwd.kuworuye.bean;

import java.io.Serializable;

/**
 * Created by Lixiang on 2017/3/17 0017.
 */
public class ShowPersionInfo implements Serializable{

    /**
     * data : {"acreage":"100平","autograph":"123456","bankCard":"1234567890","bankName":"中国银行","brand":"哇哈哈","businessLicense":"http://www.baidu.com","car":"10辆","createDate":"","experience":"5年","foodCertificate":"http://www.baidu.com","id":"8a1fb39bed6f4d828bfd6f9c176f473a","isCertification":"1","isInit":"0","isNewRecord":false,"realName":"张三嗖嗖嗖","registeredCapital":"1000万","remarks":"","salesArea":"湖北省","salesChannel":"直销","teams":"100人","updateDate":""}
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

    public static class DataBean implements Serializable{
        /**
         * acreage : 100平
         * autograph : 123456
         * bankCard : 1234567890
         * bankName : 中国银行
         * brand : 哇哈哈
         * businessLicense : http://www.baidu.com
         * car : 10辆
         * createDate :
         * experience : 5年
         * foodCertificate : http://www.baidu.com
         * id : 8a1fb39bed6f4d828bfd6f9c176f473a
         * isCertification : 1
         * isInit : 0
         * isNewRecord : false
         * realName : 张三嗖嗖嗖
         * registeredCapital : 1000万
         * remarks :
         * salesArea : 湖北省
         * salesChannel : 直销
         * teams : 100人
         * updateDate :
         */

        private String acreage;
        private String autograph;
        private String bankCard;
        private String bankName;
        private String brand;
        private String businessLicense;
        private String car;
        private String createDate;
        private String experience;
        private String foodCertificate;
        private String id;
        private String isCertification;
        private String isInit;
        private boolean isNewRecord;
        private String realName;
        private String registeredCapital;
        private String remarks;
        private String salesArea;
        private String salesChannel;
        private String teams;
        private String updateDate;

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getFoodCertificate() {
            return foodCertificate;
        }

        public void setFoodCertificate(String foodCertificate) {
            this.foodCertificate = foodCertificate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsCertification() {
            return isCertification;
        }

        public void setIsCertification(String isCertification) {
            this.isCertification = isCertification;
        }

        public String getIsInit() {
            return isInit;
        }

        public void setIsInit(String isInit) {
            this.isInit = isInit;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegisteredCapital() {
            return registeredCapital;
        }

        public void setRegisteredCapital(String registeredCapital) {
            this.registeredCapital = registeredCapital;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSalesArea() {
            return salesArea;
        }

        public void setSalesArea(String salesArea) {
            this.salesArea = salesArea;
        }

        public String getSalesChannel() {
            return salesChannel;
        }

        public void setSalesChannel(String salesChannel) {
            this.salesChannel = salesChannel;
        }

        public String getTeams() {
            return teams;
        }

        public void setTeams(String teams) {
            this.teams = teams;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }
}
