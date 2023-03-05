package com.springboot.blog.payload;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	private long id;

	// title should not be empty
	// title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;

	// description should not be empty
	// description should have at least 5 characters
	@NotEmpty
	@Size(min = 5, message = "Post title should have at least 5 characters")
	private String description;
	
	// Content should not be empty
	@NotEmpty
	private String content;

	private Set<CommentDto> comments;

}
