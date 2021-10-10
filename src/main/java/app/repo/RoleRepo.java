package app.repo;

import app.model.Role;
import eco.m1.annotate.Inject;
import eco.m1.annotate.Jdbc;
import eco.m1.jdbc.Q;

import java.util.ArrayList;
import java.util.List;

@Jdbc
public class RoleRepo {

	@Inject
	public Q q;

	public int count() {
		String sql = "select count(*) from roles";
		int count = (Integer) q.get(sql, new Object[] { }, Integer.class);
	 	return count; 
	}

	public Role get(int id) {
		String sql = "select * from roles where id = {}";
		Role role = (Role) q.get(sql, new Object[] { id },Role.class);
		return role;
	}
	
	public Role find(String name) {
		Role role = null; 
		try{
			String sql = "select * from roles where name = '{}'";
			role = (Role) q.get(sql, new Object[] { name }, Role.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return role;
	}

	public List<Role> findAll() {
		String sql = "select * from roles";
		List<Role> roles = (ArrayList) q.list(sql, new Object[]{}, Role.class);
		return roles;
	}
	
	public void save(Role role) {
		String sql = "insert into roles (name) values('{}')";
		q.save(sql, new Object[]{
				role.getName().replaceAll("'", "''")
		});
	}
	
}