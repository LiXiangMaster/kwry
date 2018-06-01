package lix.lib.arl;


public class RollItem implements IRollItem {
    private String rollItemContent;
    String rollItemPicUrl;
    String rollItemTitle;


    public RollItem(String rollItemPicUrl, String rollItemTitle, String rollItemContent) {
        this.rollItemPicUrl = rollItemPicUrl;

        this.rollItemTitle = rollItemTitle;

        this.rollItemContent = rollItemContent;
    }


    @Override
    public String getRollItemPicUrl() {
        return rollItemPicUrl;
    }

    @Override
    public String getRollItemTitle() {
        return rollItemTitle;
    }

    @Override
    public String getRollItemContent() {

        return rollItemContent;
    }
}
