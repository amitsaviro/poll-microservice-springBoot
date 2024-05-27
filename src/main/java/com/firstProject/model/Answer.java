package com.firstProject.model;


public class Answer {

    private Long id;

    private Long userId;

    private Long pollId;

    private String userAnswer;
    public Answer(Long id, Long userId, Long pollId, String userAnswer) {
        this.id=id;
        this.userId = userId;
        this.pollId = pollId;
        this.userAnswer = userAnswer;
    }

    public Answer() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
