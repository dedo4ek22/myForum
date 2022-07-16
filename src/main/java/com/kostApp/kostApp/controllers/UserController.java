package com.kostApp.kostApp.controllers;


import com.kostApp.kostApp.DTO.UserDTO;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/kostApp/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/userPage/{id}")
    private String userPage(Model model, @PathVariable("id") int id){

        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserById(id);

        if(user.getNik().equals(currentUserNik)) {
            model.addAttribute("user", convertToUserDTO(user));
        }else {
            throw new RuntimeException("you can`t see data another user");
        }

        return "/user/userPage";
    }

    @GetMapping("/editUser/{id}")
    private String editUserPage(Model model, @PathVariable("id") int id){

        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserById(id);

        if(user.getNik().equals(currentUserNik)) {
            model.addAttribute("user", convertToUserDTO(user));
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

        return "user/editUserPage";
    }

    @PatchMapping("/editUser/{id}")
    private String editUser(@ModelAttribute("user")@Valid UserDTO userDTO,
                            BindingResult bindingResult,
                            @PathVariable("id")int id){

        if(bindingResult.hasErrors()){
            return "/editUser/{id}";
        }

        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserById(id);

        if(user.getNik().equals(currentUserNik)) {
            userService.update(convertToUser(userDTO), id);
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

//  for reload user nikname and password in SecurityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getNik(), userDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/kostApp/user/userPage/{id}";
    }

    @DeleteMapping("/delete/{id}")
    private String delete(@PathVariable("id")int id){

        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserById(id);

        if(user.getNik().equals(currentUserNik)) {
            userService.delete(id);
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

        return "redirect:/authentication/login";

    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

}
