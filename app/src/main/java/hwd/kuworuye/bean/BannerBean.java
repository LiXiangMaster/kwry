package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Lixiang on 2017/3/16 0016.
 */
public class BannerBean {

    /**
     * data : [{"id":"585ae6bf75c9464cbc53ce511c1d71c2","isNewRecord":false,"remarks":"","createDate":"2017-03-10 16:47:07","updateDate":"2017-03-14 17:24:17","title":"123","describes":"123","content":"<p>\r\n\t的角度讲到交警队<\/p>\r\n<p>\r\n\t对对对<\/p>\r\n<p>\r\n\t <\/p>\r\n<p>\r\n\t <\/p>\r\n<p>\r\n\t的的的的的<\/p>","imgUrl":"http://img.sx985.com/persistence/app_avatar/da0670148fc14a9ea0932dd801d077a1.png","isHome":"1"}]
     * success : true
     * size : 1
     */

    private boolean success;
    private String size;
    private List<DataBean> data;

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
         * id : 585ae6bf75c9464cbc53ce511c1d71c2
         * isNewRecord : false
         * remarks :
         * createDate : 2017-03-10 16:47:07
         * updateDate : 2017-03-14 17:24:17
         * title : 123
         * describes : 123
         * content : <p>
         的角度讲到交警队</p>
         <p>
         对对对</p>
         <p>
         </p>
         <p>
         </p>
         <p>
         的的的的的</p>
         * imgUrl : http://img.sx985.com/persistence/app_avatar/da0670148fc14a9ea0932dd801d077a1.png
         * isHome : 1
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String title;
        private String describes;
        private String content;
        private String imgUrl;
        private String isHome;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getIsHome() {
            return isHome;
        }

        public void setIsHome(String isHome) {
            this.isHome = isHome;
        }
    }
}
