package app.jdbc;

import app.model.User;
import app.repo.UserRepo;
import eco.m1.annotate.Element;
import eco.m1.annotate.Inject;
import xyz.goioc.resources.access.Accessor;

import java.util.Set;

@Element
public class M1Accessor implements Accessor {

    @Inject
    private UserRepo userRepo;

    public String getPassword(String username){
        String password = userRepo.getUserPassword(username);
        return password;
    }

    public Set<String> getRoles(String username){
        User user = userRepo.getByUsername(username);
        Set<String> roles = userRepo.getUserRoles(user.getId());
        return roles;
    }

    public Set<String> getPermissions(String username){
        User user = userRepo.getByUsername(username);
        Set<String> permissions = userRepo.getUserPermissions(user.getId());
        return permissions;
    }

}
