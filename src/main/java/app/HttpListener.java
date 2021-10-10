package app;

import app.common.Constants;
import app.config.DbConfig;
import app.model.Role;
import app.model.User;
import app.repo.RoleRepo;
import app.repo.UserRepo;
import eco.m1.M1;
import eco.m1.jdbc.BasicDataSource;
import org.h2.tools.RunScript;
import xyz.goioc.Parakeet;
import xyz.goioc.resources.access.Accessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileReader;
import java.nio.file.Paths;
import java.sql.Connection;

@WebListener
public class HttpListener implements ServletContextListener {

    M1 m1;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {

            ServletContext context = event.getServletContext();
            Class[] configs = new Class[]{ DbConfig.class };
            String[] resources = new String[]{ "assets" };

            m1 = new M1.Injector()
                    .withConfigs(configs)
                    .withWebEnabled(true)
                    .withContext(context)
                    .withWebResources(resources)
                    .withDataEnabled(true)
                    .inject();

            BasicDataSource dataSource = (BasicDataSource)m1.get("datasource");
            Connection conn = dataSource.getConnection();
            String createSql = Paths.get("src", "main", "resources", "create-db.sql")
                                    .toAbsolutePath()
                                    .toString();
            RunScript.execute(conn, new FileReader(createSql));

            RoleRepo roleRepo = (RoleRepo) m1.get("rolerepo");
            UserRepo userRepo = (UserRepo) m1.get("userrepo");
            Accessor accessor = (Accessor) m1.get("m1accessor");
            Parakeet.perch(accessor);

            Role superRole = roleRepo.find(Constants.SUPER_ROLE);
            Role userRole = roleRepo.find(Constants.USER_ROLE);

            if(superRole == null){
                superRole = new Role();
                superRole.setName(Constants.SUPER_ROLE);
                roleRepo.save(superRole);
            }

            if(userRole == null){
                userRole = new Role();
                userRole.setName(Constants.USER_ROLE);
                roleRepo.save(userRole);
            }

            User existing = userRepo.getByUsername(Constants.SUPER_USERNAME);
            String password = Parakeet.dirty(Constants.SUPER_PASSWORD);

            if(existing == null){
                User superUser = new User();
                superUser.setUsername(Constants.SUPER_USERNAME);
                superUser.setPassword(password);
                userRepo.saveAdministrator(superUser);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            BasicDataSource dataSource = (BasicDataSource)m1.get("datasource");
            Connection conn = dataSource.getConnection();
            String dropSql = Paths.get("src", "main", "resources", "drop-db.sql")
                                    .toAbsolutePath()
                                    .toString();
            RunScript.execute(conn, new FileReader(dropSql));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
