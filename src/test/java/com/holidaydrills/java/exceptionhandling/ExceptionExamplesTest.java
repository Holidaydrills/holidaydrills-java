package com.holidaydrills.java.exceptionhandling;


import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionExamplesTest {

    ExceptionExamples cut = new ExceptionExamples();

    @Test
    void checkedException() {
        cut.checkedException();
    }

    @Test
    void callThrowsException() {
        cut.callThrowsException();
    }

    @Test
    void throwsExample() {
        assertThrows(FileNotFoundException.class, () -> cut.throwsExample());
    }

    @Test
    void finallyBlockExample() throws IOException {
        String filePath = getClass().getResource("testfile.txt").getPath();
        cut.finallyBlockExample(filePath);
    }

    @Test
    void tryWithResourcesExample() throws IOException {
        cut.tryWithResourcesExample();
    }

    @Test
    void handleMultipleExceptionOfSameHierarchy() throws IOException {
        cut.handleMultipleExceptionOfSameHierarchy("/file/does/not/exists.txt");
    }

    @Test
    void handleMultipleExceptionOfSameHierarchy_success() throws IOException {
        String filePath = getClass().getResource("testfile.txt").getPath();
        cut.handleMultipleExceptionOfSameHierarchy(filePath);
    }

    @Test
    void handleExceptionsWithSameHierarchy() {
        cut.handleExceptionsWithSameHierarchy();
    }

    @Test
    void handleMultipleExceptionsInOneCatchBlock() throws IOException {
        cut.handleMultipleExceptionsInOneCatchBlock();
    }

    @Test
    void handleMultipleExceptionsInSeparateCatchBlocks_with_not_existing_file() throws IOException {
        cut.handleMultipleExceptionsInSeparateCatchBlocks("/file/does/not/exists.txt");
    }

    @Test
    void handleMultipleExceptionsInSeparateCatchBlocks_with_existing_file() throws IOException {
        String filePath = getClass().getResource("testfile.txt").getPath();
        cut.handleMultipleExceptionsInSeparateCatchBlocks(filePath);
    }
}