package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.model.Role;
import com.repository.RoleRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	private final RoleRepository roleRepository;
	
	RoleController (RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@GetMapping
	List<Role> findAll() {
		return roleRepository.findAll();
	}
	
	@GetMapping("/{id}")
	Role findById(@PathVariable Integer id) {
		Optional<Role> role = roleRepository.findById(id);
		if (role.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
		}
		return role.get();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	void insert(@Valid @RequestBody Role role) {
		roleRepository.insert(role);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	void update(@Valid @RequestBody Role role, @PathVariable Integer id) {
		roleRepository.update(role, id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		roleRepository.delete(id);
	}
	
}
