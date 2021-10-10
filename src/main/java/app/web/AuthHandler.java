package app.web;

import app.service.AuthService;
import eco.m1.annotate.Http;
import eco.m1.annotate.Inject;
import eco.m1.annotate.verbs.Get;
import eco.m1.annotate.verbs.Post;
import eco.m1.data.RequestData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Http
public class AuthHandler {

	@Inject
	AuthService authService;

	@Post("/authenticate")
	public String authenticate(HttpServletRequest req,
							   HttpServletResponse resp,
							   RequestData data){
		return authService.authenticate(data, req);
	}

	@Get("/signin")
	public String signin(HttpServletRequest req,
						 HttpServletResponse resp,
						 RequestData data){
		return "/pages/signin.jsp";
	}

	@Get("/signup")
	public String signup(HttpServletRequest req,
						 HttpServletResponse resp,
						 RequestData data){
		return "/pages/signup.jsp";
	}

	@Get("/signout")
	public String signout(HttpServletRequest request,
						  HttpServletResponse resp,
						  RequestData data){
		return authService.deAuthenticate(data, request);
	}

}