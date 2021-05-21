package com.cos.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer> { 
   
	@Query(value= "SELECT * FROM board b WHERE b.title LIKE %:title%  ORDER BY b.id DESC LIMIT :start, :page", nativeQuery = true)
	List<Board> findByTitle(@Param("title") String search, @Param("start") int startPage, @Param("page") int pageSize); 
	
	@Query(value= "SELECT count(*) FROM board b WHERE b.title LIKE %:title%", nativeQuery = true)
	Integer findByTitleCount(@Param("title") String search);
	
	@Query(value= "SELECT * FROM board ORDER BY id DESC LIMIT ?, ?", nativeQuery = true)
	List<Board> page(int startPage, int pageSize);
	
	
	
}
