package com.kostApp.kostApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controller for admin
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     *
     * @return admin page
     */
    @GetMapping()
    private String adminPage(){

        return "/admin/adminPage";
    }

}
