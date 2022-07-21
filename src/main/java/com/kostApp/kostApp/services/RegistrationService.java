package com.kostApp.kostApp.services;

import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * service class for registration
 */
@Service
public class RegistrationService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * method for save new user in database
     *
     * @param user - storage user for save
     */
    public void save(User user){

//        encode password in bcrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        set role for user (by default all new user with ROLE_USER)
        user.setRole("ROLE_USER");

        userRepository.save(user);

    }
}
