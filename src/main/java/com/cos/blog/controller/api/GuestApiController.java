package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.GuestBook;
import com.cos.blog.model.Reply;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.GuestService;

@RestController
public class GuestApiController {
	 
	@Autowired
	private BoardService boardService;
	 
	@Autowired
	private GuestService guestService;
	
	@PostMapping("/api/guest")
	public ResponseDto<Integer> save(@RequestBody GuestBook guestBook, @AuthenticationPrincipal PrincipalDetail principal){
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		guestService.방명록쓰기(guestBook, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
