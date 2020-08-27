# Exceptions Basics

Definition:
* An exception is an event that occurs during a program execution which disrupts the the flow of the program
* An exception object is created in case of an exception. It contains information about the error that occurred and
 the state of the program when it occurred. The exception object can be passed to the runtime system to be further
  handled  
  
**There are three kinds of exceptions**
* **Checked Exception**: 
   * These exceptions should always be handled by the program. They indicate that the program flow was disrupted by
    some error that was expected to happen at some point in time
   * An example would be that our program expects an URL as a user input. But the a user would enter a malformed URL
   . In that case we should anticipate that a user could enter a wrong URL and handle this case appropriately, e.g
   . by showing an error message to the user.
* **Errors**:
   * Errors are exceptions that are caused by conditions that are not in the responsibility of the application
   * Errors are unchecked exceptions as they cannot always be anticipated and don't need to be handled. 
   * An example would be that our application 

How does it work:
* In case of an error an exception is thrown. That means an exception object is created and passed to the runtime
 system
* The runtime system checks in the [call stack] for a handler for the thrown exception.
* The handler defines how to treat the exception, e.g. an error object could be stored to the database and notify the
 user that an error occurred

 
[call stack]: https://stackoverflow.com/questions/23981391/how-exactly-does-the-callstack-work