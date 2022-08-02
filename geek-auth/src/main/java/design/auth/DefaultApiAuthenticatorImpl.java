package design.auth;

/**
 * @program: DefaultApiAuthencatorImpl
 * @description:
 * @author: wangf-q
 * @date: 2022-08-02 15:09
 **/
public class DefaultApiAuthenticatorImpl implements ApiAuthencator {

    private CredentialStorage credentialStorage;

    public DefaultApiAuthenticatorImpl() {
        this.credentialStorage = new MysqlCredentialStorage();
    }

    public DefaultApiAuthenticatorImpl(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    @Override
    public void auth(String url) {
        final ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        final String appId = apiRequest.getAppId();
        final String token = apiRequest.getToken();
        final long timestamp = apiRequest.getTimestamp();
        final String baseUrl = apiRequest.getOriginalUrl();
        final AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired");
        }
        final String password = credentialStorage.getPasswordByAppId(appId);
        final AuthToken serverAuthToken = AuthToken.generate(baseUrl, appId, password, timestamp);
        if (!serverAuthToken.match(clientAuthToken)) {
            throw new RuntimeException("Token verification failed");
        }
        System.out.println("Token verification success!");
    }
}
