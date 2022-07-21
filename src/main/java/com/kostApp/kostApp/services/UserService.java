package com.kostApp.kostApp.services;

import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * service class for user
 */
@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * method for take user from database by id
     *
     * @param id - user id
     * @return - current user
     */
    public User showUserById(int id){

//        take optional user by in from database
        Optional<User> optionalUser = userRepository.findById(id);

//        if no such user throw exception
        if (optionalUser.isEmpty()){
            throw new RuntimeException("optionalUser with this id not found");
        }

//        get user from optional
        User user = optionalUser.get();

        return user;
    }

    /**
     * method for take user from database by nikname
     *
     * @param nik - user nikname
     * @return - current user
     */
    public User showUserByNik(String nik){

//        take optional user from database by nikname
        Optional<User> optionalUser = userRepository.findByNik(nik);

//        if no such user throw exception
        if (optionalUser.isEmpty()){
            throw new RuntimeException("optionalUser with this id not found");
        }

//        take user from optional
        User user = optionalUser.get();

        return user;
    }

    /**
     * method for update user data
     *
     * @param user - user with new data
     * @param id - user id
     */
    public void update(User user, int id){

        user.setId(id);
//        set role for user by default
        user.setRole("ROLE_USER");
//        encode new user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    /**
     * method for delete user by id
     *
     * @param id - user id
     */
    public void delete(int id){
        userRepository.deleteById(id);
    }

    /**
     * method for take all users from database
     *
     * @return - list of users
     */
    public List<User> userList(){
       return userRepository.findAll();
    }

}
