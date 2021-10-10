package app.web;

import app.service.AuthService;
import eco.m1.annotate.Http;
import eco.m1.annotate.Inject;
import eco.m1.annotate.verbs.Get;
import eco.m1.data.RequestData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Http
public class BasicHandler {

    @Inject
    AuthService authService;

    @Get("/")
    public String index(HttpServletRequest req,
                        HttpServletResponse resp,
                        RequestData data){
        if(authService.isAuthenticated()){
            return "[redirect]/hi";
        }
        return "/pages/index.jsp";
    }

    @Get("/hi")
    public String welcome(HttpServletRequest req,
                        HttpServletResponse resp,
                        RequestData data){
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        return "/pages/index.jsp";
    }
}
