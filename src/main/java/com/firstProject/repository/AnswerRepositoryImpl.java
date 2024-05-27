package com.firstProject.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstProject.model.Answer;
import com.firstProject.repository.cache.CacheRepository;
import com.firstProject.repository.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {
    private static final String USER_POLL_TABLE_NAME = "user_poll";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CacheRepository cacheRepository;

@Override
    public Long createAnswer(Answer answer) throws JsonProcessingException  {
        String sql = "INSERT INTO " + USER_POLL_TABLE_NAME + " (user_id, poll_id, answer) " +
                "SELECT ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM " + USER_POLL_TABLE_NAME + " WHERE user_id = ? AND poll_id = ?)"; //not allow to enter the same userId and pollId
        jdbcTemplate.update(
                sql,
                answer.getUserId(),
                answer.getPollId(),
                answer.getUserAnswer(),
                answer.getUserId(), //for the condition
                answer.getPollId() //for the condition
        );
        Long answerId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class); //get the id we create
       answer.setId(answerId);
        String cacheKey = createAnswerIdCacheKey(answerId);
        try {
            String answerAsString = objectMapper.writeValueAsString(answer);
            cacheRepository.createCacheEntity(cacheKey, answerAsString); //create cache
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            e.printStackTrace();
        }
        return answerId;
    }

    @Override
    public Answer getAnswerById(Long id) throws JsonProcessingException {
        String cacheKey = createAnswerIdCacheKey(id);
        if (cacheRepository.isKeyExists(cacheKey)) { //check if there is inside the cache and return it
            String answer = cacheRepository.getCacheEntity(cacheKey);
            return objectMapper.readValue(answer, Answer.class);
        } else {
            String sql = "SELECT * FROM " + USER_POLL_TABLE_NAME + " WHERE id=?";
            try {
                Answer answer = jdbcTemplate.queryForObject(sql, answerMapper, id);
                String answerAsString = objectMapper.writeValueAsString(answer);
                cacheRepository.createCacheEntity(cacheKey, answerAsString); //create it in the cache
                return answer;
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Empty Data Warning");
                return null;
            }
        }
    }
    @Override
    public void deleteAnswerById(Long id) {
        cacheRepository.removeCacheEntity(id.toString()); //cache delete
        String sql = "DELETE FROM " + USER_POLL_TABLE_NAME + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public void updateAnswer(Answer answer)throws JsonProcessingException {
        if(cacheRepository.isKeyExists(answer.getId().toString())) { //if exists update cache
            String answerAsString = objectMapper.writeValueAsString(answer);
            cacheRepository.updateCacheEntity(answer.getId().toString(), answerAsString);
        }
        String sql = "UPDATE " + USER_POLL_TABLE_NAME + " SET user_id=?, poll_id=?, answer=? WHERE id=?";
        jdbcTemplate.update(
                sql,
                answer.getUserId(),
                answer.getPollId(),
                answer.getUserAnswer(),
                answer.getId()
        );
    }
    @Override
    public ResponseEntity<String> deleteAllDataByUserId(Long id) { //delete all data on user
        cacheRepository.removeCacheEntity(id.toString());
        String sql = "DELETE FROM " + USER_POLL_TABLE_NAME + " WHERE user_id=?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("User poll data deleted successfully for user ID: " + id);
        } else {
            // No data found for the user, return success message
            return ResponseEntity.ok("No data found for user ID: " + id + ". Nothing to delete.");
        }

    }
    @Override
    public List<Object[]> getDifferentAnswerCountingByPollId(Long pollId) {
        String sql = "SELECT answer, COUNT(*) AS answer_count FROM " + USER_POLL_TABLE_NAME + " WHERE poll_id = ? GROUP BY answer";
        return jdbcTemplate.query(sql, new Object[]{pollId}, (rs, rowNum) -> new Object[]{rs.getString("answer"), rs.getLong("answer_count")});
    }
    @Override
    public Long getTotalAnswerCountByPollId(Long pollId) {
        String sql = "SELECT COUNT(DISTINCT user_id) AS total_answer_count FROM " + USER_POLL_TABLE_NAME + " WHERE poll_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, pollId);
    }

    @Override
    public Map<Long, String> getUserAnswersByUserId(Long userId) {
        String sql = "SELECT poll_id, answer FROM " + USER_POLL_TABLE_NAME + " WHERE user_id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userId);

        Map<Long, String> userPollAnswers = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Long pollId = (Long) row.get("poll_id");
            String answer = (String) row.get("answer");

            userPollAnswers.put(pollId, answer);
        }
        return userPollAnswers;
    }
    @Override
    public Long getPollCountByUserId(Long userId) {
        String sql = "SELECT COUNT(DISTINCT poll_id) FROM " + USER_POLL_TABLE_NAME + " WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);
    }
    @Override
    public Map<Long, Map<String, Integer>> getAllPollResult() {
        String sql = "SELECT poll_id, answer, COUNT(*) FROM " + USER_POLL_TABLE_NAME + " GROUP BY poll_id, answer";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        Map<Long, Map<String, Integer>> pollResults = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Long pollId = (Long) row.get("poll_id");
            String answer = (String) row.get("answer");
            Integer count = ((Number) row.get("COUNT(*)")).intValue();

            pollResults.putIfAbsent(pollId, new HashMap<>());
            pollResults.get(pollId).put(answer, count);
        }
        return pollResults;
    }
    private String createAnswerIdCacheKey(Long answerId){
        return "answer.id: " + answerId;
    }
}
