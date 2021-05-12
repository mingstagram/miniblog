package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;
import com.cos.blog.model.GuestBook;
import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략가능
public interface GuestRepository extends JpaRepository<GuestBook, Integer> {  
	@Modifying
	@Query(value="INSERT INTO guestbook(writer, password, content, createDate) VALUES (?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(String writer, String password, String content); // 업데이트 된 행의 갯수를 리턴해줌.
	
}
