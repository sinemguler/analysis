package com.analysis.analysis.controller;

import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.service.WordService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WordControllerTest {

    @Mock
    private WordService wordService;

    @InjectMocks
    private WordController wordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        WordEntity wordEntity = easyRandom.nextObject(WordEntity.class);
        when(wordService.create(wordEntity)).thenReturn(wordEntity);
        ResponseEntity<WordEntity> responseEntity = wordController.create(wordEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordEntity, responseEntity.getBody());
        verify(wordService).create(wordEntity);
    }


    @Test
    public void testFindById() {
        Long id = 1L;
        WordEntity word = new WordEntity();
        word.setId(id);
        word.setWord(Collections.singletonList("word"));
        when(wordService.findByWordId(id)).thenReturn(word);
        ResponseEntity<WordEntity> response = wordController.getById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(word, response.getBody());
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(wordService.findByWordId(id)).thenReturn(null);
        ResponseEntity<WordEntity> response = wordController.getById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAll() {
        WordEntity word1 = new WordEntity();
        word1.setId(1L);
        word1.setWord(Collections.singletonList("word1"));
        WordEntity word2 = new WordEntity();
        word2.setId(2L);
        word2.setWord(Collections.singletonList("word"));
        List<WordEntity> words = Arrays.asList(word1, word2);
        when(wordService.getAll()).thenReturn(words);
        ResponseEntity<List<WordEntity>> response = wordController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(words, response.getBody());
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        WordEntity wordEntity = new WordEntity();
        wordEntity.setId(id);
        wordEntity.setWord(Collections.singletonList("word"));
        when(wordService.update(wordEntity, id)).thenReturn(wordEntity);
        ResponseEntity<WordEntity> responseEntity = wordController.update(id, wordEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wordEntity, responseEntity.getBody());
        verify(wordService).update(wordEntity, id);
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(wordService).delete(id);
        ResponseEntity<Void> responseEntity = wordController.delete(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(wordService).delete(id);
    }


}
