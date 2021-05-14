package com.cos.blog.service;


import java.util.UUID;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.  IoC를 해준다.
@Service
public class UserService {
	
	@Value("${cos.email}")
	String hostSMTPid;
	@Value("${cos.password}")
	String hostSMTPpwd;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;   
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	@Transactional(readOnly = true)
	public String 이메일회원찾기(String email) {
		String findUsername = userRepository.findByEmail(email);
		return findUsername;
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public void 이메일보내기(User user, String div, String rawPw) {
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com"; //구글 이용시 smtp.gmail.com  
		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "mandoo1021@naver.com";
		String fromName = "미니블로그";
		String subject = "";
		String msg = "";
		
		if(div.equals("findpw")) {
			subject = "미니블로그 임시 비밀번호 입니다.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += user.getUsername() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
			msg += "<p>임시 비밀번호 : ";
			msg += rawPw + "</p></div>";
		}
		
		// 받는 사람 E-Mail 주소
		String mail = user.getEmail();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		try {
			HtmlEmail email = new HtmlEmail(); 
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(465);  
			email.setAuthentication(hostSMTPid, hostSMTPpwd); 
			email.setTLS(true); 
			email.addTo(mail, charSet); 
			email.setFrom(fromEmail, fromName, charSet); 
			email.setSubject(subject); 
			email.setHtmlMsg(msg); 
			email.send(); 
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}
		
		
	}
	
	@Transactional(readOnly = true)
	public User  비밀번호찾기(String username, String email) {
		User findUser = userRepository.findByUsernameAndEmail(username, email); 
		System.out.println("111111111111111111111111111111111111111" + findUser.getUsername());
		String tempPw = null;
		if(findUser != null) { // 일치하는 유저가 있으면
			// 임시 비밀번호 생성(UUID)
			tempPw = UUID.randomUUID().toString().replace("-", "").substring(0,10); // -를 제거, 10자리로 맞춤
			findUser.setPassword(tempPw);
		}
		return findUser;
	} 
	
	@Transactional
	public void 회원가입(User user) {
		System.out.println("user.getPassword()" + user.getPassword());
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); 
		userRepository.save(user);
	} 
	
	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Validation 체크 - 카카오 로그인 사용자들은 패스워드 강제로라도 변경 금지
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword(); // 1234 원문
			String encPassword = encoder.encode(rawPassword); // 해쉬
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		// 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션이 종료 = 자동 commit
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
	}
	
}
