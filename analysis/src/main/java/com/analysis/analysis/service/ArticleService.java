package com.analysis.analysis.service;

import com.analysis.analysis.exception.ArticleNotFoundException;
import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleEntity create(ArticleEntity article){
        return articleRepository.save(article);
    }

    public ArticleEntity update(ArticleEntity updateArticle, Long id){
        Optional<ArticleEntity> articleEntity = articleRepository.findById(id);
        if (articleEntity.isPresent()) {
            ArticleEntity article = articleEntity.get();
            article.setTitle(updateArticle.getTitle());
            article.setContent(updateArticle.getContent());
            article.setWriter(updateArticle.getWriter());
            return articleRepository.save(article);
        } else {
            throw new ArticleNotFoundException("Article not found");
        }
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleEntity findByArticleId(Long id){
        Optional<ArticleEntity> article = articleRepository.findById(id);
        return article.orElseThrow(() -> new ArticleNotFoundException("Article not found : " + id));
    }

    public List<ArticleEntity> getAll() {
        return articleRepository.findAll();
    }

}

