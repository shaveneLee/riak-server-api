package api;


/** 定义api返回格式
 * Created by shavene on 3/21/2017.
 */
public class ResponseEntity {

    /**
     * errorCode description
     * 0: 成功
     * 40001: 未找到数据
     * 40002: 请求参数为空
     * 40003: 查询异常中断
     * 40004: 保存异常中断
     */
    private int errorCode;
    private String accessToken;
    private String message;
    private Object result;
    private RequestEntity query;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public RequestEntity getQuery() {
        return query;
    }

    public void setQuery(RequestEntity query) {
        this.query = query;
    }
}
