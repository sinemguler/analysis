package com.analysis.analysis.service;

import com.analysis.analysis.exception.WriterNotFoundException;
import com.analysis.analysis.model.WriterEntity;
import com.analysis.analysis.repository.WriterRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WriterServiceTest {

    @InjectMocks
    private WriterService writerService;
    @Mock
    private WriterRepository writerRepository;


    @Test
    void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity writer = easyRandom.nextObject(WriterEntity.class);

        Mockito.when(writerRepository.save(Mockito.any(WriterEntity.class))).thenReturn(writer);
        writer.setName("writer");
        writerService.create(writer);
        assertThat(writer.getName()).isEqualTo("writer");
    }


    @Test
    void testDelete() {
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity writer = easyRandom.nextObject(WriterEntity.class);
        Long id = 1L;
        writerService.delete(id);
        verify(writerRepository).deleteById(id);
    }

    @Test
    public void testFindByWriterId() {
        Long writerId = 1L;
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity writer = easyRandom.nextObject(WriterEntity.class);
        writer.setId(writerId);
        when(writerRepository.findById(writerId)).thenReturn(Optional.of(writer));
        WriterEntity writerEntity = writerService.findByWriterId(writerId);
        assertEquals(writer.getId(), writerEntity.getId());
    }


    @Test
    public void testFindByWriterIdNotFound() {
        Long writerId = 2L;
        when(writerRepository.findById(writerId)).thenReturn(Optional.empty());
        assertThrows(WriterNotFoundException.class, () -> {
            writerService.findByWriterId(writerId);
        });
    }


    @Test
    void testUpdate() {
        Long id = 1L;
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity existingWriter = easyRandom.nextObject(WriterEntity.class);
        existingWriter.setId(id);
        existingWriter.setName("writer");
        WriterEntity updatedWriter = easyRandom.nextObject(WriterEntity.class);
        updatedWriter.setName("writer");
        updatedWriter.setId(id);
        when(writerRepository.findById(id)).thenReturn(Optional.of(existingWriter));
        when(writerRepository.save(any(WriterEntity.class))).thenReturn(updatedWriter);
        WriterEntity result = writerService.update(updatedWriter, id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(updatedWriter.getName());
    }


    @Test
    void testThrowExceptionUpdateWriter() {
        Long id = 5L;
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity existingWriter = easyRandom.nextObject(WriterEntity.class);
        when(writerRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        WriterNotFoundException exception = Assertions.assertThrows(WriterNotFoundException.class, () -> {
            writerService.update(existingWriter, id);
        });

        assertThat(exception.getMessage()).isEqualTo("Writer not found");
    }

    @Test
    void testGetAll() {
        EasyRandom easyRandom = new EasyRandom();
        WriterEntity existingWriter = easyRandom.nextObject(WriterEntity.class);
        WriterEntity updatedWriter = easyRandom.nextObject(WriterEntity.class);

        List<WriterEntity> writerEntities = new ArrayList<>();
        writerEntities.add(existingWriter);
        writerEntities.add(updatedWriter);
        when(writerRepository.findAll()).thenReturn(writerEntities);

        List<WriterEntity> writerEntityList = writerService.getAll();

        assertThat(writerEntityList)
                .isNotNull()
                .hasSize(writerEntities.size())
                .containsExactlyElementsOf(writerEntities);

        verify(writerRepository).findAll();
    }
}
