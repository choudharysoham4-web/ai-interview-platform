package com.soham.aiinterview.controller;

import com.soham.aiinterview.dto.AiQuestionRequest;
import com.soham.aiinterview.dto.AiResponse;
import com.soham.aiinterview.dto.EvaluateRequest;
import com.soham.aiinterview.entity.InterviewQuestion;
import com.soham.aiinterview.repository.InterviewQuestionRepository;
import com.soham.aiinterview.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
@CrossOrigin("*")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    @PostMapping("/questions")
    public AiResponse generateQuestions(@RequestBody AiQuestionRequest request) {

        String response = geminiService.generateQuestions(
                request.getTopic(),
                request.getDifficulty()
        );

        InterviewQuestion question = new InterviewQuestion();
        question.setTopic(request.getTopic());
        question.setDifficulty(request.getDifficulty());
        question.setQuestions(response);

        interviewQuestionRepository.save(question);

        return new AiResponse(response);
    }

    @GetMapping("/history")
    public List<InterviewQuestion> getHistory() {
        return interviewQuestionRepository.findAll();
    }

    @PostMapping("/evaluate")
    public AiResponse evaluateAnswer(@RequestBody EvaluateRequest request) {

        String result = geminiService.evaluateAnswer(
                request.getQuestion(),
                request.getAnswer()
        );

        return new AiResponse(result);
    }
}