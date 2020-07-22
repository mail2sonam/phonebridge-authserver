package com.phonebridge.iam.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	  private PasswordEncoder passwordEncoder;
	  private final DataSource dataSource;
	  private UserDetailsService userDetailsService;

	
	  public SecurityConfiguration(final DataSource dataSource) 
	  { 
		  this.dataSource = dataSource; 
	  }
	 	   
	   @Override
	   public void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	        	.antMatchers("/oauth/token")
	        	.permitAll()
	        	.anyRequest().authenticated();
	   }
	    
	   @Bean
	   public PasswordEncoder encoder() {
		   if (passwordEncoder == null) {
	            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	        }
	        return passwordEncoder;
	   }
	   
	   @Override
	   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	   }
	   
	   @Override
	   @Bean
	   public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
	   }
	  
	   @Bean
	    public UserDetailsService userDetailsService() {
	        if (userDetailsService == null) {
	            userDetailsService = new JdbcDaoImpl();
	            ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
	        }
	        return userDetailsService;
	    }
}
