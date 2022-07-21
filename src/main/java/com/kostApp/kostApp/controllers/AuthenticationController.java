package com.kostApp.kostApp.controllers;


import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * controller for authentication
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private RegistrationService registrationService;

    /**
     *
     * @return login page
     */
    @GetMapping("/login")
    private String login(){
        return "/authentication/login";
    }

    /**
     *
     * @param user - give new user in view
     * @return registration page
     */
    @GetMapping("/registration")
    private String registration(@ModelAttribute("user") User user){
        return "authentication/registration";
    }

    /**
     * controller method post for register new user
     *
     * @param user - user from view
     * @param result - bindingresult for catch error in user fields
     * @return login page
     */
    @PostMapping("/registration")
    private String registrationProcess(@ModelAttribute("user") @Valid User user,
                                       BindingResult result){

//        check for error in user field
        if(result.hasErrors()){
            return "authentication/registration";
        }

//        save user in database
        registrationService.save(user);

        return "redirect:/authentication/login";
    }
}
