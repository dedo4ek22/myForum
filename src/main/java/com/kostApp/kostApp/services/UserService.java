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
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User showUserById(int id){
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()){
            throw new RuntimeException("optionalUser with this id not found");
        }

        User user = optionalUser.get();

        return user;
    }

    public User showUserByNik(String nik){
        Optional<User> optionalUser = userRepository.findByNik(nik);

        if (optionalUser.isEmpty()){
            throw new RuntimeException("optionalUser with this id not found");
        }

        User user = optionalUser.get();

        return user;
    }

    public void update(User user, int id){
        user.setId(id);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }

}
