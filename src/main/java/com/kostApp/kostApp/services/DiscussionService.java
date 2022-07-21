package com.kostApp.kostApp.services;


import com.kostApp.kostApp.DAO.DiscussionDAO;
import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.repositories.DiscussionRepository;
import com.kostApp.kostApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * service class for discussion
 */
@Service
public class DiscussionService {

    private UserRepository userRepository;

    private DiscussionRepository discussionRepository;

    private DiscussionDAO discussionDAO;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository, DiscussionDAO discussionDAO) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
        this.discussionDAO = discussionDAO;
    }

    /**
     * method for save discussion and service staff
     *
     * @param discussion - storage discussion and give it into discussionDAO
     * @return - return current discussion
     */
    public Discussion save(Discussion discussion){

//        method save discussion in database
        discussionDAO.createTable(discussion);

//        get user from session
        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByNik(currentUserNik);
        User user = optionalUser.get();

//        set user, who created discussion, to discussion
        discussion.setUser(user);

//        save discussion in database
        discussionRepository.save(discussion);

        return discussion;
    }

    /**
     * method for get discussion from database by id
     *
     * @param id - storage discussion id
     * @return - current discussion
     */
    public Discussion getDiscussionById(int id){

//        find discussion by id
        Optional<Discussion> optionalDiscussion = discussionRepository.findById(id);

//        if no such discussion with this id throw exception
        if(optionalDiscussion.isEmpty()){
            throw new RuntimeException("discussion with this id did not found ");
        }

        return optionalDiscussion.get();
    }

    /**
     * method for get all discussions from database
     *
     * @return - list of discussion
     */
    public List<Discussion> getAllDiscussions(){

        return discussionRepository.findAll();

    }

    /**
     * method for delete discussion by id
     *
     * @param id - storage discussion id
     */
    public void deleteById(int id){

//       get discussion
       Optional<Discussion> optionalDiscussion = discussionRepository.findById(id);
       Discussion discussion = optionalDiscussion.get();

//       find user by foreign key
       Optional<User> optionalUser = userRepository.findById(discussion.getUserId());
       User user = optionalUser.get();

//       find nik of user from session
       final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();

//       check if user from session equal user from discussion (only user who create discussion can delete it)
       if(user.getNik().equals(currentUserNik)){
           discussionDAO.dropTable(discussion);
           discussionRepository.deleteById(id);
       }else {
           throw new RuntimeException("you can`t delete discussion");
       }

    }

}
