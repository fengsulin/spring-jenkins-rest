package com.example.runner.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author: FSL
 * @date: 2023/3/20
 * @description: Jenkins认证类
 */
public class JenkinsAuthentication extends Credentials{
    private final AuthenticationType authType;

    private JenkinsAuthentication(final String identity,final String credential,final AuthenticationType authType){
        super(identity,credential.contains(":") ? Base64.getEncoder().encodeToString(credential.getBytes()) : credential);
        this.authType = authType;
    }

    public String authValue(){
        return this.credential;
    }

    public AuthenticationType authType(){
        return authType;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String identity = "anonymous";
        private String credential = identity + ":";
        private AuthenticationType authType = AuthenticationType.Anonymous;

        public Builder credentials(final String usernamePassword){
            this.identity = Objects.requireNonNull(extractIdentity(usernamePassword));
            this.credential = Objects.requireNonNull(usernamePassword);
            this.authType = AuthenticationType.UsernamePassword;
            return this;
        }

        public Builder apiToken(final String apiTokenCredentials){
            this.identity = Objects.requireNonNull(extractIdentity(apiTokenCredentials));
            this.credential = Objects.requireNonNull(apiTokenCredentials);
            this.authType = AuthenticationType.UsernameApiToken;
            return this;
        }
        private String extractIdentity(final String credentialString) {
            String decoded;
            if (!credentialString.contains(":")) {
                decoded = new String(Base64.getDecoder().decode(credentialString), StandardCharsets.UTF_8);
            } else {
                decoded = credentialString;
            }
            if (!decoded.contains(":")) {
                throw new RuntimeException("Unable to detect the identity being used in '" + credentialString + "'. Supported types are a user:password, or a user:apiToken, or their base64 encoded value.");
            }
            if (decoded.equals(":")) {
                return "";
            }
            return decoded.split(":")[0];
        }

        public JenkinsAuthentication build(){
            return new JenkinsAuthentication(identity,credential,authType);
        }
    }
}
