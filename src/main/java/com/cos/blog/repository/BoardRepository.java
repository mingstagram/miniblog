package com.cos.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer> { 
   
	List<Board> findByTitleContaining(String search); 
	
	@Query(value= "SELECT * FROM board ORDER BY id DESC LIMIT ?, ?", nativeQuery = true)
	List<Board> page(int startPage, int pageSize);
	
}
