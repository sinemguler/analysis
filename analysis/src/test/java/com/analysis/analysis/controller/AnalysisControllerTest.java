package com.analysis.analysis.controller;

import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.service.AnalysisService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnalysisControllerTest {

    @Mock
    private AnalysisService analysisService;

    @InjectMocks
    private AnalysisController analysisController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity article = easyRandom.nextObject(ArticleEntity.class);
        List<ArticleEntity> articleEntities = new ArrayList<>();
        article.setId(1L);
        article.setTitle("title");
        article.setContent("content");
        articleEntities.add(article);
        WriterEntity writer = easyRandom.nextObject(WriterEntity.class);
        writer.setId(1L);
        writer.setName("ayşe");
        writer.setArticle(articleEntities);
        String text = "text";
        String articleText = "text";
        when(analysisService.analysisArticle(text)).thenReturn(writer);
        ResponseEntity<String> responseEntity = analysisController.analysisArticle(articleText);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Article belongs to the user: ayşe", responseEntity.getBody());
        verify(analysisService).analysisArticle(text);
    }


}
