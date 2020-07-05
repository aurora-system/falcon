package com.falcon.config.security.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC = new String[] 
	        {"/","/login","/logout","/error","/favicon.ico","/h2-console/**"};
	
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private UserDetailsService falconUserDetailsService;
    
	/*@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}*/

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(falconUserDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			//.addFilter(authenticationFilter())
			.authorizeRequests()
				.antMatchers(PUBLIC).permitAll()
				.anyRequest().authenticated()
				.and()
			//.exceptionHandling()
			//	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			//	.and()
			//.sessionManagement()
			//	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.formLogin()
				.loginPage("/")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard", true)
				//.successHandler(spmsSuccessHandler());
				//.successForwardUrl("/success");
				.failureUrl("/")
				.and()
			.logout()
				//.and()
			;
		// Add a filter to validate the tokens with every request
		// http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		// http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/js/**");
	}
}
