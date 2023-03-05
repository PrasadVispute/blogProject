package com.springboot.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	@NotEmpty(message = "Name must not be null or empty")
	private String name;
	
	@Email
	@NotEmpty(message = "Email must not be null or empty")
	private String email;
	
	@NotEmpty(message = "Comment must not be null or empty")
	@Size(max = 500, message = "Comment must not exceed 500 characters")
	private String body;

}
