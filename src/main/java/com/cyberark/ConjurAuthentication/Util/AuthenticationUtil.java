package com.cyberark.ConjurAuthentication.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthenticationUtil {

	public static String authnlogin() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8095/getAccessToken", null,
				String.class);
		System.out.println("AuthUtil class response --- > " + response.getBody().toString());
		return response.getBody().toString();

	}

}
