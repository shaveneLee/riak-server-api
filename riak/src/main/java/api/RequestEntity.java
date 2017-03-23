package api;

import java.util.HashMap;

/** define the request data struct
 * Created by shavene on 3/21/2017.
 */
public class RequestEntity {
    private String type;
    private String buckets;
    private String keys;
    private String string;
    private HashMap obj;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuckets() {
        return buckets;
    }

    public void setBuckets(String buckets) {
        this.buckets = buckets;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public HashMap getObj() {
        return obj;
    }

    public void setObj(HashMap obj) {
        this.obj = obj;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
