package com.cos.blog.controller;

import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.GoogleOauthToken;
import com.cos.blog.model.GoogleProfile;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserService userService;
	 
	

	@GetMapping("/auth/loginForm")
	public String login() {
		// /WEB-INF/views/index.jsp
		return "user/loginForm";
	}
	
	@GetMapping("/auth/findId")
	public String findId() { 
		return "user/findId";
	}
	
	@PostMapping("/auth/findId")
	public String findIdDo(String email, Model model) {
		String findUsername = userService.이메일회원찾기(email);
		model.addAttribute("findUsername", findUsername);
		return "user/findId";
	}
	
	@GetMapping("/auth/findPw")
	public String findPw() {
		return "user/findPw";
	}
	
	@PostMapping("/auth/findPw")
	public String findPwDo(String username, String email, Model model) {
		
		User findUser = userService.비밀번호찾기(username, email); 
		String rawPw = findUser.getPassword();
		userService.회원수정(findUser);
		userService.이메일보내기(findUser, "findpw", rawPw);
		model.addAttribute("findPw", rawPw);
		
		return "user/findPw";
	} 
	

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		// /WEB-INF/views/index.jsp
		return "user/joinForm";
	}

	@GetMapping("/auth/logout")
	public String logout() {
		// /WEB-INF/views/index.jsp
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code, Model model) { // @ResponseBody : Data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate


		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "38a832f0d3de7faf7f5ea88ec14da079");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers); // kakaoTokenRequest <- 헤더값과 바디값을 가지고있는 엔티티가 된다.

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				); 

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		OAuthToken oauthToken = null;

		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());


		RestTemplate rt2 = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		System.out.println("11111111111111111111111111111111111111111111111111111111111111111");
		// HttpHeader 를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
				); 
		System.out.println("2222222222222222222222222222222222222222222222222");
		System.out.println(response2.getBody());

		ObjectMapper objectMapper2 = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().email);

		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().email + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().email);
		//		UUID garbagePassword = UUID.randomUUID(); 
		System.out.println("블로그서버 패스워드 : " + cosKey);
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘

		User kakaoUser = new User();
		kakaoUser.setUsername(kakaoProfile.getKakao_account().email + "_" + kakaoProfile.getId());
		kakaoUser.setPassword(cosKey); 
		kakaoUser.setEmail(kakaoProfile.getKakao_account().email);

		// 가입자, 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
			originUser.setUsername(kakaoProfile.getKakao_account().email + "_" + kakaoProfile.getId());
			originUser.setPassword(cosKey);
			originUser.setOauth("kakao");
			originUser.setEmail(kakaoProfile.getKakao_account().email);
			userService.회원가입(originUser);
		}
		System.out.println("자동 로그인을 진행합니다.");
		// 로그인 처리
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return  "redirect:/";
	}

	@GetMapping("/auth/google/callback")
	public String googleCallback(String code, Model model) { // @ResponseBody : Data를 리턴해주는 컨트롤러 함수

		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate

		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", "753006082056-vrrgdf43s1ve6mmmmc9i6hphfd06itl6.apps.googleusercontent.com");
		params.add("client_secret", "VYrdMjSS_sqWLKXslyotXkym");
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", "http://localhost:8000/auth/google/callback");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> googleTokenRequest = 
				new HttpEntity<>(params, headers); // kakaoTokenRequest <- 헤더값과 바디값을 가지고있는 엔티티가 된다.

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://oauth2.googleapis.com/token",
				HttpMethod.POST,
				googleTokenRequest,
				String.class
				); 

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		GoogleOauthToken googleOauthToken = null;

		try {
			googleOauthToken = objectMapper.readValue(response.getBody(), GoogleOauthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		
		// 2단계 : 토큰을 통한 사용자 정보 조회 요청
		/*
		    8. 토큰을 통한 사용자 정보 조회 (GET)
			요청 주소 : https://www.googleapis.com/oauth2/v1/userinfo
			- header 값 - 
			Authorization: Bearer {access_token}
		 */
		// 
		RestTemplate rt2 = new RestTemplate();
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + googleOauthToken.getAccess_token());
		
		// HttpHeader 오브젝트로 담기
		HttpEntity<MultiValueMap<String, String>> googleProfileRequest2 = 
				new HttpEntity<>(headers2);
		// Http 요청하기 - Get 방식, response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://www.googleapis.com/oauth2/v1/userinfo",
				HttpMethod.GET,
				googleProfileRequest2,
				String.class
				);
		
		// Gson, Json Simple, ObjectMapper
		// Json을 Java 오브젝트로 변경하기
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		GoogleProfile googleProfile2 = null;

		try {
			googleProfile2 = objectMapper2.readValue(response2.getBody(), GoogleProfile.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User googleUser = new User();
		googleUser.setUsername(googleProfile2.getEmail() + "_" + googleProfile2.getId());
		googleUser.setEmail(googleProfile2.getEmail());
		googleUser.setRole(RoleType.USER);
		googleUser.setOauth("google");
		googleUser.setPassword("alsrnr12");
		
		User originUser = userService.회원찾기(googleUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다."); 
			userService.회원가입(googleUser);
		}
		
		System.out.println("자동 로그인을 진행합니다.");
		// 로그인 처리
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(googleUser.getUsername(), "alsrnr12"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return  "redirect:/";
	}



	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}



}
