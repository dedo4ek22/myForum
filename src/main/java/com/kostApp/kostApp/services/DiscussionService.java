package com.kostApp.kostApp.services;


import com.kostApp.kostApp.models.Discussion;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.repositories.DiscussionRepository;
import com.kostApp.kostApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    private UserRepository userRepository;

    private DiscussionRepository discussionRepository;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
    }

    public Discussion save(Discussion discussion){

        final String currentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByNik(currentUserNik);
        User user = null;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else {
            throw new RuntimeException("Cannot save user to discussion in discussion service method \"save\"");
        }

        discussion.setUser(user);
        discussionRepository.save(discussion);

        return discussion;
    }

    public Discussion getDiscussionById(int id){

        Optional<Discussion> optionalDiscussion = discussionRepository.findById(id);

        if(optionalDiscussion.isEmpty()){
            throw new RuntimeException("discussion with this id did not found ");
        }

        return optionalDiscussion.get();
    }

    public List<Discussion> getAllDiscussions(){
        return discussionRepository.findAll();
    }




}
