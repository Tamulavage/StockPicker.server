package com.dmt.stockpicker.controller;

import com.dmt.stockpicker.model.UserProfile;
import com.dmt.stockpicker.repository.UserProfileRepository;
import com.dmt.stockpicker.services.UserProfileService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;



public class UserProfileControllerTest {

    @MockBean
    private UserProfileRepository userProfileRepository;

    private UserProfileController controller;
    private UserProfileService service;
    private UserProfile userProfile;

    @Before
    public void setupTest(){
        // this.controller = new UserProfileController(service);
        // this.userProfile = new UserProfile();
        // this.userProfile.setUserId(1);
        // this.userProfile.setUserName("tester");
    }

    @Test
    public void addNewUserTest(){
        // given

        // when

        // Then
    }

    @Test
    public void findUserbyUsernameTest(){
        // given

        // when
    
        // Then
    }
}

