package com.cos.blog.model;

import lombok.Data;

@Data
public class GoogleOauthToken {
	private String access_token;
	private int expires_in;
	private String refresh_token;
	private String scope;
	private String token_type;
	private String id_token;
}
