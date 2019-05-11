package me.donsdev.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		Question question = questionRepository.getOne(id);
		if(question == null) {
			return "redirect:/";
		}
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/questions/show";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.getOne(id);
		try {
			hasPermission(session, question);
			model.addAttribute("question", questionRepository.getOne(id));
			return "/questions/edit_form";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
			return "/questions/" + id;
		}
	}
	
	@PutMapping("/{id}")
	public String upadteQuestion(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
		Question question = questionRepository.getOne(id);
		if(!HttpSessionUtils.isLoginUser(session)){
			return "redirect:/users/login";
		}
		if(!question.isMyQuestion(session) || question == null) {
			return "redirect:/questions/{id}";
		}
		question.update(updatedQuestion);
		questionRepository.save(question);
		return "redirect:/questions/%d";
	}
	
	@DeleteMapping("/{id}")
	public String deleteQuestion(@PathVariable long id, Question deleteQuestion, HttpSession session) {
		Question question = questionRepository.getOne(id);
		if(!HttpSessionUtils.isLoginUser(session)){
			return "redirect:/users/login";
		}
		if(!question.isMyQuestion(session) || question == null) {
			return "redirect:/questions/{id}";
		}	
		questionRepository.delete(question);
		return "redirect:/questions";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/login";			
		}
		User sessionUser = HttpSessionUtils.currentUser(session);
		Question question = new Question(sessionUser, title, contents);
		questionRepository.save(question);
		return "redirect:/questions";
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다");
		}
		if(!question.isMyQuestion(session) || question == null) {
			throw new IllegalStateException("권한이 없습니다");
		}	
		return false;
	}
}
