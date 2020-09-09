package com.holidaydrills.java.exceptionhandling;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionExamples {

    public void checkedException() {
        try {
            FileReader fileReader = new FileReader("./testfile.txt");
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        }
    }

    public void callThrowsException() {
        try {
            throwsExample();
        } catch (FileNotFoundException e) {
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        }
    }

    public void throwsExample() throws FileNotFoundException {
        FileReader fileReader = new FileReader("./testfile.txt");
    }

    public void finallyBlockExample(String filePath) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, reading the file finished either successfully or due to an error.");
            // Close the fileReader
            if(fileReader != null) {
                fileReader.close();
            }
        }
    }

    public void tryWithResourcesExample() throws IOException {
        try (FileReader fileReader = new FileReader("./testfile.txt")){
            // Do something
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, reading the file finished either successfully or due to an error.");
        }
    }

    public void handleMultipleExceptionOfSameHierarchy(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            // Here we read the first character
            int charAsInt = fileReader.read();
            // In the while loop we print each character and read the next one
            while(charAsInt != -1) {
                System.out.print((char)charAsInt);
                charAsInt = fileReader.read();
            }
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Dear user, we could not read the file.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, reading the file finished either successfully or due to an error.");
        }
    }

    public void handleExceptionsWithSameHierarchy() {
        try (FileReader fileReader = new FileReader("./testfile.txt")) {
            // Here we read the first character
            int charAsInt = fileReader.read();
            // In the while loop we print each character and read the next one
            while(charAsInt != -1) {
                System.out.println((char)charAsInt);
                charAsInt = fileReader.read();
                fileReader.read();
            }
        } catch (IOException e) {
            System.out.println("Dear user, something went wrong while opening and reading the file.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, reading the file finished either successfully or due to an error.");
        }
    }

    public void handleMultipleExceptionsInOneCatchBlock() throws IOException {
        try (FileReader fileReader = new FileReader("./testfile.txt")) {
            Class someClass = Class.forName("com.holidaydrills.java.exceptionhandling.ExceptionExamples");
            // Do something ...
        } catch (FileNotFoundException | ClassNotFoundException e){
            System.out.println("Dear user, something went wrong");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, the file with the name 'testfile.txt' and the class with the name 'ExceptionExamples' could be found.");
        }
    }

    public void handleMultipleExceptionsInSeparateCatchBlocks(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            Class someClass = Class.forName("com.holidaydrills.java.exceptionhandling.ExceptionExamples");
            // Do something ...
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file with the name 'testfile.txt' could not be found.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Dear user, the class with the name 'ExceptionExamples' could not be found.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, found successfully the file with the name 'testfile.txt' and the class with the name 'ExceptionExamples'.");
        }
    }

}
