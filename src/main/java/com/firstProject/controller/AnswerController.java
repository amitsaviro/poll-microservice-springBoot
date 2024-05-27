package com.firstProject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Answer;
import com.firstProject.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/answer")
@RestController
public class AnswerController {
    @Autowired
    private AnswerService answerService;


    @PostMapping("/create")
    public void createAnswer(@RequestBody Answer answer) throws JsonProcessingException {
        answerService.createAnswer(answer);
    }

    @GetMapping("/get/{answerId}")
    public Answer getAnswerById(@PathVariable Long answerId) throws JsonProcessingException {
        return answerService.getAnswerById(answerId);
    }

    @DeleteMapping("/delete/{answerId}")
    public void deleteAnswerById(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
    }

    @PutMapping("/update")
    public void updateAnswer(@RequestBody Answer answer)throws JsonProcessingException {
        answerService.updateAnswer(answer);
    }

    @DeleteMapping("/deleteAllData/{userId}")//delete data function when user delete himself
    public ResponseEntity<String> deleteAllDataByUserId(@PathVariable Long userId) {
        return answerService.deleteAllDataByUserId(userId);
    }

    @GetMapping("/getCountOfDifferentAnswer/{pollId}") //counting the different answers for poll
    public List<Object[]> getDifferentAnswerCountingByPollId(@PathVariable Long pollId) {
        return answerService.getDifferentAnswerCountingByPollId(pollId);
    }

    @GetMapping("/getTotalAnswerCount/{pollId}") //get how much answered this poll
    public Long getTotalAnswerCountByPollId(@PathVariable Long pollId) {
        return answerService.getTotalAnswerCountByPollId(pollId);
    }

    @GetMapping("/getUserAnswersByUserId/{userId}") //get all user poll answers by user id
    public Map<Long, String>  getUserAnswersByUserId(@PathVariable Long userId) {
        return answerService.getUserAnswersByUserId(userId);
    }

    @GetMapping("/getPollCountByUserId/{userId}") //get how much users answered this poll
    public Long getPollCountByUserId(@PathVariable Long userId) {
        return answerService.getPollCountByUserId(userId);
    }

    @GetMapping("/getAllPollResult") //full result
    public Map<Long, Map<String, Integer>> getAllPollResult() {
        return answerService.getAllPollResult();
    }
}

