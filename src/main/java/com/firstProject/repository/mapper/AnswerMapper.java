package com.firstProject.repository.mapper;

import com.firstProject.model.Answer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("poll_id"),
                rs.getString("answer")
        );
        return answer;
    }
}