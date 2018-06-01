package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class QueryPicBean {

    /**
     * pageList : {"pageNo":1,"pageSize":3,"count":1,"list":[{"id":"afa2fa5d33304f18b16dbf06dd5cf60e","isNewRecord":false,"createDate":"2017-03-27 16:12:31","updateDate":"2017-03-27 16:12:31","processId":"8fe0503734484b788ce661f90f891672","type":"7","title":"2","imgs":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png"}]}
     */

    private PageListBean pageList;

    public PageListBean getPageList() {
        return pageList;
    }

    public void setPageList(PageListBean pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean {
        /**
         * pageNo : 1
         * pageSize : 3
         * count : 1
         * list : [{"id":"afa2fa5d33304f18b16dbf06dd5cf60e","isNewRecord":false,"createDate":"2017-03-27 16:12:31","updateDate":"2017-03-27 16:12:31","processId":"8fe0503734484b788ce661f90f891672","type":"7","title":"2","imgs":"http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png"}]
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : afa2fa5d33304f18b16dbf06dd5cf60e
             * isNewRecord : false
             * createDate : 2017-03-27 16:12:31
             * updateDate : 2017-03-27 16:12:31
             * processId : 8fe0503734484b788ce661f90f891672
             * type : 7
             * title : 2
             * imgs : http://kuwo.oss-cn-shanghai.aliyuncs.com/persistence/allImg/83190897e4fe4934854775a57722d81c.png
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String processId;
            private String type;
            private String title;
            private String imgs;

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
        }
    }
}
