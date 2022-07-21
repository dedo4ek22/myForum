package com.kostApp.kostApp.services;

import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * service class for user detail
 */
@Service
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * method for take user from database by nikname
     *
     * @param username - storage nikname of user
     * @return - user detail object
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        take user from database
        Optional<User> user = userRepository.findByNik(username);

//        if no such user with this nik throw exception
        if (user.isEmpty()){
            throw new UsernameNotFoundException("username or password aren`t right");
        }

        return new com.kostApp.kostApp.security.UserDetails(user.get());
    }
}
