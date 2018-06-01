package hwd.kuworuye.bean.tree;

public class FileBean {

    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String areaName;
    @TreeNodePeopleName
    private String peopleName;
    @TreeNodeSaleNum
    private String saleNum;
    @TreeNodeCost
    private String saleCost;
    @TreeNodeItemType
    private String itemType;
    @TreeNodeUserId
    private String userId;

    private long length;
    private String desc;

    public FileBean(int _id, int parentId, String areaName, String peopleName, String saleNum, String saleCost, String itemType, String userId) {
        super();
        this._id = _id;
        this.parentId = parentId;
        this.areaName = areaName;
        this.peopleName = peopleName;
        this.saleNum = saleNum;
        this.saleCost = saleCost;
        this.itemType = itemType;
        this.userId = userId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(String saleNum) {
        this.saleNum = saleNum;
    }

    public String getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(String saleCost) {
        this.saleCost = saleCost;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
