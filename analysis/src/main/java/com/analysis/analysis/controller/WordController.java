package com.analysis.analysis.controller;

import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/words")
public class WordController {

    @Autowired
    private WordService wordService;

    @PostMapping
    public ResponseEntity<WordEntity> create(@RequestBody WordEntity word) {
        wordService.create(word);
        return ResponseEntity.ok(word);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WordEntity> update(@PathVariable("id") Long id, @RequestBody WordEntity word) {
        wordService.update(word, id);
        return ResponseEntity.ok(word);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        wordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordEntity> getById(@PathVariable Long id) {
        WordEntity word = wordService.findByWordId(id);
        if (word != null) {
            return ResponseEntity.ok(word);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<WordEntity>> getAll() {
        return ResponseEntity.ok(wordService.getAll());
    }


}
