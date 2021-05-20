package com.cos.blog.service;


import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Criteria;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌.  IoC를 해준다.
@Service
public class BoardService {
  
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	 
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setBlike(0);
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	} 
	
	@Transactional
	public List<Board> 검색목록(String search, Criteria cri){ 
		return boardRepository.findByTitle(search, cri.getPageStart(), cri.getPerPageNum());
	}
	
	@Transactional(readOnly = true)
	public List<Board> 글목록(){ 
		return boardRepository.findAll();
	}
	
	@Transactional
	public int 게시글갯수() {
		return (int)boardRepository.count();
	}
	
	@Transactional
	public int 검색게시글갯수(String search) { 
		return boardRepository.findByTitleCount(search);
	}
	
	@Transactional
	public List<Board> 현재페이지(Criteria cri){
		return boardRepository.page(cri.getPageStart(), cri.getPerPageNum());
	}
	
	@Transactional
	public Board 글상세보기(int id) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		}); 
		
		return board;
	}
	 
	
	@Transactional
	public void 조회수(int id) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
		int cnt = board.getCount() + 1;
		board.setCount(cnt);
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글 삭제하기 id : " + id); 
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(Board requestBoard, int id) { 
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속화 완료 
		board.setCount(requestBoard.getCount());
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시(Service가 종료될 때) 트랜잭션이 종료 됩니다. 이때 더티체킹 - 자동업데이트. DB flush
	}
	
	@Transactional
	public void 추천(int id) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); 
		int cnt = board.getBlike() + 1; 
		board.setBlike(cnt); 
	}
	
	@Transactional
	public void 비추천(int id) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); 
		int cnt = board.getBlike() - 1; 
		board.setBlike(cnt); 
	}
	
	
	@Transactional 
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) { // DTO 사용해서 깔끔하게 만들기
		
		/*
		 * User user =
		 * userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다."); }); //
		 * 영속화 완료;
		 * 
		 * Board board =
		 * boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다."); }); //
		 * 영속화 완료;
		 * 
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replySaveRequestDto.getContent()) .build();
		 */
		 
		// reply.update(user, board, replySaveRequestDto.getContent()); 함수를 만들어 코드다이어트를 할 수 있다.
		
		// 네이티브쿼리로 간단하게 만들기
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("result : "+ result);
	}
	
	@Transactional
	public void 댓글삭제(int replyId) { 
		replyRepository.deleteById(replyId);
	}
	
}
