package me.donsdev.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.donsdev.domain.User;
import me.donsdev.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/login")
	private String login_form() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	private String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null || !user.passwordMatch(password)) {
			System.out.println("login FAILED");
			return "redirect:/users/login";
		}else if(user.passwordMatch(password)) {
			session.setAttribute(HttpSessionUtils.CURRENT_USER, user);
			System.out.println("login SUCCESS");	
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	private String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.CURRENT_USER);
		return "redirect:/";
	}
	
	@PostMapping("")
	private String create(User user, HttpSession session) {
		System.out.println(user);
		userRepository.save(user);
		session.setAttribute(HttpSessionUtils.CURRENT_USER, user);
		return "redirect:/"; 
	}
	
	@GetMapping("/new")
	private String new_user_form(Model model) {
		return "/user/form";
	}

	@GetMapping("/{id}/edit")
	private String edit_user_form(@PathVariable Long id, Model model, HttpSession session) {
		if (userRepository.existsById(id)){
			User sessionUser = HttpSessionUtils.currentUser(session);
			User user = userRepository.findById(id).get();
			if(!HttpSessionUtils.isLoginUser(session)){
				return "redirect:/users/login";
			}
			if(sessionUser.getId() != user.getId()) {
				System.out.println("req login");
				System.out.println(sessionUser);
				return "redirect:/users";			
			}
			model.addAttribute("user", user);
		}else {
			return "redirect:/";
		}
		return "/user/edit_form";
	}

	@PutMapping("/{id}/update")
	private String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		User sessionUser = HttpSessionUtils.currentUser(session);
		if(!HttpSessionUtils.isLoginUser(session)){
			return "redirect:/users/login";
		}
		if(sessionUser.getId() != id) {
			System.out.println("req login");
			System.out.println(sessionUser);
			return "redirect:/users";			
		}
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	private String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
}
