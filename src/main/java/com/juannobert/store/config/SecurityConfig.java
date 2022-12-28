package com.juannobert.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.juannobert.store.models.enums.UserType;

@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final String[] ADMIN = {"/products/**","/auth/register/**"};
	
	private final String[] PUBLIC = {"auth/register"};
	
	private final String[] ADMIN_OR_CLIENT = {"/products","/products/details/**"};
	
	@Value("${br.com.juannobert.store.rememberMe.validitySeconds}")
	private Integer tokenValiditySeconds;
	
	@Value("${br.com.juannobert.store.rememberMe.rememberMeKey}")
	private String rememberMeKey;
	
	
	
	@Bean
	 AuthenticationManager authenticationManager(HttpSecurity http) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(passwordEncoder)
	      .and()
	      .build();
	}
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        	
        	.requestMatchers(PUBLIC).permitAll()
        	.requestMatchers(ADMIN_OR_CLIENT).authenticated()
        	.requestMatchers(ADMIN).hasAnyAuthority(UserType.ADMIN.toString())
        	
        	
        	.and()
        	.formLogin()
        	.loginPage("/auth/login")
        	.usernameParameter("email")
        	.passwordParameter("password")
        	.defaultSuccessUrl("/products",true)
        	.permitAll()
        	
        	.and()
        	.rememberMe()
        	.rememberMeParameter("remember-me")
        	.tokenValiditySeconds(tokenValiditySeconds)
        	.key(rememberMeKey)
        	
        	.and().csrf().disable();
            
        return http.build();
    }
	
	@Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**","/h2-console/**");
    }

	
	
	

}
