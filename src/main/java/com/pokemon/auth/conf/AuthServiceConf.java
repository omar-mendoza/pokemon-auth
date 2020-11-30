package com.pokemon.auth.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServiceConf extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("poke_user")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit").authorities("USER")
				.scopes("read", "write").autoApprove(true).secret(passwordEncoder.encode("password_poke"));
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore);
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore(); // En memoria, se podría guardar el token en una BD
	}
	
	 @Override 
	   public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception { 
	       oauthServer.checkTokenAccess("isAuthenticated()"); 
	   }

}
