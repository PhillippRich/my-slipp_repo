package net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String welcome() {
//		System.out.println("name : " + name + ", age : " + age);
//		model.addAttribute("name", name);
//		model.addAttribute("age", age);
		// return page resource/template/welcome.html
		return "index";
	}
}
