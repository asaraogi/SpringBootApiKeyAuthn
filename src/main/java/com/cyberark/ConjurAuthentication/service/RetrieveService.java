package com.cyberark.ConjurAuthentication.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cyberark.ConjurAuthentication.Util.AuthenticationUtil;

@Service
public class RetrieveService {

	@Value("${application.logon.url}")
	private String access_token_url;
	@Value("${account.name}")
	private String account_name;;
	@Value("${auth.username}")
	String username;
	@Value("${auth.pass}")
	String password;

	public ResponseEntity<String> retrieveSecret()
			throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		System.out.println("Inside : RetrieveSecret() ");

		// -------------------Authenticating using Access Token-------------------
		String authorizationToken = AuthenticationUtil.authnlogin();
		System.out.println("RetrieveSecretService Token : " + authorizationToken);
		RestTemplate restTemplate = new RestTemplate();
		String authToken = "Token token=" + "\"" + authorizationToken + "\"";
		System.out.println(" authToken== " + authToken);

		// ------------Authenticating using apiKey------------------------------------
		// String originalInput = username+":"+password;
		// String encodedString = Base64.encodeBase64String(originalInput.getBytes());
		// System.out.println("encoded string---"+ encodedString);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authToken);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url2 = access_token_url + "/" + "secrets" + "/" + account_name + "/" + "variable" + "/"
				+ "jenkinsapp1/dbuserName";
		System.out.println("Inside  REST call" + access_token_url2);

		ResponseEntity<String> response = restTemplate.exchange(access_token_url2, HttpMethod.GET, request,
				String.class);

		System.out.println("Retrieve Secret Response  : " + response.getStatusCodeValue());
		return response;
	}

}
