package com.firstProject.repository.mapper;

import com.firstProject.model.Poll;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PollMapper implements RowMapper<Poll> {

    @Override
    public Poll mapRow(ResultSet rs, int rowNum) throws SQLException {
        Poll poll = new Poll(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("first_answer"),
                rs.getString("second_answer"),
                rs.getString("third_answer"),
                rs.getString("fourth_answer")
            );
        return poll;
    }
}
