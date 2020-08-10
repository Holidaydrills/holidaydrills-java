# Local Variable Type Inference

In Java 11 local variables can be declared with the `var` syntax instead of using the type of the variable. The compiler 
will automatically infer the type of the variable from the initializer on the right side.  

## Examples
You can find all the examples from this tutorial in the [holidaydrills-java11 repository](https://github.com/Holidaydrills/holidaydrills-java11) 
in the [LocalVariableSyntax.java](https://github.com/Holidaydrills/holidaydrills-java11/blob/master/src/main/java/com/holidaydrills/LocalVariableSyntax.java)
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

### Reserved type name var
`var` is **not** a [keyword](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html) but a so called 
**reserved type name**. That means that with the introduction of `var` it is not allowed to have classes or interfaces named 
"var". Here some examples about what is allowed and what not:   

**var as a class name is not allowed**
```Java
class var { } // Compilation Error
      ^ 'var' is a restricted local variable type and cannot be used for type declarations
```   


**var as an interface name is not allowed**
```Java
interface var { } // Compilation Error
          ^ 'var' is a restricted local variable type and cannot be used for type declarations
```  


**var as package name is allowed**
```Java
package var;
```   

**var as method name is allowed**
```Java
public void var() {
    System.out.println("See, it is allowed to have var as method name");
}
```  
### Where to use var
You can use `var` in the following cases:  
**Local variable declaration with initializer:**
```Java
var myString = "Hello";
var myMap = new HashMap<String, String>();
```  
**Index variables in for-loops:**
```Java
for(var i = 0; i < 10; i++) {
    System.out.println("Print me ten times.");
}
```
**Enhanced for-loops:**
```Java
int[] myIntegers = {0,1,2,3,4,5,6,7,8,9};
for(var element : myIntegers) {
    System.out.println(element);
}
```
**In lambda expressions if the formal parameters are implicitly typed**
* A lambda expression has implicitly typed parameters, if the types of the parameters can be inferred 
   * In the example below the types a and b are implicitly typed by the types provided in the BiFunction declaration (a: Integer, b: String, return value: String)
* If you use `var` it has no effect at all, but is just a syntactical difference
```Java
BiFunction<Integer, String, String> myFunction = (a, b) -> String.format("This is the Integer: %s, and this is the String: %s)", a,b);
// You can write the same as above but with the var type
myFunction = (var a, var b) -> String.format("This is the Integer: %s, and this is the String: %s)", a,b);
```
* It is not allowed to have only a part of variables declared with `var`
* It is also no allowed to have a mix of `var` and manifest types
```Java
// Comiler Error: Cannot resolve symbol b
myFunction = (a, var b) -> String.format("This is the Integer: %s, and this is the String: %s)", a,b);

// Compiler Error: Cannot mix 'var' and explicitly typed parameters in lambda expression
myFunction = (Integer a, var b) -> String.format("This is the Integer: %s, and this is the String: %s)", a,b);
```

### Generics
-> To be done

## Good to know
### Restrictions
There are some circumstances where it is not possible to use the `var` syntax. The following  examples show some of them.  

#### Not possible without initializer
* The compiler uses the right hand side of the expression to infer the type of the variable
* That's why it is not possible to use var if there is no initializer at all
* Also `Null` is not possible as it is no type (hence the type of var cannot be inferred)
```Java
// Compiler Error: Cannot infer type: 'var' on variable without initialize
var houseNumber;

//Compiler Error: Cannot infer type: variable initializer is 'null'
var lastName = null;
```

#### Multiple variable definition not possible
* When using manifest types for type declaration it is possible to declare multiple variables like so: `int counterOne, counterTwo, counterThree = 0;`
* When using `var` this is not possible:
```Java
//Compiler Error: 'var' is not allowed in a compound declaration
var counterOne, counterTwo, counterThree = 0;
```

#### All situations where the compiler relies on the left hand side of the expression
In some situation the compiler looks at the left hand side of an expression in order to infer the type of the right hand side 
of the expression. Examples for that are:  

* In a lambda expression the compiler looks at the left hand side of the expression in order to infer the type of the expression
```Java
//This works as the compiler can infer the type of the lambda by looking at the left hand side
Function<String, String> myValidFunction = a -> a;

//Compiler Error: Cannot infer type: lambda expression requires an explicit target type
var test = a -> a;
```  

* When using method references the compiler looks at the left hand side of the expression to infer the target type. If there 
is no target type, then we get a compiler error:
```Java
// Works
BiFunction<Integer, Integer, Integer> myBifunction = Math::max;

//Compiler Error: Cannot infer type: method reference requires an explicit target type
//var anotherBiFunction = Math::max;
```
* When initializing an array the compiler also infers the type by looking at the left hand side of the expression:
```Java
// Works
int[] myArray = {1,2,3,4,5};

// Compiler Error: Array initializer is not allowed here
var anotherArray= {1,2,3,4,5};

// However this would work:
var yetAnotherArray= new int[]{1, 2, 3, 4, 5};
```
