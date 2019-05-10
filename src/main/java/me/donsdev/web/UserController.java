package me.donsdev.web;

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
	
	@PostMapping("")
	private String create(User user) {
		System.out.println(user);
		userRepository.save(user);
		return "redirect:/users"; 
	}
	
	@GetMapping("/new")
	private String new_user_form(Model model) {
		return "/user/form";
	}

	@GetMapping("/{id}/edit")
	private String edit_user_form(@PathVariable Long id, Model model) {
		if (userRepository.existsById(id)){
			User user = userRepository.findById(id).get();
			model.addAttribute("user", user);
		}else {
			return "redirect:/users";
		}
		return "/user/edit_form";
	}
	
	@GetMapping("")
	private String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@PutMapping("/{id}/update")
	private String update(@PathVariable Long id, User updated_user) {
		System.out.println(id);
		User user = userRepository.findById(id).get();
		user.update(updated_user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}
