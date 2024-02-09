package com.analysis.analysis.controller;

import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleEntity> create(@RequestBody ArticleEntity article) {
        articleService.create(article);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleEntity> update(@PathVariable("id") Long id, @RequestBody ArticleEntity article) {
        articleService.update(article, id);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleEntity> getById(@PathVariable Long id) {
        ArticleEntity article = articleService.findByArticleId(id);
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ArticleEntity>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }


}
