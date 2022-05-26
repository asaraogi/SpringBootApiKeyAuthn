package com.cyberark.ConjurAuthentication.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

	@Value("${application.logon.url}")
	private String access_token_url;
	@Value("${account.name}")
	private String account_name;;
	@Value("${auth.username}")
	String username;
	@Value("${auth.pass}")
	String password;

	public ResponseEntity<String> getAccessToken()
			throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		System.out.println("Inside : getAccessToken() : Service ");

		String originalInput = username + ":" + password;
		String encodedString = Base64.encodeBase64String(originalInput.getBytes());
		System.out.println("encoded string---" + encodedString);
		RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept-Encoding", "base64");
		headers.set("Authorization", "Basic " + encodedString);

		HttpEntity<String> request = new HttpEntity<String>(headers);
		String api_key_url = access_token_url + "/" + "authn" + "/" + account_name + "/" + "login";

		ResponseEntity<String> response = restTemplate.exchange(api_key_url, HttpMethod.GET, request, String.class);
		System.out.println(" respponse--- " + response);

		// Connect to Conjur REST API to get Temporary Access Token

		// -----------hardcoded values for testing--------------------

		RestTemplate restTemplate1 = new RestTemplate();

		String body = "18xtnc4kqs2ay2j0tkhx2vq338343e4xe2rns7fd1zja7pb3hgmntq";
		HttpHeaders headers1 = new HttpHeaders();
		headers1.set("Accept-Encoding", "base64");
		HttpEntity<String> entity = new HttpEntity<String>("18xtnc4kqs2ay2j0tkhx2vq338343e4xe2rns7fd1zja7pb3hgmntq",
				headers1);

		access_token_url = access_token_url + "/" + "authn" + "/" + account_name + "/" + username + "/"
				+ "authenticate";
		ResponseEntity<String> res = null;
		try {
			res = restTemplate1.exchange(
					"https://localhost:8443/authn/myConjurAccount/host%2Fjenkins-frontend%2FNG-ADITI-O/authenticate",
					HttpMethod.POST, entity, String.class);
		} catch (HttpClientErrorException ex) {
			ex.printStackTrace();
		}

		// --------------Actual Codee =---------------

//		HttpHeaders headers2 = new HttpHeaders();	 
//	    headers2.setContentType(MediaType.APPLICATION_JSON);		
//	    headers2.set("Accept-Encoding","base64");	
//	    // build the request
//	    
//	   
//	    HttpEntity<String> request2 = new HttpEntity<String>(response.getBody().toString(),headers2);
//	    System.out.println(" request 2 \n "+request2);
//	    access_token_url=access_token_url+"/"+"authn"+"/"+account_name+"/"+username+"/"+"authenticate";  
//	    System.out.println(" acces token url ---   \n"+access_token_url);
//
//	    ResponseEntity<String> response2= null;
//	    try {
//	    	response2= restTemplate.exchange(access_token_url,HttpMethod.POST,request2,String.class);
//
//		//response2= restTemplate2.postForEntity(access_token_url, request2,String.class);
//	//	ResponseEntity<String> response2= restTemplate2.postForEntity(access_token_url, request2,String.class);
//	     } catch (HttpClientErrorException ex) {
////            if (ex.getStatusCode().value() == 404) {
////                JsonNode node = new ObjectMapper().readTree("{\"error\":{\"code\":\"NOT_FOUND\", \"message\":\"No matches found!\"}}");
////                System.out.println(node);
////            }
//	    	 ex.printStackTrace();
//            }

		System.out.println("Access  Token is : \n " + res);
		return res;

	}
}
