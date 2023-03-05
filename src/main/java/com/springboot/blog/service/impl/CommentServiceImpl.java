package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		// Retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		// set comment entity to DB
		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto,Comment.class);
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}

	@Override
	public List<CommentDto> getCommentsByPostID(long postId) {
		// Retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);

		// convert list of comment entities to comment Dto
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {

		// Retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Retrieve comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

//		if(!comment.getPost().getId().equals(post.getId())) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to this post");
//		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequsest) {

		// Retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Retrieve comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		comment.setName(commentRequsest.getName());
		comment.setEmail(commentRequsest.getEmail());
		comment.setBody(commentRequsest.getBody());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {

		// Retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// Retrieve comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		commentRepository.delete(comment);

	}

}
