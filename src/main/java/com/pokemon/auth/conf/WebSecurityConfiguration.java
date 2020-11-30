package com.pokemon.auth.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${user1.usuario}")
	private String usuario1;
	@Value("${user1.password}")
	private String password1;
	
	@Value("${user2.usuario}")
	private String usuario2;
	@Value("${user2.password}")
	private String password2;
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {

		UserDetails user1 = User.builder().username(usuario1).password(passwordEncoder().encode(password1))
				.roles("USER").build();
		UserDetails user2 = User.builder().username(usuario2).password(passwordEncoder().encode(password2))
				.roles("USER").build();
		return new InMemoryUserDetailsManager(user1, user2);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
