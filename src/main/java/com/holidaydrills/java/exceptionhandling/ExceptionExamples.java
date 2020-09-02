package com.holidaydrills.java.exceptionhandling;


import java.io.FileInputStream;
import java.io.IOException;

public class ExceptionExamples {

    public void checkedException() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/usr/Users/Ada/testfile.txt");
        } catch (IOException e){
            System.out.println("Exception when reading file: " + e.getStackTrace());
        }
    }

}
