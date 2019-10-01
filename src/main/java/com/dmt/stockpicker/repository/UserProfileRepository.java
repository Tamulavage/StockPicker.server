package com.dmt.stockpicker.repository;

import com.dmt.stockpicker.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	UserProfile getUserProfileByUserName(String username);
}
