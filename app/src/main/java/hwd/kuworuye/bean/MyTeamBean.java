package hwd.kuworuye.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyTeamBean {


    /**
     * data : [{"salesTotalVolume":0,"salesTotalBox":0,"userId":"6efb810f75bf4aefa58c125388d47135","userName":"小B","userArea":"湖北省","totalPeopleNum":2,"userType":"3","id":2,"parentId":1},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"64fc8e8b61704ed498d318b2a73c75a4","userName":"FF","userArea":"中国","totalPeopleNum":0,"userType":"1","id":3,"parentId":2},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"ed302b11da76463e897b480676df8ad4","userName":"SS","userArea":"中国","totalPeopleNum":0,"userType":"2","id":4,"parentId":2},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"15e342c8f3bf466fb5eedaca1a98441d","userName":"小G","userArea":"武汉市","totalPeopleNum":0,"userType":"1","id":5,"parentId":1},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"1e3c33d5d0cc4166b7ec1354cacd337d","userName":"小E","userArea":"武汉市","totalPeopleNum":4,"userType":"3","id":6,"parentId":1},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"7eb94394c5974d888ae653f5ae70440b","userName":"小F","userArea":"洪山区","totalPeopleNum":1,"userType":"2","id":7,"parentId":6},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"a1f4488373a94a968eaa794cd785cbdd","userName":"110","userArea":"洪山区","totalPeopleNum":0,"userType":"1","id":8,"parentId":7},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"cdbd51ec39f54a7aa8562c3b8126077e","userName":"小H","userArea":"洪山区","totalPeopleNum":0,"userType":"1","id":9,"parentId":6},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"8c68059f8f4c4fe6a2c53717e4febe29","userName":"小K","userArea":"武昌区","totalPeopleNum":0,"userType":"1","id":10,"parentId":6},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"cd1b1e6fc5424c7580b7197e456ab51b","userName":"小C","userArea":"武昌区","totalPeopleNum":1,"userType":"2","id":11,"parentId":6},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"93bf5bce05eb4d808e2e9aee087cf58f","userName":"111","userArea":"武昌区","totalPeopleNum":0,"userType":"1","id":12,"parentId":11},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"412d607a4675495d8bfd6faffec79c21","userName":"AAS","userArea":"湖北省","totalPeopleNum":0,"userType":"3","id":13,"parentId":1},{"salesTotalVolume":0,"salesTotalBox":0,"userId":"94d60fd7f60f49d6b7808eba8f82ab49","userName":"小D","userArea":"湖北省","totalPeopleNum":0,"userType":"1","id":14,"parentId":1}]
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

    public static class DataBean {
        /**
         * salesTotalVolume : 0
         * salesTotalBox : 0
         * userId : 6efb810f75bf4aefa58c125388d47135
         * userName : 小B
         * userArea : 湖北省
         * totalPeopleNum : 2
         * userType : 3
         * id : 2
         * parentId : 1
         */

        private int salesTotalVolume;
        private int salesTotalBox;
        private String userId;
        private String userName;
        private String userArea;
        private int totalPeopleNum;
        private String userType;
        private int id;
        private int parentId;

        public int getSalesTotalVolume() {
            return salesTotalVolume;
        }

        public void setSalesTotalVolume(int salesTotalVolume) {
            this.salesTotalVolume = salesTotalVolume;
        }

        public int getSalesTotalBox() {
            return salesTotalBox;
        }

        public void setSalesTotalBox(int salesTotalBox) {
            this.salesTotalBox = salesTotalBox;
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

        public String getUserArea() {
            return userArea;
        }

        public void setUserArea(String userArea) {
            this.userArea = userArea;
        }

        public int getTotalPeopleNum() {
            return totalPeopleNum;
        }

        public void setTotalPeopleNum(int totalPeopleNum) {
            this.totalPeopleNum = totalPeopleNum;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
