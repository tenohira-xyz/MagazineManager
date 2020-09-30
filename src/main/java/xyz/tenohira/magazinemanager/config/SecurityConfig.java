package xyz.tenohira.magazinemanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// データソース
	@Autowired
	private DataSource dataSource;
	
	// ユーザ名とパスワードを取得するSQL
	private static final String USER_SQL = "SELECT name, password, true FROM m_user WHERE name = ?";
	// ユーザ名と権限を取得するSQL
	private static final String ROLE_SQL = "SELECT name, authority FROM m_user WHERE name = ?";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// ログイン不要ページの設定
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").anonymous()
				.antMatchers("/list").permitAll()
				.antMatchers("/detail/*").permitAll()
				.antMatchers("/contents/*").permitAll()
				.antMatchers("/index/*").permitAll()
				.antMatchers("/h2-console/**").permitAll()	// H2DBデバッグ用
				.anyRequest().authenticated();
		
		// 未認証時のアクセス拒否の設定
		http
				.exceptionHandling()
					.authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		
		http.csrf().disable();	// H2DBデバッグ用
		http.headers().frameOptions().disable(); // H2DBデバッグ用
		
		// ログイン処理
		http
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/login")
				.usernameParameter("userName")
				.passwordParameter("password")
				.defaultSuccessUrl("/list", true);
		
		// ログアウト処理
		http
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/list");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// ログイン処理時のユーザー情報をDBから取得
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			.passwordEncoder(passwordEncoder());
	}
}
