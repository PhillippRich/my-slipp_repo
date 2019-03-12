package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}

	@PostMapping("/login")
	public String logIn(String userId, String password, HttpSession httpSession) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			return "redirect:/users/loginForm";
		}

		if (!user.getPassword().equals(password)) {
			return "redirect:/users/loginForm";
		}

		httpSession.setAttribute("user", user);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logOut(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		return "redirect:/";
	}

	@GetMapping("/form")
	public String signUp() {
		return "/user/form";
	}

	@GetMapping("/{id}/updateForm")
	public String updateform(@PathVariable Long id, Model model) {
		model.addAttribute(userRepository.findOne(id));
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String updateUser(@PathVariable Long id, User newUserInfo) {
		User user = userRepository.findOne(id);
		user.updateUser(newUserInfo);
		userRepository.save(user);
		return "redirect:/users";
	}

	@PostMapping("")
	public String createUser(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String getList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	// Old
	// @GetMapping("/form")
	// public String signUp() {
	// return "form";
	// }
	//
	// @PostMapping("/create")
	// public String create(User user) {
	// System.out.println("user id : " + user);
	// userRepository.save(user);
	// return "redirect:/list";
	// }
	//
	// @GetMapping("/list")
	// public String list(Model model) {
	// model.addAttribute("users", userRepository.findAll());
	// return "list";
	// }

}