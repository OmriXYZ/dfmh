package com.omrixyz.dfmh.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.model.User;
import com.omrixyz.dfmh.service.AmazonClient;
import com.omrixyz.dfmh.service.DonationService;
import com.omrixyz.dfmh.service.UserManagmentService;
import com.omrixyz.dfmh.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersManagemenetController {

	private UserManagmentService userManageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DonationService donationService;
	
	@Autowired
	private AmazonClient amazonClient;
	
	private static List<String> roles;
	static {
		roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_MODERATOR");
		roles.add("ROLE_USER");
	}
	
	public UsersManagemenetController(UserManagmentService theUserManageService) {
		userManageService = theUserManageService;
	}

	@GetMapping("/list")
	public String listDonations(Model theModel) {
				
		// get users from db
		List<User> theUsers = userManageService.findAll();
		
		List<String> theRoles = new ArrayList<String>();
		UserDetails deta;
		for (int i = 0; i < theUsers.size(); i++) {
			deta = userService.loadUserByUsername(theUsers.get(i).getEmail());
			theRoles.add(deta.getAuthorities().toString());
		}
		theModel.addAttribute("roler", theRoles);
		
		// add to the spring model
		theModel.addAttribute("users", theUsers);
		theModel.addAttribute("roles", roles);

		return "usermanagment/users";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("userId") int theId) {
		List<Donation> donations = donationService.searchByEmail(userManageService.findEmailById(theId));
		for (Donation donation : donations) {
			amazonClient.deleteFileFromS3(donation.getId());
		}
		donationService.deleteAllByEmail(userManageService.findEmailById(theId));
		userManageService.deleteById(theId);
		
		return "redirect:/users/list";
		
	}
	
	@GetMapping("/changeRole")
	public String changeRole(@RequestParam("userId") int theId) {
		
		userService.setRole(33, "ROLE_USER");
		return "redirect:/users/list";
		
	}
	
	@GetMapping("/showRole")
	public String showRole(@RequestParam("userEmail") String useremail, Model theModel) {

		UserDetails deta = userService.loadUserByUsername(useremail);
		theModel.addAttribute("roler", deta.getAuthorities().toString());
		
		return "redirect:/users/list";
	}
	
}
