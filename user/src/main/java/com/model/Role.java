package com.model;

import jakarta.validation.constraints.NotEmpty;

public record Role (
		Integer Id,
		@NotEmpty
		String Name,
		@NotEmpty
		String Descrip) {

}
