package com.omrixyz.dfmh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omrixyz.dfmh.service.AmazonClient;

@Controller
@RequestMapping("/sss")
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
//        return this.amazonClient.uploadFile(file);
//    }
}
