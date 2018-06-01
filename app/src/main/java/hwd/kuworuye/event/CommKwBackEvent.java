package hwd.kuworuye.event;

/**
 * Created by Administrator on 2017/3/23.
 */

public class CommKwBackEvent {
    private String json;

    public CommKwBackEvent(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
