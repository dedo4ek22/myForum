package com.kostApp.kostApp.controllers;

import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    /**
     * POST method for edit message
     *
     * @param id - id of discussion
     * @param discName - name of discussion
     * @param messageId - message id
     * @param message - message
     * @param optionalPage - number of page
     * @param optionalSize - size of page
     * @return discussion page
     */
    @PostMapping("/edit/{id}")
    private String editMessagePage(@PathVariable("id") int id,
                                   @ModelAttribute("discName")String discName,
                                   @ModelAttribute("messageId") int messageId,
                                   @ModelAttribute("message")Message message,
                                   @RequestParam("page") Optional<Integer> optionalPage,
                                   @RequestParam("size")Optional<Integer> optionalSize){

//        set size and page from requestParam end sent it tu URL for return page where massage was edit
        int page = optionalPage.orElse(1);
        int size = optionalSize.orElse(15);
//        generate name of table
        String nameOfTable = discName.replace(" ","_") + "_messages";

        messageService.editMessage(messageId, message, nameOfTable);

        return "redirect:/kostApp/discussion/discussionPage/{id}?size=" + size +"&page=" + page;
    }
}
