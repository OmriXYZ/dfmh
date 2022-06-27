package com.omrixyz.dfmh.controllers;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.model.PrivateMessage;
import com.omrixyz.dfmh.model.User;
import com.omrixyz.dfmh.service.AmazonClient;
import com.omrixyz.dfmh.service.DonationService;
import com.omrixyz.dfmh.service.UserService;
import com.omrixyz.dfmh.storage.StorageException;
import com.omrixyz.dfmh.storage.StorageFileNotImgExtension;
import com.omrixyz.dfmh.storage.StorageFileSizeLimit;
import com.omrixyz.dfmh.storage.StorageService;



@Controller
@RequestMapping("/donations")
public class DonationController {

	@Autowired
	private DonationService donationService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AmazonClient amazonClient;
	
	private static List<String> departments;
	static {
		departments = new ArrayList<>();
		departments.add("Electronics");
		departments.add("Furniture");
		departments.add("Kitchen");
		departments.add("Office");
	}
	
	public DonationController(DonationService theDonationService) {
		donationService = theDonationService;
	}

	// add mapping for "/list"

	@GetMapping("/list")
	public String listDonations(Model theModel) {
		// get donations from db
		List<Donation> theDonations = donationService.findAll();
		
		// add to the spring model
		theModel.addAttribute("donations", theDonations);
		theModel.addAttribute("departments", departments);
		
		PrivateMessage pm = new PrivateMessage();
		theModel.addAttribute("privateMessage", pm);
		
		return "donations/donation-table-forrestore";
	}

	@GetMapping("/form")
	public String showFormForAdd(Model theModel) {
		// create model attribute to bind form data
		Donation theDonation = new Donation();
		theDonation.setHEmail(false);
		theDonation.setHPhone(false);
		theModel.addAttribute("donation", theDonation);
		theModel.addAttribute("departments", departments);
		return "donations/donation-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("donationId") int theId,
									Model theModel) {

		// get the donation from the service
		Donation theDonation = donationService.findById(theId);
		
		// set donation as a model attribute to pre-populate the form
		theModel.addAttribute("donation", theDonation);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("file1", "test");
		
		// send over to our form
		return "donations/donation-form";
	}
	
	@PostMapping("/save")
//	@ResponseBody
	public String saveDonation(@ModelAttribute("donation") Donation theDonation,
							   @RequestParam("file1") MultipartFile file,
							   @RequestParam("file2") MultipartFile file2,
							   RedirectAttributes redirectAttributes,
							   @RequestParam(value = "checkphone", required = false) boolean hidePhone, 
							   @RequestParam(value = "checkemail", required = false) boolean hideEmail) throws IOException {

		Instant instant = Instant.now();
		Instant userLastDonate = userService.getLastTimeUploadDonation(theDonation.getUser());
		long seconds = ChronoUnit.SECONDS.between(userLastDonate, instant);
		if (seconds < 25) {
			redirectAttributes.addFlashAttribute("msglastdonation", "Must wait " + (25 - seconds) + " seconds more to making a donation");
			redirectAttributes.addFlashAttribute("condcond", true);
			return "redirect:/donations/form";
		}
		
		if (!patternCheck("(05)[0-9]{8}$", theDonation.getPhone())) {
			redirectAttributes.addFlashAttribute("msglastdonation", "A format that does not match to Phone number");
			redirectAttributes.addFlashAttribute("condcond", true);
			return "redirect:/donations/form";
		}
		
		if (theDonation.getUser().isBlank() || theDonation.getName().isBlank() || file.isEmpty() || theDonation.getDepartment().isBlank()) {
			redirectAttributes.addFlashAttribute("msglastdonation", "It is not possible to leave an empty field");
			redirectAttributes.addFlashAttribute("condcond", true);
			return "redirect:/donations/form";
		}
		
		System.err.println(seconds);
		userService.updateLastTimeUploadDonation(theDonation.getUser(), instant);
		
		theDonation.setHPhone(hidePhone);
		theDonation.setHEmail(hideEmail);
		donationService.save(theDonation);

		
//		try {
//			if (file.getOriginalFilename().isEmpty()  && !file2.getOriginalFilename().isEmpty()) {
//				storageService.store(file2, theDonation.getId(), "1.");
//			} else {
//			storageService.store(file, theDonation.getId(), "1.");
//			storageService.store(file2, theDonation.getId(), "2.");
//			}
//		} catch (StorageFileSizeLimit e) {
//			redirectAttributes.addFlashAttribute("msgfilelimit",
//					e.getMessage());
//			delete(theDonation.getId());
//			return "redirect:/donations/form";
//		} catch (StorageException e) {
//			System.err.println("From donation controller storage. ");
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//		} catch (StorageFileNotImgExtension e) {
//			System.err.println(e.getMessage());
//			redirectAttributes.addFlashAttribute("msgfilelimit", e.getMessage());
//			delete(theDonation.getId());
//
//			return "redirect:/donations/form"d;
//		}
		

		
		try {
			amazonClient.uploadFile(file, theDonation.getId(), 0);
			amazonClient.uploadFile(file2, theDonation.getId(), 1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			redirectAttributes.addFlashAttribute("msgfilelimit", e.getMessage());
			delete(theDonation.getId());
			return "redirect:/donations/form";
		}
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/donations/list";
	}
	
	private boolean patternCheck(String pattern, String strcheck) {
		Pattern pat = Pattern.compile(pattern);
		Matcher match = pat.matcher(strcheck);
		return match.find();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("donationId") int theId) {
		
		// delete the donation
		donationService.deleteById(theId);
		storageService.delete(String.valueOf(theId));
		amazonClient.deleteFileFromS3(theId);

		return "redirect:/donations/list";
	}
	
	@GetMapping("/deletebyuser")
	public String deleteByUser(@RequestParam("donationId") int theId) {
		Donation tempDonation = donationService.findById(theId);
		String emailOfSelectedDonation = tempDonation.getUser();
		if (userService.isValidUser(emailOfSelectedDonation)) {
			donationService.deleteById(theId);
			storageService.delete(String.valueOf(theId));
			amazonClient.deleteFileFromS3(theId);
		} else
			return "index";
		

		
		return "redirect:/donations/searchbyuser?userEmail=" + emailOfSelectedDonation;
		
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("userEmail") String useremail,
						 Model theModel) {
		
		List<Donation> theDonations = donationService.searchByEmail(useremail);
		
		
		theModel.addAttribute("donations", theDonations);
		
		// send to /donations/list
		return "donations/donation-table";
		
	}
	
	@GetMapping("/searchbyuser")
	public String searchByUser(@RequestParam("userEmail") String useremail,
						 Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if (!useremail.equals(currentPrincipalName)) {
			return "redirect:/donations/list";
		}
		
		List<Donation> theDonations = donationService.searchByEmail(useremail);
		
		
		theModel.addAttribute("donations", theDonations);
		
		// send to /donations/list
		return "donations/donation-table-user";
	}
	
	@GetMapping("/searchbydepart")
	public String searchByDepartment(@RequestParam("depart") String departmentName,
						 Model theModel) {
		
		List<Donation> theDonations = donationService.searchByDepartment(departmentName);
		theModel.addAttribute("donations", theDonations);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("depart_selected", departmentName);
		PrivateMessage pm = new PrivateMessage();
		theModel.addAttribute("privateMessage", pm);
		
		return "donations/donation-table";
	}

	
//	@GetMapping("/images")
//	public String searchByUser(@RequestParam("donationId") int theId, Model theModel) {
//
//		try {
//			theModel.addAttribute("image1", storageService.loadImg(theId + "", 0));
//			theModel.addAttribute("image2", storageService.loadImg(theId + "", 1));
//		} catch (NoSuchFileException e) {
//			System.err.println(e.getMessage());
//		}
//		
//		
//		// send to /donations/list
//		return "donations/donation-image";
//		
//	}
	
	@GetMapping("/images")
	public String searchByUser(@RequestParam("donationId") int theId, Model theModel) throws IOException {

		
		ArrayList<String> images = new ArrayList<>();
		images = amazonClient.loadFile(theId);
		for (int i = 0; i < images.size(); i++) {
			theModel.addAttribute("imagebyte" + i, images.get(i));
		}
		
		return "donations/donation-image";
		
	}
	
	@RequestMapping("/check")     
	@ResponseBody
	public boolean check(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Instant instant = Instant.now();
		Instant userLastDonate = userService.getLastTimeUploadDonation(currentPrincipalName);
		long seconds = ChronoUnit.SECONDS.between(userLastDonate, instant);
		System.out.println(seconds);
		if (seconds < 25) {
			System.err.println(seconds);
			model.addAttribute("donationvalid", true);
	        return true;
		} else {
	        model.addAttribute("donationvalid", false);
	        System.err.println("dont show");
	        return false;
	    }
		
		
		

	}

	@GetMapping("/listt")
	public String testlistt() {
		return "donations/donations-table-test";
	}
//
//	@RequestMapping(path="/donationss", method = RequestMethod.GET)
//	public List<Donation> getAllDonations(){
//		return donationService.findAll();
//	}
//
//	@RequestMapping(value = "/donationss/{id}", method = RequestMethod.GET)
//	public Donation getDonationById(@PathVariable("id") int id){
//		return donationService.findById(id);
//	}



	
}







