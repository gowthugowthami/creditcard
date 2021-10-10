package app.web;

import app.service.UserService;
import eco.m1.annotate.Http;
import eco.m1.annotate.Inject;
import eco.m1.annotate.Variable;
import eco.m1.annotate.verbs.Get;
import eco.m1.annotate.verbs.Post;
import eco.m1.data.RequestData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Http
public class UserHandler {

	@Inject
	UserService userService;

	@Get("/users")
	public String getUsers(HttpServletRequest req,
						   HttpServletResponse resp,
						   RequestData data){
		return userService.getUsers(data);
	}

	@Get("/users/edit/{{id}}")
	public String getEditUser(HttpServletRequest req,
							  HttpServletResponse resp,
							  RequestData data,
							  @Variable Long id){
		return userService.getEditUser(id, data);
	}

	@Post("/users/delete/{{id}}")
	public String deleteUser(HttpServletRequest req,
							 HttpServletResponse resp,
							 RequestData data,
							 @Variable Long id) {
		return userService.deleteUser(id, data);
	}

	@Post("/register")
	public String register(HttpServletRequest request,
							  HttpServletResponse resp,
							  RequestData data){
    	return userService.register(request, data);
	}

	@Get("/users/reset")
	public String reset(HttpServletRequest req,
						HttpServletResponse resp,
						RequestData data){
		return "/pages/user/reset.jsp";
	}


	@Post("/users/send")
	public String sendReset(HttpServletRequest request,
							HttpServletResponse resp,
				    		RequestData data){
		return userService.sendReset(data, request);
	}

	@Get("/users/confirm")
	public String confirm(HttpServletRequest req,
						  HttpServletResponse resp,
						  RequestData data){
		return userService.confirm(data, req);
	}

	@Post("/users/reset/{{id}}")
	public String resetPassword(HttpServletRequest req,
								HttpServletResponse resp,
								RequestData data,
								@Variable Long id){
    	return userService.resetPassword(id, data, req);
	}

}