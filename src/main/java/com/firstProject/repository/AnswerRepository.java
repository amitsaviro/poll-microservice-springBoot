package com.firstProject.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Answer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AnswerRepository {
    Long createAnswer(Answer answer)throws JsonProcessingException ;
    Answer getAnswerById(Long id) throws JsonProcessingException;
    void deleteAnswerById(Long id);
    void updateAnswer(Answer answer)throws JsonProcessingException;
     ResponseEntity<String> deleteAllDataByUserId(Long id);
     List<Object[]> getDifferentAnswerCountingByPollId(Long pollId);
     Long getTotalAnswerCountByPollId(Long pollId);
    Map<Long, String>  getUserAnswersByUserId(Long userId);
    Long getPollCountByUserId(Long userId);
    Map<Long, Map<String, Integer>> getAllPollResult();
    }
