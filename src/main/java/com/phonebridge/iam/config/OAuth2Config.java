package com.phonebridge.iam.config;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	   
	   private final DataSource dataSource;
	   private final PasswordEncoder passwordEncoder;
	   private final AuthenticationManager authenticationManager;
	   private final SecurityProperties securityProperties;
	   private final UserDetailsService userDetailsService;

	    private JwtAccessTokenConverter jwtAccessTokenConverter;
	    private TokenStore tokenStore;
	    
	    public OAuth2Config(final DataSource dataSource, final PasswordEncoder passwordEncoder,
                final AuthenticationManager authenticationManager, final SecurityProperties securityProperties,
                final UserDetailsService userDetailsService) {
			this.dataSource = dataSource;
			this.passwordEncoder = passwordEncoder;
			this.authenticationManager = authenticationManager;
			this.securityProperties = securityProperties;
			this.userDetailsService = userDetailsService;
    	}
	    

	    @Bean
	    public JwtAccessTokenConverter jwtAccessTokenConverter() {
	        if (jwtAccessTokenConverter != null) {
	            return jwtAccessTokenConverter;
	        }

	        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
	        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

	        jwtAccessTokenConverter = new JwtAccessTokenConverter();
	        jwtAccessTokenConverter.setKeyPair(keyPair);
	        return jwtAccessTokenConverter;
	    }
	    
	    @Bean
	    public TokenStore tokenStore() {
	        if (tokenStore == null) {
	            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
	        }
	        return tokenStore;
	    }
	    
	   @Override
	   public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		   oauthServer.passwordEncoder(this.passwordEncoder).tokenKeyAccess("permitAll()")
           .checkTokenAccess("isAuthenticated()");
	   }
	   
	   @Override
	   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		   clients.jdbc(this.dataSource);
	   }

	    @Override
	    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
	        endpoints.authenticationManager(this.authenticationManager)
	                .accessTokenConverter(jwtAccessTokenConverter())
	                .userDetailsService(this.userDetailsService)
	                .tokenStore(tokenStore());
	    }
	    
	    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
	        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
	    }
	    
	    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties) {
	        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
	    }
	    
}
