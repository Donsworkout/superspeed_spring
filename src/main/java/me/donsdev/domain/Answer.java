package me.donsdev.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;

import me.donsdev.web.HttpSessionUtils;

@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
	private Question question;
	
	@Lob
	private String contents;
	
	private LocalDateTime createDate;
	
	public Answer() {}
	
	public Long getId() {
		return id;
	}

	public User getWriter() {
		return writer;
	}

	public Question getQuestion() {
		return question;
	}

	public String getContents() {
		return contents;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Answer(String contents, User writer, Question question) {
		this.writer = writer;
		this.question = question;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }

	public boolean isMyAnswer(HttpSession session) {
		User user = HttpSessionUtils.currentUser(session);
		if(user.getId() == this.writer.getId()) {
			return true;
		}else {
			return false;
		}
	}
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + ", createDate=" + createDate
				+ "]";
	}
	
}
