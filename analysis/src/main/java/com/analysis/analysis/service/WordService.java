package com.analysis.analysis.service;

import com.analysis.analysis.exception.WordNotFoundException;
import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordService {
    private WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


    public WordEntity create(WordEntity word) {

        return wordRepository.save(word);
    }

    public WordEntity update(WordEntity updateWord, Long id) {
        Optional<WordEntity> wordEntity = wordRepository.findById(id);
        if (wordEntity.isPresent()) {
            WordEntity word = wordEntity.get();
            word.setWord(updateWord.getWord());
            word.setWriter(updateWord.getWriter());
            return wordRepository.save(word);
        } else {
            throw new WordNotFoundException("Word not found");
        }
    }


    public void delete(Long id) {
        wordRepository.deleteById(id);
    }

    public WordEntity findByWordId(Long id) {
        Optional<WordEntity> word = wordRepository.findById(id);
        return word.orElseThrow(() -> new WordNotFoundException("Word not found : " + id));
    }

    public List<WordEntity> getAll() {
        return wordRepository.findAll();
    }
}
