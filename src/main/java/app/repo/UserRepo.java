package app.repo;

import app.common.Constants;
import app.model.Role;
import app.model.User;
import app.model.UserPermission;
import app.model.UserRole;
import eco.m1.annotate.Inject;
import eco.m1.annotate.Jdbc;
import eco.m1.jdbc.Q;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Jdbc
public class UserRepo {

	@Inject
	Q q;

	@Inject
	RoleRepo roleRepo;

	public User getLastInserted() {
		String sql = "select max(id) from users";
		User user = (User) q.get(sql, new Object[]{}, User.class);
		return user;
	}

	public long getCount() {
		String sql = "select count(*) from users";
		Long count = (Long) q.get(sql, new Object[] { }, Long.class);
	 	return count; 
	}

	public User get(long id) {
		String sql = "select * from users where id = {}";
		User user = (User) q.get(sql, new Object[] { id }, User.class);
		
		if(user == null) user = new User();
		return user;
	}

	public User getByUsername(String username) {
		User user = null;
		try{
			String sql = "select * from users where username = '{}'";
			user = (User) q.get(sql, new Object[] { username }, User.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("user : " + user);
		return user;
	}
	
	public List<User> findAll() {
		String sql = "select * from users";
		List<User> users = (ArrayList) q.list(sql, new Object[]{}, User.class);
		System.out.println("users size : " + users);
		return users;
	}

	public User save(User user) {
		String sql = "insert into users (username, password, date_created) values ('{}', '{}', {})";
		q.save(sql, new Object[]{
				user.getUsername().replaceAll("'", "''"),
				user.getPassword().replaceAll("'", "''"),
				user.getDateCreated()
		});

		String savedSql = "select * from users order by id desc limit 1 ";
		User savedUser = (User) q.get(savedSql, new Object[]{}, User.class);

		checkSaveDefaultUserRole(savedUser.getId());
		checkSaveDefaultUserPermission(savedUser.getId());

		return savedUser;
	}

	public User saveAdministrator(User user) {
		String sql = "insert into users (username, password) values ('{}', '{}')";
		q.save(sql, new Object[]{
				user.getUsername().replaceAll("'", "''"),
				user.getPassword().replaceAll("'", "''")
		});

		String savedSql = "select * from users order by id desc limit 1 ";
		User savedUser = (User) q.get(savedSql, new Object[]{}, User.class);

		checkSaveSuperRole(savedUser.getId());
		checkSaveDefaultUserPermission(savedUser.getId());

		return savedUser;
	}

	public boolean updateUuid(User user) {
		String sql = "update users set uuid = '{}' where id = {}";
		q.update(sql, new Object[]{
				user.getUuid().replaceAll("'", "''"),
				user.getId()
		});
		return true;
	}
	
	public boolean updatePassword(User user) {
		String sql = "update users set password = '{}' where id = {}";
		q.update(sql, new Object[]{
				user.getPassword().replaceAll("'", "''"),
				user.getId()
		});
		return true;
	}

	public User getByUsernameAndUuid(String username, String uuid){
		User user = null;
		try{
			String sql = "select * from users where username = '{}' and uuid = '{}'";
			user = (User) q.get(sql, new Object[] {
					username.replaceAll("'", "''"),
					uuid.replaceAll("'", "''")
			}, User.class);

		}catch(Exception ex){}

		return user;
	}
	
	public boolean delete(long id) {
		String sql = "delete from users where id = {}";
		q.update(sql, new Object[] { id });
		return true;
	}

	public String getUserPassword(String username) {
		User user = getByUsername(username);
		return user.getPassword();
	}

	public boolean checkSaveSuperRole(long userId){
		Role role = roleRepo.find(Constants.SUPER_ROLE);
		UserRole existing = getUserRole(userId, role.getId());
		if(existing == null){
			saveUserRole(userId, role.getId());
		}
		return true;
	}

	public boolean checkSaveDefaultUserRole(long userId){
		Role role = roleRepo.find(Constants.USER_ROLE);
		UserRole existing = getUserRole(userId, role.getId());
		if(existing == null){
			saveUserRole(userId, role.getId());
		}
		return true;
	}

	public UserRole getUserRole(long userId, long roleId){
		String sql = "select * from user_roles where user_id = {} and role_id = {}";
		UserRole userRole = null;
		try {
			userRole = (UserRole) q.get(sql, new Object[]{ userId, roleId}, UserRole.class);
		}catch(Exception e){
		}
		return null;
	}

	public boolean checkSaveDefaultUserPermission(long userId){
		String permission = Constants.USER_MAINTENANCE + userId;
		UserPermission existing = getUserPermission(userId, permission);
		if(existing == null){
			savePermission(userId, permission);
		}
		return true;
	}

	public UserPermission getUserPermission(long userId, String permission){
		String sql = "select * from user_permissions where user_id = {} and permission = '{}'";
		UserPermission userPermission = null;
		try {
			userPermission = (UserPermission) q.get(sql, new Object[]{ userId, permission}, UserPermission.class);
		}catch(Exception ex){ }
		return userPermission;
	}

	public boolean saveUserRole(long userId, long roleId){
		String sql = "insert into user_roles (role_id, user_id) values ({}, {})";
		q.save(sql, new Object[]{roleId, userId});
		return true;
	}
	
	public boolean savePermission(long accountId, String permission){
		String sql = "insert into user_permissions (user_id, permission) values ({}, '{}')";
		q.save(sql, new Object[]{
				accountId,
				permission.replaceAll("'", "''")} );
		return true;
	}
	
	public boolean deleteUserRoles(long userId){
		String sql = "delete from user_roles where user_id = {}";
		q.delete(sql, new Object[]{ userId });
		return true;
	}
	
	public boolean deleteUserPermissions(long userId){
		String sql = "delete from user_permissions where user_id = {}";
		q.delete(sql, new Object[] { userId });
		return true;
	}

	public Set<String> getUserRoles(long id) {
		String sql = "select r.name as name from user_roles ur inner join roles r on r.id = ur.role_id where ur.user_id = {}";
		List<UserRole> rolesList = (ArrayList) q.list(sql, new Object[]{ id }, UserRole.class);
		Set<String> roles = new HashSet<>();
		for(UserRole role: rolesList){
			roles.add(role.getName());
		}
		return roles;
	}

	public Set<String> getUserPermissions(long id) {
		String sql = "select permission from user_permissions where user_id = {}";
		List<UserPermission> permissionsList = (ArrayList) q.list(sql, new Object[]{ id }, UserPermission.class);
		Set<String> permissions = new HashSet<>();
		for(UserPermission permission: permissionsList){
			permissions.add(permission.getPermission());
		}
		return permissions;
	}


    public boolean update(User user) {
		String sql = "update users set username = '{}', password = '{}' where id = {}";
		q.update(sql, new Object[]{
				user.getUsername().replaceAll("'","''"),
				user.getPassword().replaceAll("'","''"),
				user.getId()
		});
		return true;
    }
}