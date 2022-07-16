package com.kostApp.kostApp.controllers;

import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kostApp/Discussion/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/save/{id}")
    private String SaveMessage(@PathVariable("id") int id,
                               @ModelAttribute("discName")String discName,
                               @ModelAttribute("discId")int discId,
                               @ModelAttribute("message")Message message){

        String mes = message.getMessage();
        String nameOfTable = discName.replace(" ","_") + "_messages";

        messageService.saveMessage(mes, nameOfTable, discId);

        return "redirect:/kostApp/discussion/discussionPage/{id}";
    }

    @GetMapping("/showMessage/{id}")
    private String showMessage(){

        return "redirect:/kostApp/discussion/discussionPage/{id}";
    }
}
