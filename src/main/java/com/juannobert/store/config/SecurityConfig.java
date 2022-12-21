package com.juannobert.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) 
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
        	.requestMatchers(HttpMethod.POST,"/products/insert" ).hasAnyRole(UserType.ADMIN.toString())
        	.requestMatchers("/auth/register").permitAll()
        	.anyRequest().authenticated()
        	
        	.and()
        	.formLogin()
        	.usernameParameter("email")
        	.passwordParameter("password")
        	.loginPage("/auth/login")
        	.defaultSuccessUrl("/products")
        	.permitAll();
            
        return http.build();
    }
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

	
	
	

}
