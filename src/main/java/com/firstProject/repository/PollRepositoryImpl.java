package com.firstProject.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstProject.repository.cache.CacheRepository;
import com.firstProject.repository.mapper.PollMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.firstProject.model.Poll;


@Repository
public class PollRepositoryImpl implements PollRepository {

    private static final String CUSTOMER_TABLE_NAME = "poll";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PollMapper pollMapper;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Long createPoll(Poll poll) {
        String sql = "INSERT INTO " + CUSTOMER_TABLE_NAME + " " + "(title, first_answer, second_answer, third_answer, fourth_answer) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                poll.getTitle(),
                poll.getFirstAnswer(),
                poll.getSecondAnswer(),
                poll.getThirdAnswer(),
                poll.getFourthAnswer()
        );
        Long pollId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
        poll.setId(pollId);
        String cacheKey = createPollIdCacheKey(pollId);
        try {
            String pollAsString = objectMapper.writeValueAsString(poll);
            cacheRepository.createCacheEntity(cacheKey, pollAsString);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            e.printStackTrace();
        }
        return pollId;
    }

    @Override
    public void updatePoll(Poll poll)throws JsonProcessingException {
        if(cacheRepository.isKeyExists(poll.getId().toString())){
            String pollAsString=objectMapper.writeValueAsString(poll);
            cacheRepository.updateCacheEntity(poll.getId().toString(), pollAsString);
        }
        String sql = "UPDATE " + CUSTOMER_TABLE_NAME + " SET title=?, first_answer=?, second_answer=?, third_answer=?, fourth_answer=? WHERE id=?";
        jdbcTemplate.update(
                sql,
                poll.getTitle(),
                poll.getFirstAnswer(),
                poll.getSecondAnswer(),
                poll.getThirdAnswer(),
                poll.getFourthAnswer(),
                poll.getId()
        );
    }

    @Override
    public void deletePollById(Long id) {
        cacheRepository.removeCacheEntity(id.toString());
        String sql = "DELETE FROM " + CUSTOMER_TABLE_NAME + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Poll getPollById(Long id) throws JsonProcessingException {
        String cacheKey = createPollIdCacheKey(id);
        if (cacheRepository.isKeyExists(cacheKey)) {
            String poll = cacheRepository.getCacheEntity(cacheKey);
            return objectMapper.readValue(poll, Poll.class);
        }
    else{
        String sql = "SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE id=?";
        try {
            Poll poll = jdbcTemplate.queryForObject(sql, pollMapper, id);
            String pollAsString = objectMapper.writeValueAsString(poll);
            cacheRepository.createCacheEntity(cacheKey, pollAsString);
            return poll;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Empty Data Warning");
            return null;
        }
    }
}

    private String createPollIdCacheKey(Long pollId) {
        return "poll.id: " + pollId;
    }
}















