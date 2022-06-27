package com.omrixyz.dfmh.controllers;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.omrixyz.dfmh.controllers.dto.UserRegistrationDto;
import com.omrixyz.dfmh.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

//	@ModelAttribute("user")
//    public User userRegistrationDto() {
//        return new UserRegistrationDto(new User());
//    }

//	@GetMapping("/user")
//	public String showFormForAdd(Model theModel) {
//		// create model attribute to bind form data
//		User user = new User();
//		theModel.addAttribute("user", user);
//		return "registration";
//	}
	
	@GetMapping
	public String showRegistrationForm(Model theModel) {
		User user = new User();
		theModel.addAttribute("user", user);
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			//errors processing
		}

		UserRegistrationDto registrationDto = new UserRegistrationDto(user);
		if(userService.existUser(registrationDto)) {
			return "redirect:/registration?erremail";
		}

		String pat = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
		if (!patternCheck(pat, registrationDto.getUsername())) {
			redirectAttributes.addFlashAttribute("reg_msg", "A format that does not match to Email");
			redirectAttributes.addFlashAttribute("regmsg", true);
			System.err.println("pattern error");
			return "redirect:/registration";
		}

		try {
			System.out.println(user.getEmail());
			userService.save(registrationDto);
		} catch (UnsupportedEncodingException | MessagingException e) {

			System.err.println(e.getMessage());
		}
//		return "redirect:/registration?success";
		return "redirect:/auth/validation?user=" + user.getEmail();

	}
	
	@GetMapping("/showRole")
	public String showRole(@RequestParam("userEmail") String useremail, Model theModel) {

		UserDetails deta = userService.loadUserByUsername(useremail);
		System.err.println(deta.getAuthorities());
		theModel.addAttribute(deta.getAuthorities());

		return "redirect:/users/list";
	}
	
	private boolean patternCheck(String pattern, String strcheck) {
		Pattern pat = Pattern.compile(pattern);
		Matcher match = pat.matcher(strcheck);
		return match.find();
	}

}












