package design.auth;

/**
 * @program: ApiRequest
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 15:01
 **/
public class ApiRequest {
    private String originalUrl;
    private String token;
    private String appId;
    private long timestamp;

    public ApiRequest(String originalUrl, String token, String appId, long timestamp) {
        this.originalUrl = originalUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest buildFromUrl(String url) {
        final String[] reqArray = url.split("&");
        final String originalUrl = reqArray[0].split("=", 2)[1];
        String appId = reqArray[1].split("=", 2)[1];
        String token = reqArray[2].split("=", 2)[1];
        long timestamp = Long.parseLong(reqArray[3].split("=", 2)[1]);
        return new ApiRequest(originalUrl, token, appId, timestamp);
    }

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
