package com.soham.aiinterview.entity;

import jakarta.persistence.*;

@Entity
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String difficulty;

    @Column(length = 5000)
    private String questions;

    public InterviewQuestion() {
    }

    public InterviewQuestion(String topic, String difficulty, String questions) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}