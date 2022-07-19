package com.kostApp.kostApp.services;

import com.kostApp.kostApp.models.Message;
import com.kostApp.kostApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveMessage(String message, String nameOfTable, int discussionId){

       message = addLineBreak(message);

        final String curentUserNik = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.showUserByNik(curentUserNik);

        jdbcTemplate.update("INSERT INTO " + nameOfTable +
                "(discussion_id, message, created_at, user_id) values (?,?,?,?)",
                discussionId,
                message,
                getCurrentTimeStamp(),
                user.getId());
    }

    public List<Message> messageList (String nameOfTable){
       return jdbcTemplate.query("SELECT * FROM " + nameOfTable + " ORDER BY created_at DESC ", new MessageMapper());
    }

    public void deleteMessageForId(int id, String nameOfTable){
        jdbcTemplate.update("DELETE FROM " + nameOfTable + " WHERE id = ?" ,
                id);
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }
    //  logic for long message witch insert '\n' behind word when row is longer then 30 char
    private String addLineBreak(String message){
        if(message.length() > 30) {
            int counter = 0;
            char [] chars = message.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : chars){

                counter++;
                if(counter > 30 && c == ' '){
                    c = '\n';
                    counter = 0;
                }
                stringBuilder.append(c);
            }
            message = new String(stringBuilder);
        }
        return message;
    }

    class MessageMapper implements RowMapper<Message>{

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
}
