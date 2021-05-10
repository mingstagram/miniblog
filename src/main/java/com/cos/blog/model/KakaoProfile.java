package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

	public Integer id;
	public String connected_at;
	public Properties properties;
	public Kakao_account kakao_account;

	public class Properties {
		public String nickname;
		public String profile_image;
		public String thumbnail_image;
	}

	public class Kakao_account {
		public Boolean profile_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;
		
		public class Profile {
			public String nickname;
			public String thumbnail_image_url;
			public String profile_image_url;
		}

	}
	

	
}





