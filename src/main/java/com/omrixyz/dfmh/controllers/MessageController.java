package com.omrixyz.dfmh.controllers;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.model.PrivateMessage;
import com.omrixyz.dfmh.service.AmazonClient;
import com.omrixyz.dfmh.service.DonationService;
import com.omrixyz.dfmh.service.MessageService;
import com.omrixyz.dfmh.service.UserService;
import com.omrixyz.dfmh.storage.StorageException;
import com.omrixyz.dfmh.storage.StorageFileNotImgExtension;
import com.omrixyz.dfmh.storage.StorageFileSizeLimit;
import com.omrixyz.dfmh.storage.StorageService;

@Controller
@RequestMapping("/msg")
public class MessageController {

	private MessageService messageService;
	
	public MessageController(MessageService theMessageService) {
		messageService = theMessageService;
	}

	@GetMapping("/list")
	public String listMessages(Model theModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		List<PrivateMessage> messages = messageService.findAllByUser(currentPrincipalName);
		theModel.addAttribute("msgs", messages);
		
		PrivateMessage pm = new PrivateMessage();
		theModel.addAttribute("privateMessage", pm);
		return "msg/msg-list";
	}

	@GetMapping("/qwe")
	public String showMsgForm(Model theModel) {
		System.err.println("inside form");
		PrivateMessage pm = new PrivateMessage();
		theModel.addAttribute("privateMessage", pm);
		return "redirect:/donations/list";
	}

	@PostMapping("/send")
	public String save(@ModelAttribute("privateMessage") PrivateMessage pm,
			@RequestParam(value = "donationName", required = false) String donName, RedirectAttributes redirectAttributes) {
		if (pm.getFromUser().equals(pm.getToUser())) {
			redirectAttributes.addFlashAttribute("errmsg", true);
			return "redirect:/donations/list";
		}
		if (donName != null) {
			pm.addDonationNameForMsg(donName);
		}
		messageService.save(pm);
		return "redirect:/donations/list";
	}
	
	@PostMapping("/sendback")
	public String sendBack(@ModelAttribute("privateMessage") PrivateMessage pm,
			@RequestParam(value = "donationName", required = false) String donName) {
		if (donName != null) {
			pm.addDonationNameForMsg(donName);
		}
		messageService.save(pm);
		return "redirect:/msg/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("msgId") int msgId) {
		messageService.deleteById(msgId);
		return "redirect:/msg/list";
	}
	
}