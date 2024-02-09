package com.analysis.analysis.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleNotFoundExceptionTest {

    @Test
    public void testArticleNotFoundException() {
        String message = "Article not found!";
        ArticleNotFoundException exception = new ArticleNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

}
