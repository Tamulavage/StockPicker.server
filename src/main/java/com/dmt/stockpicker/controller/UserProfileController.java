package com.dmt.stockpicker.controller;


import com.dmt.stockpicker.model.UserProfile;
import com.dmt.stockpicker.services.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/userprofile")
public class UserProfileController { 

    
    private UserProfileService userProfileService;    

    @Autowired
    public UserProfileController(UserProfileService service) {userProfileService = service;}

    @GetMapping("/{name}")
    public ResponseEntity<UserProfile> getUserProfileByUsername(@PathVariable String name){
        return new ResponseEntity<>(userProfileService.getUserProfileByUsername(name), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserProfile> newUserProfile(@RequestBody UserProfile userProfile){
        return new ResponseEntity<>(userProfileService.addUserProfile(userProfile), HttpStatus.CREATED);
    }

}