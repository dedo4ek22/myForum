package com.kostApp.kostApp.DAO;

import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.models.User;
import com.kostApp.kostApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * class with implementation operation for messages storage
 */
@Component
public class MessageDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    /**
     * method for save message in database
     *
     * @param message - storage text of message
     * @param nameOfTable - storage name of table
     * @param discussionId - storage id of discussion where message was send
     */
    public void saveMessage(String message, String nameOfTable, int discussionId){

//        take user from session for set foreign key on user in message
        final String curentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserByNik(curentUserNik);

        jdbcTemplate.update("INSERT INTO " + nameOfTable +
                        "(discussion_id, message, created_at, user_id) values (?,?,?,?)",
                discussionId,
                message,
                getCurrentTimeStamp(),
                user.getId());

    }

    /**
     * method for take from database all message for current discussion
     *
     * @param nameOfTable - storage name of table
     * @return - list of messages
     */
    public List<Message> getMessageList(String nameOfTable){

        return jdbcTemplate.query("SELECT * FROM " + nameOfTable + " ORDER BY created_at DESC ", new MessageMapper());

    }

    /**
     * method for delete message from table
     *
     * @param id - storage id current message
     * @param nameOfTable - storage name of table
     */
    public void deleteMessageById(int id, String nameOfTable){

        jdbcTemplate.update("DELETE FROM " + nameOfTable + " WHERE id = ?",
                id);

    }

    /**
     * method for get message from database by message id
     *
     * @param id - storage message id
     * @param nameOfTable - storage name of table
     * @return - message or null if no such message with this id
     */
    public Message getMessageById(int id, String nameOfTable){

        return jdbcTemplate.query("SELECT * FROM " + nameOfTable + " WHERE id = ?",
                new Object[] {id},
                new MessageMapper()
        ).stream().findAny().orElse(null);

    }

    /**
     * method for edit message
     *
     * @param id - storage message id
     * @param message - storage message object
     * @param nameOfTable - storage name of table
     */
    public void editMessage(int id, Message message, String nameOfTable){

        jdbcTemplate.update("UPDATE " + nameOfTable + " SET message = ? WHERE id = ?" ,
                message.getMessage(),
                id);

    }

    /**
     * method for return time, when message was send
     *
     * @return - timestamp with time of send
     */
    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}

/**
 * class for transfer row from database with message data in java object
 */
class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();

        message.setId(rs.getInt("id"));
        message.setDiscussionId(rs.getInt("discussion_id"));
        message.setMessage(rs.getString("message"));
        message.setCreatedAt(rs.getTimestamp("created_at"));
        message.setUserId(rs.getInt("user_id"));


        return message;
    }
}
