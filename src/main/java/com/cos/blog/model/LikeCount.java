package com.cos.blog.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LikeCount {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
		private int id;
	
		// Many = Board, One = User -- 한명의 유저는 여러개의 게시글을 쓸 수 있다.
		@ManyToOne(fetch = FetchType.EAGER) // EAGER : DB에 무조건 들고와라. default값 : LAZY : DB
		// @OneToOne -- 한명의 유저는 한개의 게시글밖에 쓸 수 없다.
		@JoinColumn(name="userId")
		private User user; // DB는 오브젝트를 저장할 수 없기에 FK를 사용하지만, 자바는 오브젝트를 저장할 수 있다.
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="boardId")
		private Board board;
}
