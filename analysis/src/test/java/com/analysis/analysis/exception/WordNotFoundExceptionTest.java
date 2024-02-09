package com.analysis.analysis.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WordNotFoundExceptionTest {

    @Test
    public void testWordNotFoundException() {
        String message = "Word not found!";
        WordNotFoundException exception = new WordNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

}
