package me.donsdev.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	@GetMapping("/helloworld")
	public String welcome(String name, Model model) {
		if(name == null) {
			name = "visitor";
		}
		System.out.println(name); 
		model.addAttribute("name", name);
		return "welcome";
	}
}
