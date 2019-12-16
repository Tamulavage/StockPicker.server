package com.dmt.stockpicker.services;

import com.dmt.stockpicker.model.UserProfile;
import com.dmt.stockpicker.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile addUserProfile(UserProfile userProfile){
        return userProfileRepository.save(userProfile);
    }

    public UserProfile getUserProfileByUsername(String username){
        return userProfileRepository.getUserProfileByUserName(username);
    }

}