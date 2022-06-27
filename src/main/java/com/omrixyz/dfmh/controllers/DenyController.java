package com.omrixyz.dfmh.controllers;


import com.omrixyz.dfmh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class DenyController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/validation")
    public String checkingUser(@RequestParam("user") String user, Model theModel) {
        if (!userService.isExistUserByEmail(user) || userService.checkVerification(user)) //User not exist or user is valid
            return "/login";
        theModel.addAttribute("user", user);
        return "/valid";
    }

    @GetMapping(path = "/error")
    public String error(Model theModel) {
        theModel.addAttribute("error", true);
        return "/login";
    }

    @PostMapping("/postvalidation")
    public String checkCodeValid(@RequestParam("token") String token, @RequestParam("email") String email) {
        userService.checkToken(token, email);
        return "/login";

    }

}
