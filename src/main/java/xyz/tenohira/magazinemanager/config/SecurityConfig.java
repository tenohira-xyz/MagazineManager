package xyz.tenohira.magazinemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// ログイン不要ページの設定
		http
			.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/list").permitAll()
				.antMatchers("/detail").permitAll()
				.antMatchers("/contents").permitAll()
				.antMatchers("/index").permitAll()
				.antMatchers("/h2-console/**").permitAll()	// H2DBデバッグ用
				.anyRequest().authenticated();
		
		http.csrf().disable();	// H2DBデバッグ用
		http.headers().frameOptions().disable(); // H2DBデバッグ用
	}
}
