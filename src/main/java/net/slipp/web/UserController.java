package net.slipp.web;

import static net.slipp.web.HttpSessionUtils.HTTP_SESSION_USER;
import static net.slipp.web.HttpSessionUtils.isLoginUser;
import static net.slipp.web.HttpSessionUtils.getUserFromSession;

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

		if (user.matchPassword(password)) {
			return "redirect:/users/loginForm";
		}

		httpSession.setAttribute(HTTP_SESSION_USER, user);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logOut(HttpSession httpSession) {
		httpSession.removeAttribute(HTTP_SESSION_USER);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String signUp() {
		return "/user/form";
	}

	@GetMapping("/{id}/form")
	public String updateform(@PathVariable Long id, Model model, HttpSession httpSession) {
		if (isLoginUser(httpSession)) {
			return "redirect:/users/loginForm";
		}
		
		if(!getUserFromSession(httpSession).matchId(id)) {
			throw new IllegalStateException("자신의 정보만 스정 할 수 있습니다");
		}
		
		model.addAttribute(userRepository.findOne(id));
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String updateUser(@PathVariable Long id, User newUserInfo, HttpSession httpSession) {
		if (isLoginUser(httpSession)) {
			return "redirect:/users/loginForm";
		}
		
		if(!getUserFromSession(httpSession).matchId(id)) {
			throw new IllegalStateException("자신의 정보만 스정 할 수 있습니다");
		}
		
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
}