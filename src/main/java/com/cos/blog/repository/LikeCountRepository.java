package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.LikeCount;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략가능
public interface LikeCountRepository extends JpaRepository<LikeCount, Integer> { 

	@Query(value= "SELECT * FROM likecount WHERE userId=? AND boardId=?", nativeQuery = true)
	LikeCount like(int userId, int boardId);
	 
	
}
