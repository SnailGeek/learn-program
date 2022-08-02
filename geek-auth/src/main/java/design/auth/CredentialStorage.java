package design.auth;

public interface CredentialStorage {
    String getPasswordByAppId(String appId);
}
