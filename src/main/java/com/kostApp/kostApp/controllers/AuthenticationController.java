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

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/login")
    private String login(){
        return "/authentication/login";
    }

    @GetMapping("/registration")
    private String registration(@ModelAttribute("user") User user){
        return "authentication/registration";
    }

    @PostMapping("/registration")
    private String registrationProcess(@ModelAttribute("user") @Valid User user,
                                       BindingResult result){

        if(result.hasErrors()){
            return "authentication/registration";
        }

        registrationService.save(user);

        return "redirect:/authentication/login";
    }
}
