package me.donsdev.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.donsdev.domain.Question;
import me.donsdev.domain.QuestionRepository;
import me.donsdev.domain.User;


@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("")
	public String listQuestion(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "/questions/q_list";
	}
	
	@GetMapping("/new")
	public String newForm(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";			
		}
		return "/questions/form";
	}
	
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";			
		}
		User sessionUser = HttpSessionUtils.currentUser(session);
		Question question = new Question(sessionUser.getUserId(), title, contents);
		questionRepository.save(question);
		return "redirect:/questions";
	}
}
