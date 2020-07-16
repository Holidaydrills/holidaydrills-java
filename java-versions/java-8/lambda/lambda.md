# Lambdas
Lambda expressions (aka closures, aka anonymous methods) give a functional programming flavour to the Java language. 
With lambda expressions it is possible to create functions that don't belong to any class or instance. We can pass these 
functions around like parameters (e.g. pass them as a method parameter) and reference them like they where objects.

## Examples
You can find all the examples from this tutorial in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8) 
in the [src/main/lambdaexpressions](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/lambdaexpressions) 
directory.


## What is it good for?
* You want to pass functionality as an argument
* You want to tread a block of code as an object, store it in a variable, pass it around and execute it at some point 
(also known as tread code as data)
* You want to provide functionality without writing a full fledged method with access modifier and method name

## How does it work?
### Lambda vs. no lambda
First let's see an example where we convert the same feature from a non-lambda implementation to a lambda 
implementation.  
Let's assume we want to implement a fancy calculator that takes two integers and does some calculation. We want to leave
it open what kind of calculation should be done. One calculation could be for example the sum of the two values, another
one could be to multiply the values and so on...  
We also want to be able to pass the implementation (so the way the two values are calculated) as a parameter to a 
method. We call that method `int calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator)`. It 
takes two integers and a FancyCalculator (which represents the implementation of the interface) as parameters.  
Because of this special requirement we decide to provide an interface with the name `FancyCalculator` that 
defines only one method `int calculateTwoValues(int valueOne, int valueTwo);` which takes to integers and returns a 
result which is also an integer. It is important that the interface defines exactly one abstract method! Why this is we 
will see later. This is our interface:  
```
public interface FancyCalculator {
    int calculateTwoValues(int valueOne, int valueTwo);
}
```
This interface can now be implemented in all different ways that fulfill the needs of the developer (e.g. creating a 
sum, a product or something else).   
 
#### Without lambda expressions
* We define a method `calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator)`
   * The parameters are the two values that we want to use for the calculation. The third parameter is the 
   implementation of the FancyCalculator which holds the logic of the operation that should be done.
* We define additional methods for each kind of operation that we want to perform with the two values. In our example 
this operations are to sum and to multiply the two values. The methods ... 
   * ... take two integers as parameters that represent the values we want to use for the calculation
   * ... implement the `FancyCalculator` interface as anonymous inner class (this could also be done in a 
   separate class. But as the interface has only one method to implement this is the way to go).
   * ... call our `calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator)` method 
   and pass the two values and the `FancyCalculator` implementation as parameters.   

```
    public int calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator) {
            return fancyCalculator.calculateTwoValues(valueOne, valueTwo);
    }

    // Will return 12
    public int makeSumTheOldWay() {
        FancyCalculator calculator = new FancyCalculator() {
            @Override
            public int calculateTwoValues(int valueOne, int valueTwo) {
                return valueOne + valueTwo;
            }
        };
        return calculateTwoValues(5, 7, calculator);
    }

    // Will return 35
    public int makeProductTheOldWay() {
        FancyCalculator calculator = new FancyCalculator() {
            @Override
            public int calculateTwoValues(int valueOne, int valueTwo) {
                return valueOne * valueTwo;
            }
        };
        return calculateTwoValues(5, 7, calculator);
    }
```
As you can see the implementation of the interface with anonymous inner classes is not very neat and takes quite a lot
of lines. The only thing we're actually interested in is the logic which takes only one line:
* For the sum it is `return valueOne + valueTwo;`
* For the product it is `return valueOne * valueTwo;`  

Instead of storing the implementation of the `FancyCalculator` interface in a variable we could also put it directly 
into the argument list. But still this is quite verbose: 
```
    // Will return 35
    public int makeProductTheOldWayImplementationInline() {
        return calculateTwoValues(5, 7, new FancyCalculator() {
            @Override
            public int calculateTwoValues(int valueOne, int valueTwo) {
                return valueOne * valueTwo;
            }
        });
    }
```

Let's see how we can improve that with lambda expressions!

#### With lambda expressions
* Again we have a method `calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator)` like above
* We define additional methods for each kind of operation that we want to perform with the two values. But this time, 
 instead of implementing the `FancyCalculator` interface with an anonymous inner class, we use lambda expressions  
 
```
    public int calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator) {
        return fancyCalculator.calculateTwoValues(valueOne, valueTwo);
    }

    // Will return 12. The lambda expression is stored in a variable before it is used.
    public int makeSum() {
        FancyCalculator sumCalculator = (a,b) -> a + b;
        return calculateTwoValues(5, 7, sumCalculator);
    }
    
    // Will return 35. 
    // The lambda expression is written directly in the parameter list. This is usually the way to go with lambdas
    public int makeProduct() {
        return calculateTwoValues(5, 7, (a,b) -> a * b);
    }

```

As you can see we replaced the anonymous inner class with a more simple expression which consists of the actual logic 
that we want to perform. Without all the boilerplate code we had to write when using anonymous inner classes.  
In the `makeProduct` method you see that the lambda is written inline. This is usually the way to go with lambdas. This 
results in readable code in contrast to implementing anonymous inner classes inline.  

Now that we saw how lambdas look like with a first example, we will go into the syntax details.

### Anatomy of lambda expressions
A lambda expression in its full form is already quite concise when comparing it to an anonymous inner class. But lambdas 
provide even shorter syntax in some cases. See below how a full fledged lambda expression can be striped down when some 
preconditions are met.

#### Full lambda expression
A full fledged lambda expression consists of:
* A parameter list enclosed brackets
* An arrow
* The logic of the expression enclosed in braces
```
(a, b) -> {
            String result = a + b;
            return result;
        };
```

#### Lambda with a single expression
In case the lambda expression has only one single expression, we can avoid the braces around it. In case we have a 
return value we can also leave out the `return` keyword. The lambda expression below is semantically the same as the 
one above:
```
(a, b) ->  a + b;
```

#### Lambda expression with only one parameter 
In case the lambda expression only takes one parameter we can even omit the brackets that enclose the parameter list.
So instead of `(a) -> System.out.prinlnt(a);`, we could write `a -> System.out.println(a);`

### What is a lambda expression? - Functional Interfaces
We've seen now some examples of lambda expression, compared them to anonymous inner classes and learned about the 
some details about the syntax. But what is a lambda expression actually?  
Here a short definition:
* A lambda expression is an anonymous method. That means a method without a name.
* A lambda expression is an implementation of a functional interface. A functional interface is an interface that 
defines at most one abstract method (is can however contain as many default methods as you like).  
 
Let's take the [FancyCalculator example](#Lambda-vs.-no-lambda) from the beginning. We defined an 
interface that contains only one abstract method `int calculateTwoValues(int valueOne, int valueTwo);`. This method is 
implemented once as a sum of two values and once as a product of two values:
```
(a,b) -> a + b; // Sum
(a,b) -> a * b; // Product
```
Let's have again a look at the two points mentioned above:
* A lambda expression is an anonymous method:
   * We can see that the expressions have no name like usually a method would have. (Compare it to the examples above 
   where we used anonymous inner classes to implement the FancyCalculator. There you can see that the methods have names)
* A lambda expression is an implementation of a functional interface
   * Because a lambda expression has no method name we cannot specify which method of an interface it should implement. 
   That's why a lambda expression can only implement *Functional Interfaces*. These have only one abstract method, so the 
   compiler knows exactly what method is implemented... because there is only one.  
   * It is important that the lambda expression has the same return type and parameter list as the method that it 
   implements. E.g. in our example above the method in FancyCalculator is `int calculateTwoValues(int valueOne, int valueTwo);` 
   taking two integers and returning an integer. Our lambda expressions do the same, they take to integers (1,b) and 
   return an integer.

It is not possible to use a lambda expression as an implementation of an interface with more than one abstract method. 
Let's assume the FancyCalculator would define more than one abstract method:  

```
public interface FancyCalculator {
    int calculateTwoValues(int valueOne, int valueTwo);
    void someOtherMethod(String input);
}
```

If we now would try to do the following, we would get a compilation error because the compiler would not know what method 
is implemented by our lambda expression: 

```
public int calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator) {
        return fancyCalculator.calculateTwoValues(valueOne, valueTwo);
    }

    // Will return 12. The lambda expression is stored in a variable before it is used.
    public int makeSum() {
        // Compiler does not know if this is an implementation of calculateTwoValues or someOtherMethod
        FancyCalculator sumCalculator = (a,b) -> a + b; 
        return calculateTwoValues(5, 7, sumCalculator);
    }
```

Java 8 provides an annotation to mark an interface as a functional interface. If you add the annotation the compiler will 
give you an error in case you define more than one abstract method in the interface:
```
@FunctionalInterface
public interface FancyCalculator {
    int calculateTwoValues(int valueOne, int valueTwo);
}
```

## Good to know
When we look at the `FancyCalculator` interface from the examples above we can see that the method which it defines 
`int calculateTwoValues(int valueOne, int valueTwo);` takes two integers and returns one. It doesn't tell anything about 
what happens (or what should happen) in the implementation. Therefore we also could have named the interface and the 
method that it defines something like:
```
@FunctionalInterface
public interface TakeTwoIntegersAndReturnOne {
    int doSomeLogic(int valueOne, int valueTwo);
}
``` 
The implementation of this interface would still look the same as above `(a, b) -> a + b`. So actually it would even be 
better to choose a more generic name as it makes the interface more open to different implementations.  
Despite the example above we could also provide a lot of other functional interfaces like e.g.:
* One that takes no input parameter and returns nothing
* One that takes one input parameter and returns nothing
* One that takes two input parameters and returns nothing
* One that takes an input parameter and returns something
* ...    

Luckily Java provides a lot of ready to use functional interface for all these cases. Have a look at the 
[java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) 
package. Let's see if we can find a substitute for our `FancyCalculator` there. The package provides for a example an 
interface that is called `BiFunction<T,U,R>` which has the following description:
* Represents a function that accepts two arguments and produces a result
* T - the type of the first argument to the function
* U - the type of the second argument to the function
* R - the type of the result of the function  
* It has one method `apply(T t, U u)` which applies this function to the given arguments t and u.  

That looks pretty much like our FancyCalculator's `int doSomeLogic(int valueOne, int valueTwo)` method. So let's try it 
out: 
```
    public int calculateTwoValuesWithBiFunction(int valueOne, int valueTwo, BiFunction<Integer, Integer, Integer> myFancyFunction) {
        return myFancyFunction.apply(valueOne, valueTwo);
    }

    // This will return 35
    public int makeProductWithBiFunction() {
        return calculateTwoValuesWithBiFunction(5, 7, (a,b) -> a * b);
    }
```

And it actually works. As before we pass in two integers and return one. When you compare it to the usage of the 
`FancyCalculator` it looks quite the same: 
```
    public int calculateTwoValues(int valueOne, int valueTwo, FancyCalculator fancyCalculator) {
        return fancyCalculator.calculateTwoValues(valueOne, valueTwo);
    }
    
    // Will return 35.
    public int makeProduct() {
        return calculateTwoValues(5, 7, (a,b) -> a * b);
    }
```
The difference we have here is that we need to provide the types of our arguments and return values to the `BiFunction<T,U,R>`: 
* We tell it that the T (the first argument) is an Integer
* We tell it that the U (the second argument) is an Integer
* And we tell it that R (the return value) is also of type Integer  
So actually we should use the wrapper type of int in the example above. But Java does the work for us by wrapping and 
unwrapping the primitive types as needed. Still, if we would to it, it would look like this: 
```
    public Integer calculateTwoValuesWithBiFunction(int valueOne, int valueTwo, BiFunction<Integer, Integer, Integer> myFancyFunction) {
        return myFancyFunction.apply(valueOne, valueTwo);
    }

    // This will return 35
    public Integer makeProductWithBiFunction() {
        return calculateTwoValuesWithBiFunction(5, 7, (a,b) -> a * b);
    }
```

Let's take one additional example to see how we could utilize the pre defined functional interfaces: Let's say we want 
to pass a string and print it to the console. So actually we would need a method that:
* Takes one argument which is in this case a string
* Returns nothing   

Looking at the list of functional interface in the [java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) 
package there is a `Consumer<T>` interface that looks exactly what we need. It has the following definition:
* Represents an operation that accepts a single input argument and returns no result
* T - the type of the input to the operation  
* Method `accept(T t)` performs this operation on the given argument t.

So let's try it out:
```
    public void testConsumer(String someone, Consumer<String> consumerFunction) {
        consumerFunction.accept(someone);
    }

    public void printSomethingNice() {
        Consumer<String> myConsumer = inputString -> System.out.println(String.format("%s is my best friend", inputString));
        // Will print "Franky is my best friend"
        testConsumer("Franky", myConsumer);

        // Will print "Hey Franky, do you want some ice cream?"
        testConsumer("do you want some ice cream?", inputString -> System.out.println(String.format("Hey Franky, %s", inputString)));
    }
```
* In the first example we first store the lambda expression in a variable `myConsumer` and then pass it 
(just for illustration). The lambda expression takes the parameter which is in this case the string "Franky", puts it 
before " is my best friend" and prints it. The `testConsumer` executes the lambda expression at the end by calling the 
`accept` method on it. This actually prints the string to the console.
* In the second example we do the same except that we don't store the lambda expression in a variable, but pass define 
it inline as a parameter.  

There are a lot more pre defined functional interfaces you can use for all the different use cases that you come across.
And in case you don't find a fitting interface just define your own like we did at the beginning of this tutorial. So go 
ahead and try it out.






