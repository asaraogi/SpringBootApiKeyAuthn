package com.cyberark.ConjurAuthentication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyberark.ConjurAuthentication.service.AuthenticationService;
import com.cyberark.ConjurAuthentication.service.RetrieveService;

@Controller
public class ConjurController {

	private final AuthenticationService authService;
	private final RetrieveService retrieveService;

	@Autowired
	ConjurController(final AuthenticationService authService, final RetrieveService retrieveSecretService) {
		this.authService = authService;
		this.retrieveService = retrieveSecretService;
	}

	@RequestMapping("/getAccessToken")
	public ResponseEntity<String> getAccessToken() throws Exception {
		System.out.println("Inside : Controller : getAccessToken() ");
		return authService.getAccessToken();
	}

	@RequestMapping("/retrieveSecret")
	public ResponseEntity<String> retrieve() throws Exception {
		System.out.println("Inside : Controller : retrieveSecret()");
		return retrieveService.retrieveSecret();
	}

}
