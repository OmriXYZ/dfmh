package com.omrixyz.dfmh.controllers;

import com.omrixyz.dfmh.model.Donation;
import com.omrixyz.dfmh.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonationRestController {

    @Autowired
    private DonationService donationService;

    @RequestMapping(path="/donations", method = RequestMethod.GET)
    public List<Donation> getAllDonations(){
        return donationService.findAll();
    }

    @RequestMapping(value = "/donation/{id}", method = RequestMethod.GET)
    public Donation getDonationById(@PathVariable("id") int id){
        return donationService.findById(id);
    }
}
