package com.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public record User (
		Integer Id,
		@NotEmpty
		String Name,
		@Email
		String Email,
		Role Role) {

}
