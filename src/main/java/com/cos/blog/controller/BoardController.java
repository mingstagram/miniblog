package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Criteria;
import com.cos.blog.model.LikeCount;
import com.cos.blog.model.PageMaker; 
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
	@GetMapping({"","/","/?search={search}&page={page}"})
	public String index(String search, Model model, Criteria cri) {
		
		PageMaker pageMaker = new PageMaker();
		if(search == null) {
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(boardService.게시글갯수()); // 총 게시글의 수
			List<Board> boards = boardService.현재페이지(cri);
			model.addAttribute("search",search);
			model.addAttribute("boards", boards); 
			model.addAttribute("curPageNum", cri.getPage());
			model.addAttribute("pageMaker", pageMaker);
		} else {
			System.out.println("1111111111111111111111111111111111111111");
			pageMaker.setCri(cri);
			System.out.println("222222222222222222222222222222");
			pageMaker.setTotalCount(boardService.검색게시글갯수(search));
			System.out.println("333333333333333333333333333333333");
			List<Board> searchBoards = boardService.검색목록(search, cri);
			System.out.println("4444444444444444444444444444444");
			model.addAttribute("search",search);
			model.addAttribute("boards", searchBoards); 
			model.addAttribute("curPageNum", cri.getPage());
			model.addAttribute("pageMaker", pageMaker);
		}
		/*
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.게시글갯수()); // 총 게시글의 수
		List<Board> boards = boardService.현재페이지(cri);
		model.addAttribute("boards", boards); 
		model.addAttribute("curPageNum", cri.getPage());
		model.addAttribute("pageMaker", pageMaker);
		*/

		return "index"; // viewResolver 작동!!
	}
	
	/*
	 * @PostMapping({"","/"}) public String searchIndex(String search, Model model,
	 * Criteria cri) { PageMaker pageMaker = new PageMaker();
	 * 
	 * pageMaker.setCri(cri); pageMaker.setTotalCount(boardService.검색게시글갯수(search,
	 * cri)); List<Board> searchBoards = boardService.검색목록(search, cri);
	 * model.addAttribute("boards", searchBoards); model.addAttribute("curPageNum",
	 * cri.getPage()); model.addAttribute("pageMaker", pageMaker);
	 * 
	 * return "index"; }
	 */
	

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
