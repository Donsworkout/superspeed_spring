package me.donsdev.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.donsdev.domain.Answer;
import me.donsdev.domain.AnswerRepository;
import me.donsdev.domain.Question;
import me.donsdev.domain.QuestionRepository;
import me.donsdev.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";			
		}
		User sessionUser = HttpSessionUtils.currentUser(session);
		Question question = questionRepository.findById(questionId).orElse(null);
		Answer answer = new Answer(contents, sessionUser, question);
		answerRepository.save(answer);
		return "redirect:/questions/{questionId}";
	}
}
