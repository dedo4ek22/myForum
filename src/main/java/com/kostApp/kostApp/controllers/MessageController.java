package com.kostApp.kostApp.controllers;

import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * controller for message
 */
@Controller
@RequestMapping("/kostApp/Discussion/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * post method for save message
     *
     * @param id - id of discussion
     * @param discName - name of discussion
     * @param message - message object
     * @return discussion page
     */
    @PostMapping("/save/{id}")
    private String SaveMessage(@PathVariable("id") int id,
                               @ModelAttribute("discName")String discName,
                               @ModelAttribute("message")Message message){

//        take message
        String mes = message.getMessage();
//        generate name of table
        String nameOfTable = discName.replace(" ","_") + "_messages";

//        save message in database
        messageService.saveMessage(mes, nameOfTable, id);

        return "redirect:/kostApp/discussion/discussionPage/{id}";
    }

    /**
     * delete method for delete message
     *
     * @param id - discussion id
     * @param discName - discussion name
     * @param messageId - message id
     * @return discussion page
     */
    @DeleteMapping("/delete/{id}")
    private String deleteMessage(@PathVariable("id") int id,
                                 @ModelAttribute("discName")String discName,
                                 @ModelAttribute("messageId") int messageId){

//        generate name of table
        String nameOfTable = discName.replace(" ","_") + "_messages";

//        delete message from database
        messageService.deleteMessageForId(messageId, nameOfTable);

        return "redirect:/kostApp/discussion/discussionPage/{id}";
    }
}
