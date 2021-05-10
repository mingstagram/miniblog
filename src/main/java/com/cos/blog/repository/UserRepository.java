package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> { // User : User테이블이 관리하는 repository
																																	   // Integer : PK값이 Integer
	// Select * from user where username = 1?;
	Optional<User> findByUsername(String username);	
	
	@Query(value= "SELECT username FROM user WHERE email=?", nativeQuery = true)
	String findByEmail(String email);
	
	@Query(value= "SELECT * FROM user WHERE username=? AND email=?", nativeQuery = true)
	User findByUsernameAndEmail(String username, String email);
	 
}


//JPA Naming 쿼리
	// 방법1
	// SELECT * FROM user WHERE username = ?(매개변수username) AND password = ?(매개변수password)
//User findByUsernameAndPassword(String username, String password);
 
 // 방법2
//@Query(value=" SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//User login(String username, String password);
