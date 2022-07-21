package com.kostApp.kostApp.controllers;


import com.kostApp.kostApp.DTO.UserDTO;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * controller for users
 */
@Controller
@RequestMapping("/kostApp/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @param model - model for add attribute to view
     * @param id - user id
     * @return user page
     */
    @GetMapping("/userPage/{id}")
    private String userPage(Model model, @PathVariable("id") int id){

//        take user nik from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
//        take user from database
        User user = userService.showUserById(id);

//        check if this user can watch data for user from database
        if(user.getNik().equals(currentUserNik)) {
            model.addAttribute("user", convertToUserDTO(user));
        }else {
            throw new RuntimeException("you can`t see data another user");
        }

        return "/user/userPage";
    }

    /**
     *
     * @param model - model for add attribute to view
     * @param id - user id
     * @return edit page for user
     */
    @GetMapping("/editUser/{id}")
    private String editUserPage(Model model, @PathVariable("id") int id){

//        take user nik from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
//        take user from database
        User user = userService.showUserById(id);

//        check if this user can edit data for user from database
        if(user.getNik().equals(currentUserNik)) {
            model.addAttribute("user", convertToUserDTO(user));
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

        return "user/editUserPage";
    }

    /**
     * post method for edit user
     *
     * @param userDTO - user for edit
     * @param bindingResult - bindingResult for catch errors
     * @param id - user id
     * @return user page
     */
    @PatchMapping("/editUser/{id}")
    private String editUser(@ModelAttribute("user")@Valid UserDTO userDTO,
                            BindingResult bindingResult,
                            @PathVariable("id")int id){

//        check if user field has error
        if(bindingResult.hasErrors()){
            return "/editUser/{id}";
        }

//        take user nik from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
//        take user from database
        User user = userService.showUserById(id);

//        check if this user can edit data for user from database
        if(user.getNik().equals(currentUserNik)) {
            userService.update(convertToUser(userDTO), id);
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

//        reload user nikname and password in SecurityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getNik(), userDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/kostApp/user/userPage/{id}";
    }

    /**
     * delete method for delete user
     *
     * @param id - user id
     * @return login page
     */
    @DeleteMapping("/delete/{id}")
    private String delete(@PathVariable("id")int id){

//        take user id from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
//        take user from database
        User user = userService.showUserById(id);

//        check if this user can delete user from database
        if(user.getNik().equals(currentUserNik)) {
            userService.delete(id);
        }else {
            throw new RuntimeException("you can`t edit data another user");
        }

        return "redirect:/authentication/login";

    }

    /**
     * DTO convert method
     *
     * @param userDTO
     * @return user converted from DTO
     */
    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * DTO convert method
     *
     * @param user
     * @return userDTO converted from user
     */
    private UserDTO convertToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

}
