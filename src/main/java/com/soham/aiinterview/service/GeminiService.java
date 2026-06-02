package com.soham.aiinterview.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    // GENERATE QUESTIONS
    public String generateQuestions(String topic, String difficulty) {

        try {

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + apiKey;

            String prompt =
                    "Generate 5 " + difficulty +
                            " interview questions on " + topic +
                            " with answers.";

            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(prompt);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            String responseBody = response.getBody();

            int textIndex = responseBody.indexOf("\"text\":");

            int start =
                    responseBody.indexOf("\"", textIndex + 7) + 1;

            int end =
                    responseBody.indexOf("\"", start);

            return responseBody.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");

        } catch (Exception e) {

            e.printStackTrace();

            return "Gemini Error: " + e.getMessage();
        }
    }

    // EVALUATE ANSWER
    public String evaluateAnswer(String question, String answer) {

        try {

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + apiKey;

            String prompt =
                    "Evaluate this interview answer.\n\n" +

                            "Question: " + question + "\n\n" +

                            "Candidate Answer: " + answer + "\n\n" +

                            "Give response in this format:\n" +

                            "Score: /10\n" +

                            "Feedback:\n" +

                            "Improvement Tips:";

            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(prompt);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            String responseBody = response.getBody();

            int textIndex = responseBody.indexOf("\"text\":");

            int start =
                    responseBody.indexOf("\"", textIndex + 7) + 1;

            int end =
                    responseBody.indexOf("\"", start);

            return responseBody.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");

        } catch (Exception e) {

            e.printStackTrace();

            return "Evaluation failed: " + e.getMessage();
        }
    }

    // RESUME ANALYSIS
    public String analyzeResume(String resumeText) {

        try {

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + apiKey;

            String prompt =
                    "Analyze this resume and provide:\n\n" +

                            "1. Detected Skills\n" +
                            "2. Strengths\n" +
                            "3. Weaknesses\n" +
                            "4. Recommended Technologies\n" +
                            "5. Personalized Interview Questions\n\n" +

                            "Resume:\n" + resumeText;

            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(prompt);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            String responseBody = response.getBody();

            int textIndex = responseBody.indexOf("\"text\":");

            int start =
                    responseBody.indexOf("\"", textIndex + 7) + 1;

            int end =
                    responseBody.indexOf("\"", start);

            return responseBody.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");

        } catch (Exception e) {

            e.printStackTrace();

            return "Resume analysis failed: " + e.getMessage();
        }
    }
}