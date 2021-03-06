package com.kostApp.kostApp.controllers;

import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.DiscussionService;
import com.kostApp.kostApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * controller of main page
 */
@Controller
@RequestMapping("/kostApp")
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private DiscussionService discussionService;

    /**
     *
     * @param model - model for add attribute to view
     * @return welcome page
     */
    @GetMapping("/welcome")
    private String kostAppWelcomePage(Model model) {

//        take user nik from session and find user by it
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserByNik(currentUserNik);

        model.addAttribute("userService", userService);
        model.addAttribute("user", user);
        model.addAttribute("discussions", discussionService.getAllDiscussions());
        return "/main/kostAppWelcomePage";
    }




}
