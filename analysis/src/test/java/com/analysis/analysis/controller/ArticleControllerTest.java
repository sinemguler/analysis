package com.analysis.analysis.controller;

import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.service.ArticleService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ArticleControllerTest {
    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity article = easyRandom.nextObject(ArticleEntity.class);
        when(articleService.create(article)).thenReturn(article);
        ResponseEntity<ArticleEntity> responseEntity = articleController.create(article);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(article, responseEntity.getBody());
        verify(articleService).create(article);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(id);
        articleEntity.setTitle("title");
        when(articleService.update(articleEntity, id)).thenReturn(articleEntity);
        ResponseEntity<ArticleEntity> responseEntity = articleController.update(id, articleEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(articleEntity, responseEntity.getBody());
        verify(articleService).update(articleEntity, id);
    }

    @Test
    public void testGetAll() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(1L);
        articleEntity.setTitle("title");
        ArticleEntity articleEntity1 = new ArticleEntity();
        articleEntity1.setId(1L);
        articleEntity1.setTitle("title");
        List<ArticleEntity> articleEntityList = Arrays.asList(articleEntity, articleEntity1);
        when(articleService.getAll()).thenReturn(articleEntityList);
        ResponseEntity<List<ArticleEntity>> response = articleController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(articleEntityList, response.getBody());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(1L);
        articleEntity.setTitle("title");
        when(articleService.findByArticleId(id)).thenReturn(articleEntity);
        ResponseEntity<ArticleEntity> response = articleController.getById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(articleEntity, response.getBody());
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(articleService.findByArticleId(id)).thenReturn(null);
        ResponseEntity<ArticleEntity> response = articleController.getById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(articleService).delete(id);
        ResponseEntity<Void> responseEntity = articleController.delete(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(articleService).delete(id);
    }
}
