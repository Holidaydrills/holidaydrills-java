package com.holidaydrills.java.exceptionhandling;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExceptionExamples {

    public void checkedException() {
        try {
            FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt");
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
        FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt");
    }

    public void finallyBlockExample() throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/usr/Users/Ada/testfile.txt");
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
        try (FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt")){
            // Do something
        } catch (FileNotFoundException e){
            System.out.println("Dear user, the file cannot be found.");
            e.printStackTrace();
        } finally {
            System.out.println("Dear user, reading the file finished either successfully or due to an error.");
        }
    }

    public void handleMultipleException() throws IOException {
        try (FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt")) {
            // Here we read the first character
            int charAsInt = fileReader.read();
            // In the while loop we print each character and read the next one
            while(charAsInt != -1) {
                System.out.println((char)charAsInt);
                charAsInt = fileReader.read();
                fileReader.read();
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

}
