package com.firstProject.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Poll;

import java.util.List;

public interface PollRepository {
    Long createPoll(Poll poll);

    void updatePoll(Poll poll)throws JsonProcessingException;

    void deletePollById(Long id);

    Poll getPollById(Long id) throws JsonProcessingException;
}


