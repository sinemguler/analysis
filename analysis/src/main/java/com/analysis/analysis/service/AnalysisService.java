package com.analysis.analysis.service;

import com.analysis.analysis.model.ArticleEntity;
import com.analysis.analysis.model.WordEntity;
import com.analysis.analysis.model.WriterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisService {

    @Autowired
    private WriterService writerService;

    @Autowired
    private ArticleService articleService;

    public WriterEntity analysisArticle(String text) {
        List<String> words = extractWords(text);
        List<WordEntity> wordEntities = getUserArticles();
        WriterEntity writerEntity = findMatchingWriter(words, wordEntities);
        return writerEntity;
    }

    private List<String> extractWords(String text) {
        String[] conjunctionList = {"ve", "ama", "veya", "ile", "ancak", "fakat", "çünkü", "eğer", "lakin", "halbuki"};
        List<String> conjunctions = Arrays.asList(conjunctionList);

        List<String> words = new ArrayList<>();
        String[] splitWords = text.split("\\s+");
        words.addAll(Arrays.asList(splitWords));
        words.removeAll(conjunctions);
        return words;
    }

    private List<WordEntity> getUserArticles() {
        List<WordEntity> wordEntityList = new ArrayList<>();
        List<WriterEntity> writerEntities = writerService.getAll();
        for (WriterEntity writer : writerEntities) {
            List<ArticleEntity> articles = writer.getArticle();
            for (ArticleEntity article : articles) {
                List<String> words = extractWords(article.getContent());
                WordEntity wordEntity = new WordEntity();
                wordEntity.setWriter(writer);
                wordEntity.setWord(words);
                wordEntityList.add(wordEntity);
            }
        }
        return wordEntityList;
    }

    private WriterEntity findMatchingWriter(List<String> words, List<WordEntity> wordEntityList) {
        WriterEntity matchingUser = null;
        int maxMatchCount = 0; //en fazla kullanılan kelime
        for (WordEntity wordEntity : wordEntityList) {
            List<String> articleWords = wordEntity.getWord();
            int matchCount = 0;//kelime sayısını sakla
            for (String word : words) {
                if (articleWords.contains(word)) {
                    matchCount++;
                }
            }
            if (matchCount > maxMatchCount) {
                maxMatchCount = matchCount;//eşleşme varsa kaydet
                matchingUser = wordEntity.getWriter();
            }
        }
        return matchingUser;
    }


}











