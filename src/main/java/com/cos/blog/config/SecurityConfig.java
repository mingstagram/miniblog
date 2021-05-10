package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
// 3가지 세트 (@Configuration, @EnableWebSecurity, @EnableGlobalMethodSecurity(prePostEnabled = true))
@Configuration // 빈등록(IoC 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC가 되요! 필요할때마다 가져다 쓰면 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}	
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	/*
	 * XSS :  자바스크립트 공격
	 * CSRF : 인터넷 사용자(희생자)가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정,삭제,등록)등을 특정 웹사이트에 요청하게 만드는 공격
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화(테스트시 걸어두는 게 좋음)
			.authorizeRequests() // 요청이 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // /auth/** 로 들어오는건 
				.permitAll() // 누구나 들어올 수 있다.
				.anyRequest() // 이게 아닌 다른 요청들은
				.authenticated() // 인증이 필요하다.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
	}
}
