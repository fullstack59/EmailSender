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

import com.model.User;
import com.rabbitmq.contract.UserDeleted;
import com.rabbitmq.contract.UserInserted;
import com.rabbitmq.contract.UserUpdated;
import com.rabbitmq.publisher.UserPublisher;
import com.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class UserController {
	
	private final UserRepository userRepository;
	private final UserPublisher userPublisher;
	
	UserController (UserRepository userRepository, UserPublisher userPublisher) {
		this.userRepository = userRepository;
		this.userPublisher = userPublisher;
	}
	
	@GetMapping
	List<User> findAll() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	User findById(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
		}
		return user.get();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	void insert(@Valid @RequestBody User user) {
		userRepository.insert(user);
		userPublisher.publish(new UserInserted(user.Id(), user.Name(), user.Email(), user.Role().Name()));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	void update(@Valid @RequestBody User user, @PathVariable Integer id) {
		userRepository.update(user, id);
		userPublisher.publish(new UserUpdated(user.Id(), user.Name(), user.Email(), user.Role().Name()));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		userRepository.delete(id);
		userPublisher.publish(new UserDeleted(id));
	}

}
