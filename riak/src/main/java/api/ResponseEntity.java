package api;


/** define the return data struct of the api request
 * Created by shavene on 3/21/2017.
 */
public class ResponseEntity {

    /**
     * errorCode description
     * 0: success
     * 40001: Data Not Found
     * 40002: Request Parameter is null
     * 40003: Query Exception
     * 40004: Save Exception
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
