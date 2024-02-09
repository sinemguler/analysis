package com.analysis.analysis.controller;

import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<String> analysisArticle(@RequestBody String article) {
        WriterEntity writer = analysisService.analysisArticle(article);
        if (writer != null && writer.getName() != null) {
            System.out.println("yazar " + writer);
            return ResponseEntity.ok("Article belongs to the user: " + writer.getName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


