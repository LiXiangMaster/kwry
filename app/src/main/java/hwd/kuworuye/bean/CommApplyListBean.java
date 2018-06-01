package hwd.kuworuye.bean;

/**
 * Created by Administrator on 2017/4/23.
 */

public class CommApplyListBean {
    private String createDate;
    private String approvalStatus;
    private String name;
    private String ratio;
    private String salesVolume;
    private String id;
    private int joinType;

    public CommApplyListBean(String createDate, String approvalStatus, String name, String ratio, String salesVolume, String id, int joinType) {
        this.createDate = createDate;
        this.approvalStatus = approvalStatus;
        this.name = name;
        this.ratio = ratio;
        this.salesVolume = salesVolume;
        this.id = id;
        this.joinType = joinType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    @Override
    public String toString() {
        return "CommApplyListBean{" +
                "createDate='" + createDate + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", name='" + name + '\'' +
                ", ratio='" + ratio + '\'' +
                ", salesVolume='" + salesVolume + '\'' +
                ", id='" + id + '\'' +
                ", joinType=" + joinType +
                '}';
    }
}
