package com.kostApp.kostApp.services;

import com.kostApp.kostApp.DAO.MessageDAO;
import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

/**
 * service class for discussion
 */
@Service
public class MessageService {

    @Autowired
    UserService userService;

    @Autowired
    private MessageDAO messageDAO;

    /**
     * method for save message
     *
     * @param message - storage text of message and give it to messageDAO
     * @param nameOfTable - storage name of table and give it to messageDAO
     * @param discussionId - storage discussion id and give it to messageDAO
     */
    public void saveMessage(String message, String nameOfTable, int discussionId){

//        add line break in long message
       message = addLineBreak(message);

       messageDAO.saveMessage(message, nameOfTable, discussionId);

    }

    /**
     * method for take all messages from database
     *
     * @param nameOfTable - storage name of table and give it to messageDAO
     * @return - list of message
     */
    public Page<Message> getMessageList(String nameOfTable, Pageable pageable){

       List<Message> messages = messageDAO.getMessageList(nameOfTable);

       int pageSize = pageable.getPageSize();
       int currentPage = pageable.getPageNumber();
       int startItem = currentPage * pageSize;

       List<Message> list;

       if(messages.size() < startItem){
           list = Collections.emptyList();
       } else {
           int toIndex = Math.min(startItem + pageSize, messages.size());
           list = messages.subList(startItem, toIndex);
       }

       Page<Message> messagePage =
               new PageImpl<Message>(list, PageRequest.of(currentPage, pageSize), messages.size());

       return messagePage;
    }

    /**
     * method for delete message from database
     *
     * @param id - storage message id
     * @param nameOfTable - storage name of table
     */
    public void deleteMessageForId(int id, String nameOfTable){

//        take user nik from session
        final String curentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();

//        take user from database by nikname
        User user = userService.showUserByNik(curentUserNik);

//        take message from database by id
        Message message = getMessageForId(id, nameOfTable);

//        check if user id equals foreign key user in message (only user, who create message can delete it)
        if(user.getId() == message.getUserId()) {
            messageDAO.deleteMessageById(id, nameOfTable);
        }

    }

    /**
     * method for get message by id
     *
     * @param id - storage message id
     * @param nameOfTable - storage name of table
     * @return - message
     */
    public Message getMessageForId(int id, String nameOfTable){

       return messageDAO.getMessageById(id, nameOfTable);

    }

    /**
     * method for and line break in message. logic witch insert '\n' behind word when row is longer then 30 char
     *
     * @param message - storage message
     * @return - message with line break
     */
    private String addLineBreak(String message){

//        check if message longer then 30 chars
        if(message.length() > 30) {

            int counter = 0;
            char [] chars = message.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();

//                increase counter for 1 on each char, when it became more than 30 and pointer between word add \n in chars
            for (char c : chars){

                counter++;

                if(counter > 30 && c == ' '){
                    c = '\n';
                    counter = 0;
                }
//                add chars in string builder
                stringBuilder.append(c);
            }
//            rewrite message on new one with line break
            message = new String(stringBuilder);

        }
        return message;
    }

}
