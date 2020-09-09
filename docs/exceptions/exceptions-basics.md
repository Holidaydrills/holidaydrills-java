# Exceptions Basics

## Definition
* An exception is an event that occurs during a program execution which disrupts the the flow of the program
* An Exception Object is created in case of an exception. It contains information about the error that occurred and
 the state of the program when it occurred. The Exception Object can be passed to the runtime system to be further
  handled  
  
## Checked and unchecked exceptions
* **Checked Exception**: 
   * Checked exceptions are are checked at compile time
   * That means if you use a checked exception in your code, the compiler enforces you to handle that exception
   * You should use checked exceptions whenever your program could recover from that exception
   * An example would be that our program expects an URL as a user input. But the a user would enter a malformed URL
   . In that case we should anticipate that a user could enter a wrong URL and handle this case appropriately, e.g
   . by showing an error message to the user.
* **Unchecked Exceptions**:
   * Unchecked exceptions are not checked at compile time, so you're not forced to handle this kind of exceptions
   * These are exceptions that you don't expect your program to recover from
   * An example for an unchecked exception is an issue in the program logic. E.g. If you divide with 0 then you get a
    *ArithmeticException*. You should rather fix that bug than handle the exception.

## How does it work
### Lifetime of an exception
* In case of an error an exception is thrown. That means an Exception Object is created and passed to the runtime
 system (the JVM)
* The runtime system checks in the [call stack] for a handler for the thrown exception.
* The handler defines how to treat the exception, e.g. an error object could be stored to the database and notify the
 user that an error occurred
* If a handler can not be found by the runtime system, then a default exception handler takes the Exception Object
. This default exception handler prints the exception information and terminates the program.

### Syntax of exception handling
There is a set of keywords for exception handling. These are `try`, `catch`, `finally`, `throw`, `throws`.
* `try`: You put all code that potentially could throw an exception into the try block
* `catch`: The catch block follows the try block. Here is the place you specify what kind of exceptions you want to
 catch and how you want to handle them
* `finally`: The optional finally block is always executed (also if no exception occurs). Here you can do some
 cleanup that always has to be done
* `throw`: With the throw keyword you can throw an exception that you instantiated
* `throws`: In case your method throws an exception and you don't have an exception handler within that method you
 have to add the throws keyword to the method signature. This indicates to the caller of the method that it has to
  handle the exception.  
  
## Code examples
### try catch block   
In the example below
* We instantiate a [FileReader]
* In case the file cannot be found the constructor throws an [FileNotFoundException]
* In that case we handle the exception in a catch block by printing the stackTrace   

The compiler enforces us in this case to catch the exception as the [FileNotFoundException] is a checked exception.
   
```Java
public void checkedException() {
    try {
        FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt");
    } catch (FileNotFoundException e){
        System.out.println("Dear user, the file cannot be found.");
        e.printStackTrace();
    }
}
```

### throws 
In the example below we also read a file, but this time we don't catch the exception
* We instantiate a [FileReader]
* In case the file cannot be found the constructor throws an [FileNotFoundException]
* In that case the method throws an exception
* The exception is then handled by the calling method `callThrowsException()`

```Java
public void throwsExample() throws FileNotFoundException {
    FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt");
}

public void callThrowsException() {
    try {
        throwsExample();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
``` 

### try catch finally
In the example below we use in addition the finally block which allows us to do the necessary cleanup work
* We instantiate the FileReader first with null, so that we can access it later in the finally block
* In the try block we instantiate a new [FileReader] and assign it to the `fileReader` variable
* In case the file cannot be found the constructor throws an exception which we handle by printing the stack trace
* In the finally block we cleanup the resources by closing the `fileReader`. (The `close()`method of the fileReader
 throws an `IOException`, that's why our `finalExample()` method must have the *throws IOException* in its delaration
 .)  
 
 
```Java
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
```

### try with resources
With Java 7 there comes the try-with-resources feature. This allows to pass resources to the try block which are then
 automatically cleaned up after the try block finished (the passed resource must implement the [java.io.Closable
 ] interface). This can be either because it successfully ended or because some exception occurred. That means that
  the resource will always be cleaned up.  
Here is what happens in the example below:
* The [FileReader] is instantiated within the resource part of the try block. Because this can result in an
 IOException we have to add a `throws IOException` to the method signature of our method `tryWithResourcesExample()`
* Within the try block we have some logic
* In case an exception occurs the catch block will handle it
* The finally block will always be executed telling the user that the file read was finished either due to success or
 because some exception occurred  
 


```Java
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
```

### Handling several exceptions
It is also possible to handle multiple exceptions that occur. In the example below we not only instantiate a
 [FileReader] but we also read a file with the FileReader's [read()] method.  
This is what happens in the example below:
* First we instantiate the `fileReader` variable by assigning null to it. This is needed in order to access the 
* In the *try* block we first instantiate the [FileReader]. This can cause a *FileNotFoundException* which we handle in
 the first *catch* block
* The second thing we do in the *try* block is to read the file char by char. the [read()] method throws an
 *IOException* which we handle with the second *catch* block.
* In the *finally* block we inform the user that the file read had finished either due to success or because some
 exception occurred  
* As we used the *try-with-resource* pattern the FileReader will be closed at the end  


```Java
public void handleMultipleExceptionOfSameHierarchy() throws IOException {
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
```

### Handling several exceptions of the same hierarchy 
In the example above we handled a [FileNotFoundException] and an [IOException] separately. This is because we wanted
 to provide a specific user message for each exception. But in this example it would be possible to handle both
  exceptions at once by only handling the [IOException]. This is because both exceptions are in the same class
   hierarchy. To be specific the [FileNotFoundException] is a subclass of [IOException].  
Let's see how this would look like with an example: 
* Here we only handle the *IOException* which implicitly handles its subclass *FileNotFoundException*  

 
```Java
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
```

### Handling several exceptions of different class hierarchies
It's also possible to handle exceptions of different class hierarchies in one single *catch* block.  
Let's have a look at an example:  
* First we instantiate a [FileReader] in the *try-with-resource* block. For that we need to add the *IOException* to
 the method signature as the closing of the *fileRader* can cause an *IOException*
* Within the try block we get a class by it's name. This can potentially throw a [ClassNotFoundException]
* In the catch block we handle both exceptions
* In the finally block we tell the user that everything went well  


```Java
public void handleMultipleExceptionsInOneCatchBlock() throws IOException {
    try (FileReader fileReader = new FileReader("/usr/Users/Ada/testfile.txt")) {
        Class someClass = Class.forName("com.holidaydrills.java.exceptionhandling.ExceptionExamples");
        // Do something ...
    } catch (FileNotFoundException | ClassNotFoundException e){
        System.out.println("Dear user, something went wrong");
        e.printStackTrace();
    } finally {
        System.out.println("Dear user, the file with the name 'testfile.txt' and the class with the name 'ExceptionExamples' could be found.");
    }
}
```  

We can do the same as above by handling the exceptions in two separate *catch* blocks:  


```Java
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
```
 
[call stack]: https://stackoverflow.com/questions/23981391/how-exactly-does-the-callstack-work
[FileReader]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileReader.html
[FileNotFoundException]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileNotFoundException.html
[read()]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/InputStreamReader.html#read()
[java.io.Closable]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/Closeable.html
[IOException]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/IOException.html
[ClassNotFoundException]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/ClassNotFoundException.html