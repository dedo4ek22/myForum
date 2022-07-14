package com.kostApp.kostApp.services;

import com.kostApp.kostApp.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveMessage(String message, String nameOfTable, int discussionId){
        jdbcTemplate.update("INSERT INTO " + nameOfTable +
                "(discussion_id, message, created_at) values (?,?,?)",
                discussionId,
                message,
                getCurrentTimeStamp());
    }

    public List<Message> messageList (String nameOfTable){
       return jdbcTemplate.query("SELECT * FROM " + nameOfTable + " ORDER BY created_at DESC ", new MessageMapper());
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    class MessageMapper implements RowMapper<Message>{

        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();

            message.setId(rs.getInt("id"));
            message.setDiscussionId(rs.getInt("discussion_id"));
            message.setMessage(rs.getString("message"));
            message.setCreatedAt(rs.getDate("created_at"));

            return message;
        }
    }
}
