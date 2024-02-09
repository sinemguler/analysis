package com.analysis.analysis.service;

import com.analysis.analysis.exception.ArticleNotFoundException;
import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.repository.ArticleRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)

public class ArticleServiceTest {
    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    void testCreate() {
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity article = easyRandom.nextObject(ArticleEntity.class);

        Mockito.when(articleRepository.save(Mockito.any(ArticleEntity.class))).thenReturn(article);
        article.setTitle("title");
        articleService.create(article);
        assertThat(article.getTitle()).isEqualTo("title");
    }


    @Test
    void testDelete() {
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity article = easyRandom.nextObject(ArticleEntity.class);
        Long id = 1L;
        articleService.delete(id);
        verify(articleRepository).deleteById(id);
    }

    @Test
    public void testFindByArticleId() {
        Long articleId = 1L;
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity article = easyRandom.nextObject(ArticleEntity.class);
        article.setId(articleId);
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        ArticleEntity articles = articleService.findByArticleId(articleId);
        assertEquals(article.getId(), articles.getId());
    }


    @Test
    public void testFindByArticleIdNotFound() {
        Long articleId = 2L;
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> {
            articleService.findByArticleId(articleId);
        });
    }


    @Test
    void testUpdate() {
        Long id = 1L;
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity existingArticle = easyRandom.nextObject(ArticleEntity.class);
        existingArticle.setId(id);
        existingArticle.setTitle("title");
        ArticleEntity updatedArticle = easyRandom.nextObject(ArticleEntity.class);
        updatedArticle.setTitle("başlık");
        updatedArticle.setId(id);
        when(articleRepository.findById(id)).thenReturn(Optional.of(existingArticle));
        when(articleRepository.save(any(ArticleEntity.class))).thenReturn(updatedArticle);
        ArticleEntity result = articleService.update(updatedArticle, id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo(updatedArticle.getTitle());
    }


    @Test
    void testThrowExceptionUpdateArticle() {
        Long id = 5L;
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity existingArticle = easyRandom.nextObject(ArticleEntity.class);
        when(articleRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ArticleNotFoundException exception = Assertions.assertThrows(ArticleNotFoundException.class, () -> {
            articleService.update(existingArticle, id);
        });

        assertThat(exception.getMessage()).isEqualTo("Article not found");
    }

    @Test
    void testGetAll() {
        EasyRandom easyRandom = new EasyRandom();
        ArticleEntity existingArticle = easyRandom.nextObject(ArticleEntity.class);
        ArticleEntity updatedArticle = easyRandom.nextObject(ArticleEntity.class);

        List<ArticleEntity> articleEntities = new ArrayList<>();
        articleEntities.add(existingArticle);
        articleEntities.add(updatedArticle);
        when(articleRepository.findAll()).thenReturn(articleEntities);

        List<ArticleEntity> articleEntityList = articleService.getAll();

        assertThat(articleEntityList)
                .isNotNull()
                .hasSize(articleEntities.size())
                .containsExactlyElementsOf(articleEntities);

        verify(articleRepository).findAll();
    }
}
