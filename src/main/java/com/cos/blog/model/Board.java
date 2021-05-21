package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리
	
	private int count; // 조회수
	
	private int blike; // 추천/비추천
	
	// Many = Board, One = User -- 한명의 유저는 여러개의 게시글을 쓸 수 있다.
	@ManyToOne(fetch = FetchType.EAGER) // EAGER : DB에 무조건 들고와라. default값 : LAZY : DB
	// @OneToOne -- 한명의 유저는 한개의 게시글밖에 쓸 수 없다.
	@JoinColumn(name="userId")
	private User user; // DB는 오브젝트를 저장할 수 없기에 FK를 사용하지만, 자바는 오브젝트를 저장할 수 있다.
 
	@OneToMany(mappedBy = "board" , fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // "board" <- Reply 모델에 있는 필드 "board"
	// mappedBy - 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 컬럼 만들지 마세요.
	@JsonIgnoreProperties({"board"}) // 무한 참조 방지
	@OrderBy("id desc")
	private List<Reply> replys; 
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
