package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.LikeCount;
import com.cos.blog.model.User;
import com.cos.blog.repository.LikeCountRepository;

@Service
public class LikeCountService { 
	
	@Autowired
	private LikeCountRepository likeCountRepository;
	
	
	@Transactional
	public void 추천유무(Board board, User user) {  
		LikeCount likeCount = new LikeCount();
		likeCount.setBoard(board);
		likeCount.setUser(user);
		likeCountRepository.save(likeCount);
	} 
	
}
