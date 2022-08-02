package auth;


import design.auth.ApiAuthencator;
import design.auth.AuthToken;
import design.auth.DefaultApiAuthenticatorImpl;
import org.junit.jupiter.api.Test;

/**
 * @program: AuthTest
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 16:57
 **/

public class AuthTest {

    @Test
    public void testAuth() {
        String originalUrl = "http://www.geek.design";
        String appId = "1001";
        String password = "PASS-1001";
        final long timeStamp = System.currentTimeMillis();
        final AuthToken authToken = AuthToken.generate(originalUrl, appId, password, timeStamp);

        ApiAuthencator apiAuthencator = new DefaultApiAuthenticatorImpl();
        apiAuthencator.auth(String.format("%s&appId=%s&token=%s&timestamp=%s", originalUrl, appId, authToken.getToken(), timeStamp));
    }


}
