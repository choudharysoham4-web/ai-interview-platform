package com.soham.aiinterview.controller;

import com.soham.aiinterview.dto.AiResponse;
import com.soham.aiinterview.dto.AiResponse;
import com.soham.aiinterview.service.GeminiService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/resume")
@CrossOrigin("*")
public class ResumeController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/analyze")
    public AiResponse analyzeResume(@RequestParam("file") MultipartFile file) {

        try {
            PDDocument document = PDDocument.load(file.getInputStream());

            PDFTextStripper stripper = new PDFTextStripper();

            String resumeText = stripper.getText(document);

            document.close();

            String result = geminiService.analyzeResume(resumeText);

            return new AiResponse(result);

        } catch (IOException e) {
            return new AiResponse("Failed to read resume PDF");
        }
    }
}