package me.donsdev.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;

import me.donsdev.web.HttpSessionUtils;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	private String title;
	
	@Column(length=20000)
	private String contents;
	
	private LocalDateTime createDate;
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getId() {
		return id;
	}
	public User getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContents() {
		return contents;
	}
	
	public void update(Question question) {
		this.title = question.title;
		this.contents = question.contents;
	}
	
	// 디폴트 생성자 
	public Question() {}
	
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public boolean isMyQuestion(HttpSession session) {
		User user = HttpSessionUtils.currentUser(session);
		if(user.getId() == this.writer.getId()) {
			return true;
		}else {
			return false;
		}
	}
	
	public String FormattedCreateDate() {
		if (createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	}
}
