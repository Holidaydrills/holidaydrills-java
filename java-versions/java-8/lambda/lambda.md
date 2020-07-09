# Lambdas
Lambda expressions (aka closures, aka anonymous methods) give a functional programming flavour to the Java language. With lambda expressions
it is possible to create functions that don't belong to any class or instance. You can pass these functions around
like parameters (e.g. pass them as method parameter) and reference them like they where objects.

[holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/methodreference).

## What is it good for?
* You want to pass functionality as an argument
* You want to tread a block of code as an object, store it in a variable, pass it around and execute it at some point 
(also known as tread code as data)
* You want to provide functionality without writing a full fledged method with access modifier and method name

## How does it work?
### Without lambda vs. with lambda
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
 
#### This is how we would approach this without lambdas
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

    public int makeSumTheOldWay(int valueOne, int valueTwo) {
        FancyCalculator calculator = new FancyCalculator() {
            @Override
            public int calculateTwoValues(int valueOne, int valueTwo) {
                return valueOne + valueTwo;
            }
        };
        return calculateTwoValues(valueOne, valueTwo, calculator);
    }

    public int makeProductTheOldWay(int valueOne, int valueTwo) {
        FancyCalculator calculator = new FancyCalculator() {
            @Override
            public int calculateTwoValues(int valueOne, int valueTwo) {
                return valueOne * valueTwo;
            }
        };
        return calculateTwoValues(valueOne, valueTwo, calculator);
    }
```
As you can see the implementation of the interface with anonymous inner classes is not very neat and takes quite a lot
of lines. The only thing we're actually interested in is the logic which takes only one line:
* For the sum it is `return valueOne + valueTwo;`
* For the product it is `return valueOne * valueTwo;`  

Let's see how we can improve that with lambda expressions!
### The anatomy of a lambda expression


