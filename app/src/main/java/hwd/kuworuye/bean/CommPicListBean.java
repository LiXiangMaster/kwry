package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class CommPicListBean {

    /**
     * pageList : {"pageNo":1,"pageSize":3,"count":2,"list":[{"id":"24052ee6dc0847cfa6e4df0ffd443ff2","isNewRecord":false,"createDate":"2017-03-30 20:04:14","updateDate":"2017-03-30 20:04:14","processId":"e55cec41eae147c9b3c80a18527de95d","type":"3","title":"a2","imgs":"200"},{"id":"d3f411cd9d0047d0814c58609d70b73c","isNewRecord":false,"createDate":"2017-03-30 20:04:14","updateDate":"2017-03-30 20:04:14","processId":"e55cec41eae147c9b3c80a18527de95d","type":"3","title":"a1","imgs":"100"}]}
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
         * count : 2
         * list : [{"id":"24052ee6dc0847cfa6e4df0ffd443ff2","isNewRecord":false,"createDate":"2017-03-30 20:04:14","updateDate":"2017-03-30 20:04:14","processId":"e55cec41eae147c9b3c80a18527de95d","type":"3","title":"a2","imgs":"200"},{"id":"d3f411cd9d0047d0814c58609d70b73c","isNewRecord":false,"createDate":"2017-03-30 20:04:14","updateDate":"2017-03-30 20:04:14","processId":"e55cec41eae147c9b3c80a18527de95d","type":"3","title":"a1","imgs":"100"}]
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
             * id : 24052ee6dc0847cfa6e4df0ffd443ff2
             * isNewRecord : false
             * createDate : 2017-03-30 20:04:14
             * updateDate : 2017-03-30 20:04:14
             * processId : e55cec41eae147c9b3c80a18527de95d
             * type : 3
             * title : a2
             * imgs : 200
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
