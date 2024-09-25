package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.model.Role;

@Repository
public class RoleRepository implements ICrudRepository<Role>{

	private final JdbcClient jdbcClient;
	
	public RoleRepository (JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}
	
	@Override
	public List<Role> findAll() {
		return jdbcClient.sql("select * from role")
				.query(Role.class)
				.list();
	}

	@Override
	public Optional<Role> findById(Integer id) {
		return jdbcClient.sql("SELECT role_id, role_name, role_descrip FROM role WHERE role_id = :id")
				.param("id", id)
				.query(Role.class)
				.optional();
	}

	@Override
	public void insert(Role obj) {
		var updated = jdbcClient.sql("INSERT INTO role(role_name, role_descrip) VALUES(?,?)")
				.params(List.of(obj.Name(), obj.Descrip()))
				.update();
		
		Assert.state(updated == 1, "Failed to insert role " + obj.Name());
	}

	@Override
	public void update(Role obj, Integer id) {
		var updated = jdbcClient.sql("UPDATE role SET role_name = ?, role_descrip = ? WHERE role_id = ?")
				.params(List.of(obj.Name(), obj.Descrip(), id))
				.update();
		
		Assert.state(updated == 1, "Failed to update role " + obj.Name());
	}

	@Override
	public void delete(Integer id) {
		var updated = jdbcClient.sql("DELETE FROM role WHERE role_id = :id")
				.param("id", id)
				.update();
		
		Assert.state(updated == 1, "Failed to delete role " + id);
	}

	@Override
	public void saveAll(List<Role> objs) {
		objs.stream().forEach(this::insert);
	}

}
