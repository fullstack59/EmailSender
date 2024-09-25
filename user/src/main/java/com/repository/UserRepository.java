package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.model.Role;
import com.model.User;

@Repository
public class UserRepository implements ICrudRepository<User>{
	
	private final JdbcClient jdbcClient;
	
	public UserRepository (JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	@Override
	public List<User> findAll() {
		return jdbcClient.sql("""
				SELECT u.user_id, u.user_name, u.user_email,
					   r.role_id, r.role_name, r.role_descrip,
			    FROM user u
			    JOIN role r ON u.role_id = r.role_id
				""")
				.query((row, rowNum) -> new User(
					row.getInt("user_id"),
					row.getString("user_name"),
					row.getString("user_email"),
					new Role(
						row.getInt("role_id"),
						row.getString("role_name"),
						row.getString("role_descrip"))
				))
				.list();
	}

	@Override
	public Optional<User> findById(Integer id) {
		return jdbcClient.sql("""
				SELECT u.user_id, u.user_name, u.user_email,
				   r.role_id, r.role_name, r.role_descrip,
			    FROM user u
			    JOIN role r ON u.role_id = r.role_id
			    WHERE u.role_id = :id
				""")
				.param("id", id)
				.query((row, rowNum) -> new User(
					row.getInt("user_id"),
					row.getString("user_name"),
					row.getString("user_email"),
					new Role(
						row.getInt("role_id"),
						row.getString("role_name"),
						row.getString("role_descrip"))
				))
				.optional();
	}

	@Override
	public void insert(User obj) {
		var updated = jdbcClient.sql("INSERT INTO user(user_name, user_email, role_id) VALUES(?,?,?)")
				.params(List.of(obj.Name(), obj.Email(), obj.Role().Id()))
				.update();
		
		Assert.state(updated == 1, "Failed to insert user " + obj.Name());		
	}

	@Override
	public void update(User obj, Integer id) {
		var updated = jdbcClient.sql("UPDATE user SET user_name = ?, user_email = ?, role_id = ? WHERE user_id = ?")
				.params(List.of(obj.Name(), obj.Email(), obj.Role().Id(), id))
				.update();
		
		Assert.state(updated == 1, "Failed to update user " + obj.Name());		
	}

	@Override
	public void delete(Integer id) {
		var updated = jdbcClient.sql("DELETE FROM user WHERE user_id = :id")
				.param("id", id)
				.update();
		
		Assert.state(updated == 1, "Failed to delete role " + id);
	}

	@Override
	public void saveAll(List<User> objs) {
		objs.stream().forEach(this::insert);		
	}
	
	
}
