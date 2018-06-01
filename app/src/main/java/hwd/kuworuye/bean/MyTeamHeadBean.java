package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MyTeamHeadBean {

    /**
     * data : {"salesTotalVolume":11,"salesTotalBox":11,"userId":"0d23a676d8494af99a5380e4eee94238","userName":"区域经理","userArea":"武汉市","totalPeopleNum":3,"userType":"3"}
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
         * salesTotalVolume : 11
         * salesTotalBox : 11
         * userId : 0d23a676d8494af99a5380e4eee94238
         * userName : 区域经理
         * userArea : 武汉市
         * totalPeopleNum : 3
         * userType : 3
         */

        private int salesTotalVolume;
        private int salesTotalBox;
        private String userId;
        private String userName;
        private String userArea;
        private int totalPeopleNum;
        private String userType;

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
    }
}
