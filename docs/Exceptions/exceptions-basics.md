# Exceptions Basics

** Definition
* An exception is an event that occurs during a program execution which disrupts the the flow of the program
* An Exception Object is created in case of an exception. It contains information about the error that occurred and
 the state of the program when it occurred. The Exception Object can be passed to the runtime system to be further
  handled  
  
** Checked and unchecked exceptions
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

** How does it work
*** Lifetime of an exception
* In case of an error an exception is thrown. That means an Exception Object is created and passed to the runtime
 system (the JVM)
* The runtime system checks in the [call stack] for a handler for the thrown exception.
* The handler defines how to treat the exception, e.g. an error object could be stored to the database and notify the
 user that an error occurred
* If a handler can not be found by the runtime system, then a default exception handler takes the Exception Object
. This default exception handler prints the exception information and terminates the program.

*** Syntax of exception handling
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
  
** Code examples
 
[call stack]: https://stackoverflow.com/questions/23981391/how-exactly-does-the-callstack-work