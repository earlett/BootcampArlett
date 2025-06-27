package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // GET /profile - Get current user's profile
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public Profile getProfile(Principal principal) {
        try {
            // Get the currently logged-in user's username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // Get the profile by user id
            Profile profile = profileDao.getByUserId(user.getId());

            if (profile == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }

            return profile;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // PUT /profile - Update current user's profile
    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public void updateProfile(@RequestBody Profile profile, Principal principal) {
        try {
            // Get the currently logged-in user's username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // Make sure the profile belongs to the current user
            profile.setUserId(user.getId());

            // Check if profile exists
            Profile existingProfile = profileDao.getByUserId(user.getId());
            if (existingProfile == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }

            // Update the profile
            profileDao.update(profile);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}