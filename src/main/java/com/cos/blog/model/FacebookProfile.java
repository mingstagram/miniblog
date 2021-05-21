package com.cos.blog.model;

import java.util.List;
import lombok.Data;

@Data
public class FacebookProfile {
	public Data data;
	public class Data {
		public String app_id;
		public String type;
		public String application;
		public Integer data_access_expires_at;
		public Integer expires_at;
		public Boolean is_valid;
		public Integer issued_at;
		public Metadata metadata;
		public List<String> scopes = null;
		public String user_id;
		public class Metadata {
			public String auth_type;
		}
	}
}



