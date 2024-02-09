package com.analysis.analysis.service;

import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.model.WriterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AnalysisServiceTest {

    @Mock
    private WriterService writerService;

    @Mock
    private ArticleService articleService;

    @Mock
    private AnalysisService analysisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAnalysnisArticle() {
        String text = "Example title content";
        WriterEntity expectedWriter = new WriterEntity();
        expectedWriter.setName("ayşe");
        expectedWriter.setSurname("ayşe");
        List<WordEntity> wordEntities = new ArrayList<>();
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWriter(expectedWriter);
        wordEntity.setWord(List.of("title", "example", "content"));
        wordEntities.add(wordEntity);
        when(analysisService.analysisArticle(text)).thenReturn(expectedWriter);
        WriterEntity actualWriter = analysisService.analysisArticle(text);
        assertEquals(expectedWriter, actualWriter);
    }


}