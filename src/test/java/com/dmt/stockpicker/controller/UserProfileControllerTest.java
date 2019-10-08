package com.dmt.stockpicker.controller;

import com.dmt.stockpicker.model.UserProfile;
import com.dmt.stockpicker.repository.UserProfileRepository;
import com.dmt.stockpicker.services.UserProfileService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {

    // @MockBean
    // private UserProfileRepository userProfileRepository;

    private UserProfileController controller;

    @Mock
    private UserProfileService userProfileService;
    private UserProfile userProfile;

    @Before
    public void setupTest(){
        this.controller = new UserProfileController(userProfileService);
        this.userProfile = new UserProfile();
        this.userProfile.setUserId(1);
        this.userProfile.setUserName("tester");
    }

    @Test
    public void getUserProfileByUsernameTest(){
        // given
        HttpStatus expectedStatus = HttpStatus.OK;
        BDDMockito
            .given(userProfileService.getUserProfileByUsername(userProfile.getUserName()))
            .willReturn(userProfile);

        // when
        ResponseEntity<UserProfile> actual = controller.getUserProfileByUsername(userProfile.getUserName());
        HttpStatus actualStatus = actual.getStatusCode();
        UserProfile actualUserProfile = actual.getBody();
    
        // Then
        Assert.assertEquals(expectedStatus, actualStatus);
        Assert.assertEquals(userProfile, actualUserProfile);
    }
}

