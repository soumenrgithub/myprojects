package org.myapp.controller;


import javax.servlet.http.HttpServletRequest;

import org.myapp.model.LoginRequest;
import org.myapp.model.LoginResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;

@Controller
@RequestMapping(value="/user")
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.PUT)
	public @ResponseBody WebAsyncTask<LoginResponse> userLogin(
			@RequestHeader(value="clientContextPath", required=false) String clientContextPath,
			@RequestBody LoginRequest loginRequest, 
			HttpServletRequest request) {

		WebAsyncTask<LoginResponse> w = new WebAsyncTask<>(
				5000L, 
				() -> {
					LoginResponse loginResponse = new LoginResponse();
					try{
						loginResponse.setResponsecode(200);
						loginResponse.setMessage("OK");

					}catch(Exception e){
						System.out.println(e);
					}
					return loginResponse;
				});
		w.onTimeout(() -> {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setResponsecode(400);
			loginResponse.setMessage("BAD REQUEST");
			return loginResponse;

		});
		return w;		
	}
}
