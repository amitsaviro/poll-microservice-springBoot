package com.firstProject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firstProject.model.Answer;
import com.firstProject.repository.AnswerRepository;
import com.firstProject.userUrl.UserUrlResponse;
import com.firstProject.userUrl.UserUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserUrlService userUrlService;

    @Override
    public Long createAnswer(Answer answer) throws JsonProcessingException {
        try {
            UserUrlResponse user = userUrlService.getUserUrlById(answer.getUserId());
            if (user.getStatus().equals("NOT_REGISTER")) {
                throw new IllegalStateException("User is not registered");
            } else if (!Objects.equals(answer.getUserAnswer(), "a") && !Objects.equals(answer.getUserAnswer(), "b") && !Objects.equals(answer.getUserAnswer(), "c") && !Objects.equals(answer.getUserAnswer(), "d")) {
                throw new IllegalStateException("Answer must to be 'a', 'b', 'c' or 'd'");
            } else {
                return answerRepository.createAnswer(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("server error");
        }
    }

    @Override
    public Answer getAnswerById(Long id) throws JsonProcessingException {
        return answerRepository.getAnswerById(id);
    }

    @Override
    public void deleteAnswerById(Long id) {
        answerRepository.deleteAnswerById(id);
    }

    @Override
    public void updateAnswer(Answer answer)throws JsonProcessingException {
        try {
            UserUrlResponse user = userUrlService.getUserUrlById(answer.getUserId());
            if (user.getStatus().equals("NOT_REGISTER")) {
                throw new IllegalStateException("User is not registered");
            } else if (!Objects.equals(answer.getUserAnswer(), "a") && !Objects.equals(answer.getUserAnswer(), "b") && !Objects.equals(answer.getUserAnswer(), "c") && !Objects.equals(answer.getUserAnswer(), "d")) {
                throw new IllegalStateException("Answer must to be 'a', 'b', 'c' or 'd'");
            } else {
                answerRepository.updateAnswer(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("server error");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllDataByUserId(Long id) {
        return answerRepository.deleteAllDataByUserId(id);
    }

    @Override
    public List<Object[]> getDifferentAnswerCountingByPollId(Long pollId) {
        return answerRepository.getDifferentAnswerCountingByPollId(pollId);
    }

    @Override
    public Long getTotalAnswerCountByPollId(Long pollId) {
        return answerRepository.getTotalAnswerCountByPollId(pollId);
    }

    @Override
    public Map<Long, String>  getUserAnswersByUserId(Long userId) {
        return answerRepository.getUserAnswersByUserId(userId);
    }

    @Override
    public Long getPollCountByUserId(Long userId) {
        return answerRepository.getPollCountByUserId(userId);
    }

    @Override
    public Map<Long, Map<String, Integer>> getAllPollResult() {
        return answerRepository.getAllPollResult();
    }
}
