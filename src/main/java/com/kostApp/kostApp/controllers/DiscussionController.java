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


    @GetMapping("/create")
    private String createDiscussionPage(Model model){

        model.addAttribute("discussion", convertToDiscussionDTO(new Discussion()));

        return "/discussion/createPage";
    }

    @PostMapping("/create")
    private String createDiscussionProcess(@ModelAttribute("discussion")DiscussionDTO discussionDTO){

        discussionService.save(convertToDiscussion(discussionDTO));

        return "redirect:/kostApp/welcome";
    }

    @GetMapping("/discussionPage/{id}")
    private String discussionPage(@PathVariable("id")int id, Model model){

        Discussion discussion = discussionService.getDiscussionById(id);
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserByNik(currentUserNik);

        String nameOfDiscussion = discussion.getName();
        String nameOfTable = nameOfDiscussion + "_messages";

        model.addAttribute("message", new Message());
        model.addAttribute("discussion", discussion);
        model.addAttribute("user", user);
        model.addAttribute("messages", messageService.messageList(nameOfTable));

        return "/discussion/discussionPage";
    }

    @DeleteMapping("/delete/{id}")
    private String deleteDiscussion(@PathVariable("id")int id){

        discussionService.deleteById(id);

        return "redirect:/kostApp/welcome";
    }

    private Discussion convertToDiscussion (DiscussionDTO discussionDTO){
        return modelMapper.map(discussionDTO, Discussion.class);
    }

    private DiscussionDTO convertToDiscussionDTO (Discussion discussion){
        return modelMapper.map(discussion, DiscussionDTO.class);
    }
}
