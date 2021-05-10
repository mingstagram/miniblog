package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Data
public class PrincipalDetail implements UserDetails{

	private User user; // 컴포지션

	public PrincipalDetail(User user) {
		this.user = user;
	}
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 잠겨있는지 안 잠겨있는지 리턴한다. (true:잠겨있지않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. (true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 활성화(사용가능)인지 리턴한다. (true:활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 계정의 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
	
//		collectors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				return "ROLE_" + user.getRole(); // ROLE_USER, ROLE_ADMIN
//			}
//		});
		
		// 위의 방식의 람다식표현
		collectors.add(()->{
			return "ROLE_" + user.getRole();
		});
		
		return collectors;
	}
	
}
