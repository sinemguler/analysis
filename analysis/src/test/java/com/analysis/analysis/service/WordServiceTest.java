package com.analysis.analysis.service;

import com.analysis.analysis.exception.WordNotFoundException;
import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.repository.WordRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class WordServiceTest {
    @InjectMocks
    private WordService wordService;
    @Mock
    private WordRepository wordRepository;


    @Test
    void testCreate() {
        List<String> wordEntityList = new ArrayList<>();
        wordEntityList.add("word");
        WordEntity word = new WordEntity();
        word.setWord(wordEntityList);
        when(wordRepository.save(any(WordEntity.class))).thenReturn(word);
        WordEntity savedWord = wordService.create(word);
        assertThat(savedWord.getWord()).isEqualTo(wordEntityList);
    }


    @Test
    void testDelete() {
        EasyRandom easyRandom = new EasyRandom();
        WordEntity word = easyRandom.nextObject(WordEntity.class);
        Long id = 1L;
        wordService.delete(id);
        verify(wordRepository).deleteById(id);
    }

    @Test
    public void testFindByArticleId() {
        Long wordId = 1L;
        EasyRandom easyRandom = new EasyRandom();
        WordEntity word = easyRandom.nextObject(WordEntity.class);
        word.setId(wordId);
        when(wordRepository.findById(wordId)).thenReturn(Optional.of(word));
        WordEntity wordEntity = wordService.findByWordId(wordId);
        assertEquals(word.getId(), wordEntity.getId());
    }


    @Test
    public void testFindByArticleIdNotFound() {
        Long wordId = 2L;
        when(wordRepository.findById(wordId)).thenReturn(Optional.empty());
        assertThrows(WordNotFoundException.class, () -> {
            wordService.findByWordId(wordId);
        });
    }


    @Test
    void testUpdate() {
        Long id = 1L;
        EasyRandom easyRandom = new EasyRandom();
        WordEntity existingWord = easyRandom.nextObject(WordEntity.class);
        existingWord.setId(id);
        existingWord.setWord(Collections.singletonList("existingWord"));
        WordEntity updatedWord = easyRandom.nextObject(WordEntity.class);
        updatedWord.setId(id);
        updatedWord.setWord(Collections.singletonList("updatedWord"));
        when(wordRepository.findById(id)).thenReturn(Optional.of(existingWord));
        when(wordRepository.save(any(WordEntity.class))).thenReturn(updatedWord);
        WordEntity result = wordService.update(updatedWord, id);

        assertEquals(updatedWord.getWord(), result.getWord());
        assertEquals(updatedWord.getWriter(), result.getWriter());
        verify(wordRepository, times(1)).findById(id);
        verify(wordRepository, times(1)).save(any(WordEntity.class));

    }


    @Test
    void testThrowExceptionUpdateWord() {
        Long id = 5L;
        EasyRandom easyRandom = new EasyRandom();
        WordEntity existingWord = easyRandom.nextObject(WordEntity.class);
        when(wordRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        WordNotFoundException exception = Assertions.assertThrows(WordNotFoundException.class, () -> {
            wordService.update(existingWord, id);
        });

        assertThat(exception.getMessage()).isEqualTo("Word not found");
    }

    @Test
    void testGetAll() {
        EasyRandom easyRandom = new EasyRandom();
        WordEntity existingWord = easyRandom.nextObject(WordEntity.class);
        WordEntity updatedWord = easyRandom.nextObject(WordEntity.class);

        List<WordEntity> wordEntityList = new ArrayList<>();
        wordEntityList.add(existingWord);
        wordEntityList.add(updatedWord);
        when(wordRepository.findAll()).thenReturn(wordEntityList);

        List<WordEntity> wordEntities = wordService.getAll();

        assertThat(wordEntities)
                .isNotNull()
                .hasSize(wordEntityList.size())
                .containsExactlyElementsOf(wordEntityList);

        verify(wordRepository).findAll();
    }
}
