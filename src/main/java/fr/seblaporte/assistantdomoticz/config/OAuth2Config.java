package fr.seblaporte.assistantdomoticz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final AuthenticationManager authenticationManager;
    private final String clientId;
    private final String secret;

    @Autowired
    public OAuth2Config(PasswordEncoder passwordEncoder,
                        TokenStore tokenStore,
                        AuthenticationManager authenticationManager,
                        @Value("${oauth.client-id}") String clientId,
                        @Value("${oauth.secret}") String secret) {
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.authenticationManager = authenticationManager;
        this.clientId = clientId;
        this.secret = secret;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("user_info")
                .accessTokenValiditySeconds(1*60*60)
                .refreshTokenValiditySeconds(Integer.MAX_VALUE)
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
