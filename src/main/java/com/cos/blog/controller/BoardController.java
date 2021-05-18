package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.LikeCount;
import com.cos.blog.model.User;
import com.cos.blog.repository.LikeCountRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.BoardService; 

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LikeCountRepository likeCountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// 컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {	
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; // viewResolver 작동!!
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->{
			return new IllegalArgumentException("유저 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});  
		LikeCount likeCount = likeCountRepository.like(user.getId(), id);
		model.addAttribute("board", boardService.글상세보기(id)); 
		if(likeCount != null)
			model.addAttribute("likeCount", likeCount.getId()); 
		boardService.조회수(id);
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	 
	
}
