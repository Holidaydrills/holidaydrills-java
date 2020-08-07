# Local Variable Type Inference

In Java 11 local variables can be declared with the `var` keyword instead of using the type of the variable. The compiler 
will automatically infer the type of the variable from the initializer on the right side.

## Examples
You can find all the examples from this tutorial in the [holidaydrills-java11 repository](https://github.com/Holidaydrills/holidaydrills-Java8) 
in the [LocalVariableSyntax.java](https://github.com/Holidaydrills/holidaydrills-Java11/tree/master/src/main/java/com/holidaydrills/LocalVariableSystax.java)
class.

## What is it good for?
* You can use the `var` syntax for local variable declaration (e.g. in methods or lambda expressions)
* You want to emphasize the name of the variable instead of the type (in cases where the type can be clearly read from the 
context)
* You have to declare several local variables and want to make it more readable
* You can use it for loop indexes as the type is anyways clear (it's int)

## How does it work?
### The var syntax
First let's see an example how the `var` syntax looks like. Let's say we have a method where we need to initialize a bunch 
of variables (here we ignore the fact, that having a bunch of variables in a method is a strong indicator for bad code)
```Java
public void myFancyMethod() {
    String myString = "Hello";
    Map<String, String> myMap = new HashMap<>();
    Integer[] myArray = new Integer[10];
    double myDouble = 0.0;
    double mySecondDouble = 0.0;

    // Do something with all this variables
}
```
This looks a little messy. In this situation clearly the variable names are more important than the types (as we see the 
types from the right hand side initializer).  
With the `var` syntax it would look like this:
```Java
public void myFancyMethodWithVar() {
    var myString = "Hello";
    var myMap = new HashMap<String, String>();
    var myArray = new Integer[10];
    var myDouble = 0.0;
    var mySecondDouble = 0.0;

    // Do something with all this variables
}
```
This looks already much cleaner than the example before. Even there is no type provided the compiler will infer the types 
of the variables by looking at the initializer at the right side and infer the type for us.


## Good to know

  ```
  var myString = "Hello";
  var myMap = new HashMap<String, String>();
  var myArray = new Integer[10];
  var myDouble = 0.0;
  var mySecondDouble = 0.0;
  
  // Instead of:
  String myString = "Hello";
  Map<String, String> myMap = new HashMap<>();
  Integer[] myArray = new Integer[10];
  double myDouble = 0.0;
  double mySecondDouble = 0.0;
  ```