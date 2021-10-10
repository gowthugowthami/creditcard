package app.service;

import app.common.App;
import app.common.Constants;
import app.model.Role;
import app.model.User;
import app.repo.RoleRepo;
import app.repo.UserRepo;
import eco.m1.annotate.Inject;
import eco.m1.annotate.Service;
import eco.m1.data.RequestData;
import xyz.goioc.Parakeet;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

@Service
public class UserService {

    @Inject
    UserRepo userRepo;

    @Inject
    RoleRepo roleRepo;

    @Inject
    AuthService authService;


    private String getPermission(String id){
        return Constants.USER_MAINTENANCE + id;
    }

    public String getUsers(RequestData data){
        if(!authService.isAuthenticated()){
            return "[redirect]/";
        }
        if(!authService.isAdministrator()){
            data.put("message", "You must be a super user in order to access accounts.");
            return "[redirect]/";
        }

        List<User> users = userRepo.findAll();
        data.put("users", users);

        return "/pages/user/index.jsp";
    }

    public String getEditUser(Long id, RequestData data){
        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        User user = userRepo.get(id);
        data.put("user", user);

        return "/pages/user/edit.jsp";
    }


    public String editPassword(Long id, RequestData data) {

        String permission = getPermission(Long.toString(id));
        if(!authService.isAdministrator() ||
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        User user = userRepo.get(id);
        data.put("user", user);
        return "/pages/user/password.jsp";
    }


    public String updatePassword(User user, RequestData data) {

        String permission = getPermission(Long.toString(user.getId()));
        if(!authService.isAdministrator() &&
                !authService.hasPermission(permission)){
            return "[redirect]/";
        }

        if(user.getPassword().length() < 7){
            data.put("message", "Passwords must be at least 7 characters long.");
            return "[redirect]/signup";
        }

        if(!user.getPassword().equals("")){
            user.setPassword(Parakeet.dirty(user.getPassword()));
            userRepo.updatePassword(user);
        }

        data.put("message", "password successfully updated");
        Long id = authService.getUser().getId();
        return "[redirect]/user/edit_password/" + id;

    }

    public String deleteUser(Long id, RequestData data) {
        if(!authService.isAdministrator()){
            data.put("message", "You don't have permission");
            return "[redirect]/";
        }

        data.put("message", "Successfully deleted user");
        return "[redirect]/admin/users";
    }

    public String register(HttpServletRequest req, RequestData data) {


        Enumeration<String> parameters = req.getParameterNames();
        while(parameters.hasMoreElements()){
            System.out.println(parameters.nextElement());
        }
        System.out.println("M1 Starter " + req);
        System.out.println("here..." + req.getParameterNames());
        String username = req.getParameter("username");
        String rawPassword = req.getParameter("password");

        if(username == null ||
                username.equals("")){
            data.put("message", "please enter a username.");
            return "[redirect]/signup";
        }

        User existingUser = userRepo.getByUsername(username);
        if(existingUser != null){
            data.put("message", "User exists with same username.");
            return "[redirect]/signup";
        }

        if(rawPassword == null ||
                rawPassword.equals("")) {
            data.put("message", "Password cannot be blank");
            return "[redirect]/signup";
        }

        if(rawPassword.length() < 7){
            data.put("message", "Password must be at least 7 characters long.");
            return "[redirect]/signup";
        }

        String passwordHashed = Parakeet.dirty(rawPassword);

        try{
            User user = new User();

            user.setUsername(username);
            user.setPassword(passwordHashed);
            user.setDateCreated(App.getDate());
            userRepo.save(user);

            User savedUser = userRepo.getByUsername(user.getUsername());
            Role defaultRole = roleRepo.find(Constants.USER_ROLE);

            userRepo.saveUserRole(savedUser.getId(), defaultRole.getId());
            String permission = getPermission(Long.toString(savedUser.getId()));
            userRepo.savePermission(savedUser.getId(), permission);


        }catch(Exception e){
            e.printStackTrace();
            data.put("message", "Will you contact us? Email us with the subject, support@amadeus.social. Our programmers missed something. Gracias!");
            return("[redirect]/signup");
        }

        if(!authService.signin(username, rawPassword)) {
            data.put("message", "Thank you for registering. We hope you enjoy!");
            return "[redirect]/";
        }

        req.getSession().setAttribute("username", username);
        req.getSession().setAttribute("userId", authService.getUser().getId());

        return "[redirect]/";
    }

    public String sendReset(RequestData data, HttpServletRequest req) {

        try {
            String username = req.getParameter("username");
            User user = userRepo.getByUsername(username);
            if (user == null) {
                data.put("message", "Unable to find user.");
                return ("[redirect]/user/reset");
            }

            String resetUuid = App.getString(13);
            user.setUuid(resetUuid);
            userRepo.updateUuid(user);

            StringBuffer url = req.getRequestURL();

            String[] split = url.toString().split(req.getContextPath());
            String httpSection = split[0];

            String resetUrl = httpSection + req.getContextPath() + "/user/confirm?";
            String params = "username=" + URLEncoder.encode(user.getUsername(), "utf-8") + "&uuid=" + resetUuid;
            resetUrl += params;

            String body = "<p>Reset password</p>" +
                    "<p><a href=\"" + resetUrl + "\">" + resetUrl + "</a></p>";


        }catch(Exception e){
            e.printStackTrace();
        }

        return "/pages/user/send.jsp";
    }

    public String confirm(RequestData data, HttpServletRequest req) {

        String uuid = req.getParameter("uuid");
        String username = req.getParameter("username");

        User user = userRepo.getByUsernameAndUuid(username, uuid);
        if (user == null) {
            data.put("error", "Unable to locate user.");
            return "[redirect]/user/reset";
        }

        data.put("user", user);
        return "/pages/user/confirm.jsp";
    }

    public String resetPassword(Long id, RequestData data, HttpServletRequest req) {

        User user = userRepo.get(id);
        String uuid = req.getParameter("uuid");
        String username = req.getParameter("username");
        String rawPassword = req.getParameter("password");

        if(rawPassword.length() < 7){
            data.put("message", "Passwords must be at least 7 characters long.");
            return "[redirect]/user/confirm?username=" + username + "&uuid=" + uuid;
        }

        if(!rawPassword.equals("")){
            String password = Parakeet.dirty(rawPassword);
            user.setPassword(password);
            userRepo.updatePassword(user);
        }

        data.put("message", "Password successfully updated");
        return "/pages/user/success.jsp";
    }

}
