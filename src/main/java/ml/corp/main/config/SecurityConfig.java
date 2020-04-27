package ml.corp.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ml.corp.main.security.TokenFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.addFilterAfter(new TokenFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()			
			.antMatchers(HttpMethod.POST, "/token").permitAll()
			.anyRequest().authenticated()
			.and()
			.cors();
		
	}
	
}
