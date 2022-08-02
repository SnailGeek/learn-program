package design.auth;

import com.geek.design.utils.MD5Utils;

import java.util.Map;

/**
 * @program: AuthToken
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 14:49
 **/
public class AuthToken {
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 1 * 60 * 1000;
    private String token;
    private long createTime;
    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    private static AuthToken create(String baseUrl, long createTime, Map<String, String> params) {
        return null;
    }

    public static AuthToken generate(String originalUrl, String appId, String password, long timestamp) {
        String separator = "@";
        StringBuilder builder = new StringBuilder(originalUrl);
        builder.append(separator).append(appId);
        builder.append(separator).append(password);
        builder.append(separator).append(timestamp);
        String token = MD5Utils.md5(builder.toString());
        return new AuthToken(token, System.currentTimeMillis());
    }

    public String getToken() {
        return this.token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.createTime + expiredTimeInterval;
    }

    public boolean match(AuthToken authToken) {
        return this.token.equals(authToken.getToken());
    }


}
