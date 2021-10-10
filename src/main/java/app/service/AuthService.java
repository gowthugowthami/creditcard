package app.service;

import app.common.Constants;
import app.model.User;
import app.repo.UserRepo;
import eco.m1.annotate.Inject;
import eco.m1.annotate.Service;
import eco.m1.data.RequestData;
import xyz.goioc.Parakeet;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Inject
    private UserRepo userRepo;

    public boolean signin(String username, String password){
        User user = userRepo.getByUsername(username);
        if(user == null) {
            return false;
        }
        return Parakeet.login(username, password);
    }

    public boolean signout(){
        return Parakeet.logout();
    }

    public boolean isAuthenticated(){
        return Parakeet.isAuthenticated();
    }

    public boolean isAdministrator(){
        return Parakeet.hasRole(Constants.SUPER_ROLE);
    }

    public boolean hasPermission(String permission){
        return Parakeet.hasPermission(permission);
    }

    public boolean hasRole(String role){
        return Parakeet.hasRole(role);
    }

    public User getUser(){
        String username = Parakeet.getUser();
        User user = userRepo.getByUsername(username);
        return user;
    }

    public String authenticate(RequestData data, HttpServletRequest req) {

        try{
            String username = req.getParameter("username");
            String passwordDirty = req.getParameter("password");
            if(!signin(username, passwordDirty)){
                data.put("message", "Wrong username and password");
                return "[redirect]/signin";
            }

            User authdUser = userRepo.getByUsername(username);

            req.getSession().setAttribute("username", authdUser.getUsername());
            req.getSession().setAttribute("userId", authdUser.getId());

        } catch ( Exception e ) {
            e.printStackTrace();
            data.put("message", "Please yell at one of us, something is a little off.");
            return "[redirect]/";
        }

        return "[redirect]/";
    }

    public String deAuthenticate(RequestData data, HttpServletRequest req) {
        signout();
        data.put("message", "Successfully signed out");
        req.getSession().setAttribute("username", "");
        req.getSession().setAttribute("userId", "");
        return "[redirect]/";
    }
}
