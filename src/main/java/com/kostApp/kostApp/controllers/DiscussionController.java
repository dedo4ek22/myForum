package com.kostApp.kostApp.controllers;

import com.kostApp.kostApp.DTO.DiscussionDTO;
import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.DiscussionService;
import com.kostApp.kostApp.services.MessageService;
import com.kostApp.kostApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * controller for discussion
 */
@Controller
@RequestMapping("/kostApp/discussion")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     *
     * @param model - model for add attribute to view
     * @return create discussion page
     */
    @GetMapping("/create")
    private String createDiscussionPage(Model model){

        model.addAttribute("discussion", convertToDiscussionDTO(new Discussion()));

        return "/discussion/createPage";
    }

    /**
     * post method for save discussion
     *
     * @param discussionDTO - discussion for save
     * @return welcome page
     */
    @PostMapping("/create")
    private String createDiscussionProcess(@ModelAttribute("discussion")DiscussionDTO discussionDTO){

        discussionService.save(convertToDiscussion(discussionDTO));

        return "redirect:/kostApp/welcome";
    }

    /**
     * discussion page with messages
     *
     * @param id - discussion id
     * @param model - model for add attribute to view
     * @return page of current discussion
     */
    @GetMapping("/discussionPage/{id}")
    private String discussionPage(@PathVariable("id")int id, Model model){

//        take discussion from database
        Discussion discussion = discussionService.getDiscussionById(id);
//        take user nik from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
//        take user by nik from database
        User user = userService.showUserByNik(currentUserNik);

//        generate name for message table
        String nameOfTable = discussion.getName().replace(" ","_") + "_messages";

        model.addAttribute("userService", userService);
        model.addAttribute("users", userService.userList());
        model.addAttribute("message", new Message());
        model.addAttribute("discussion", discussion);
        model.addAttribute("user", user);
        model.addAttribute("messages", messageService.getMessageList(nameOfTable));

        return "/discussion/discussionPage";
    }

    /**
     * delete method for discussion
     *
     * @param id - discussion id
     * @return welcome page
     */
    @DeleteMapping("/delete/{id}")
    private String deleteDiscussion(@PathVariable("id")int id){

        discussionService.deleteById(id);

        return "redirect:/kostApp/welcome";
    }

    /**
     * DTO method for discussion
     *
     * @param discussionDTO
     * @return new discussion converted from DTO
     */
    private Discussion convertToDiscussion (DiscussionDTO discussionDTO){
        return modelMapper.map(discussionDTO, Discussion.class);
    }

    /**
     * DTO method for discussion
     *
     * @param discussion
     * @return new discussionDTO converted from discussion
     */
    private DiscussionDTO convertToDiscussionDTO (Discussion discussion){
        return modelMapper.map(discussion, DiscussionDTO.class);
    }
}
