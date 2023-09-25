package com.pixeltrice.springbootOTPenabledapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UsersService usersService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/","/aboutus").permitAll()  //dashboard , Aboutus page will be permit to all user 
			.antMatchers("/admin/**").hasAnyRole("ADMIN") //Only admin user can login 
			.antMatchers("/user/**").hasAnyRole("USER") //Only normal user can login 
			.anyRequest().authenticated() //Rest of all request need authentication 
			.and()
			.formLogin().loginPage("/login")  //Login form all can access .. 
			.defaultSuccessUrl("/dashboard")//Once the login is done, then by default dashboard page will come with the URL /dashboard.
			.failureUrl("/login?error")//If login is not success, then error URL get triggered, or called.
			.permitAll()//Once authentication and login success, then user can acess all the URL.
			.and()
			.logout()
			.permitAll()
			.and()
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
			//If logged in users try to access other URLs, 
			//for which he or she is not allowed then Access Denied will occur,
			//so to handle this scenario,
			//we already Autowired or injected the predefined class named AccessDeniedHandler.
	
		}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		auth.userDetailsService(usersService).passwordEncoder(passwordEncoder);;
		    }
	// we are passing a usersService object, still, we will attach all users’ details in that object, and from the above method, it will be validated.
	//We are also using the password encoder to store or convert the password in 
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();//we defined our own custom class to Handle the Access Denied scenario.
	}

}

