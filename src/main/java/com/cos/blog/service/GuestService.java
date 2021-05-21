package com.cos.blog.service;


import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.GuestBook;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.GuestRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.  IoC를 해준다.
@Service
public class GuestService {
  
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private UserRepository userRepository; 
	
	@Transactional
	public void 방명록쓰기(GuestBook guestBook) {
		guestRepository.save(guestBook);
	}
	
	@Transactional(readOnly = true)
	public List<GuestBook> 방명록목록(){
		return guestRepository.findAll();
	}
	
	@Transactional
	public void 방명록삭제(int id) {
		guestRepository.deleteById(id);
	}
	
}
