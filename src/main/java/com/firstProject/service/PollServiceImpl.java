package com.firstProject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Poll;
import com.firstProject.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollRepository pollRepository;

    @Override
    public Long createPoll(Poll poll) throws JsonProcessingException {
        return pollRepository.createPoll(poll);
    }

    @Override
    public void updatePoll(Poll poll)throws JsonProcessingException {
        pollRepository.updatePoll(poll);
    }

    @Override
    public void deletePollById(Long id) {
        pollRepository.deletePollById(id);
    }

    @Override
    public Poll getPollById(Long id) throws JsonProcessingException {
        return pollRepository.getPollById(id);
    }
}
//



