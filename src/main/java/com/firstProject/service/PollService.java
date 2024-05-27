package com.firstProject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Poll;

import java.util.List;

public interface PollService {
    Long createPoll(Poll poll) throws JsonProcessingException;

    void updatePoll(Poll poll) throws JsonProcessingException;

    void deletePollById(Long id);

    Poll getPollById(Long id) throws JsonProcessingException;


}

