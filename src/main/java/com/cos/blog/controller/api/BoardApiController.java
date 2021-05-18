package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.LikeCount;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.LikeCountRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.LikeCountService;

@RestController
public class BoardApiController {
	 	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LikeCountService likeCountService;
	
	@Autowired
	private LikeCountRepository likeCountRepository;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) { 
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@RequestBody Board board, @PathVariable int id){
		boardService.글수정하기(board, id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/boardlike/{id}")
	public ResponseDto<Integer> like(@PathVariable("id") int boardId, @AuthenticationPrincipal PrincipalDetail principal){
		User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->{
			return new IllegalArgumentException("유저 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});  
	    // likeCount 테이블 조회
		LikeCount likeCount = likeCountRepository.like(user.getId(), boardId); 
		if(likeCount == null) { // 값이 없으면 추천 + 1
			boardService.추천(boardId); 
			Board board = boardRepository.findById(boardId).orElseThrow(()->{
				return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
			});  
			likeCountService.추천유무(board, user);
		} else { // 값이 있으면 값을 삭제하고 추천 - 1
			likeCountRepository.delete(likeCount); 
			boardService.비추천(boardId);   
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// dto 사용하지 않은 이유는!! 
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {  
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replySave(@PathVariable int replyId) {  
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	 
	
	
}
