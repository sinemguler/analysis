package com.analysis.analysis.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WriterNotFoundExceptionTest {

    @Test
    public void testWriterNotFoundException() {
        String message = "Writer not found!";
        WriterNotFoundException exception = new WriterNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

}
