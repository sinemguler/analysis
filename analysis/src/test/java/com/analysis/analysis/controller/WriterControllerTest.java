package com.analysis.analysis.controller;

import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.service.WriterService;
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

public class WriterControllerTest {
    @Mock
    private WriterService writerService;

    @InjectMocks
    private WriterController writerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity writer = easyRandom.nextObject(WriterEntity.class);
        when(writerService.create(writer)).thenReturn(writer);
        ResponseEntity<WriterEntity> responseEntity = writerController.create(writer);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(writer, responseEntity.getBody());
        verify(writerService).create(writer);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        WriterEntity writerEntity = new WriterEntity();
        writerEntity.setId(id);
        writerEntity.setName("ayşe");
        when(writerService.update(writerEntity, id)).thenReturn(writerEntity);
        ResponseEntity<WriterEntity> responseEntity = writerController.update(id, writerEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(writerEntity, responseEntity.getBody());
        verify(writerService).update(writerEntity, id);
    }

    @Test
    public void testGetAll() {
        Long id = 1L;
        WriterEntity writerEntity = new WriterEntity();
        writerEntity.setId(id);
        writerEntity.setName("ayşe");
        WriterEntity writerEntity1 = new WriterEntity();
        writerEntity1.setId(id);
        writerEntity1.setName("ayşe");
        List<WriterEntity> writerEntityList = Arrays.asList(writerEntity, writerEntity1);
        when(writerService.getAll()).thenReturn(writerEntityList);
        ResponseEntity<List<WriterEntity>> response = writerController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(writerEntityList, response.getBody());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        WriterEntity writerEntity = new WriterEntity();
        writerEntity.setId(id);
        writerEntity.setName("ayşe");
        when(writerService.findByWriterId(id)).thenReturn(writerEntity);
        ResponseEntity<WriterEntity> response = writerController.getById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(writerEntity, response.getBody());
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(writerService.findByWriterId(id)).thenReturn(null);
        ResponseEntity<WriterEntity> response = writerController.getById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(writerService).delete(id);
        ResponseEntity<Void> responseEntity = writerController.delete(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(writerService).delete(id);
    }
}
