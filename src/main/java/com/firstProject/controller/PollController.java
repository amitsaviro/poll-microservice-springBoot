package com.firstProject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Poll;
import com.firstProject.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping("/create")
    public void createPoll(@RequestBody Poll poll) throws JsonProcessingException {
        pollService.createPoll(poll);
    }

    @PutMapping("/update")
    public void updatePoll(@RequestBody Poll poll) throws JsonProcessingException{
        pollService.updatePoll(poll);
    }

    @DeleteMapping("/delete/{pollId}")
    public void deletePollById(@PathVariable Long pollId) {
        pollService.deletePollById(pollId);
    }

    @GetMapping("/{pollId}")
    public Poll getPollById(@PathVariable Long pollId) throws JsonProcessingException {
        return pollService.getPollById(pollId);
    }
}



