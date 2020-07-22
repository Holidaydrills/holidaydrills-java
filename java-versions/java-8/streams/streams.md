# Java Streams
Java streams ([java.util.stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)) are 
the second fundamental feature apart from [lambda expressions](../lambda-expressions/lambda-expressions.md) 
that were introduced with Java 8.  

** A stream is a sequence of elements supporting sequential and parallel aggregate operations**  

Streams provide you an alternative way to iterate over collections while applying operations to the items of the 
collection. Streams are tightly coupled with [lambda expressions](../lambda-expressions/lambda-expressions.md) which 
makes it possible to write concise and readable code. Furthermore Java streams provide methods that enable you to process 
the items of a collection in a parallel manner.

## Examples
You can find all the examples from this tutorial in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8) 
in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/streams)
directory.

## What is it good for?
* You want to loop over the items in a collection
* You want to apply operations to the items in a collection while looping over the collection, e.g.
   * filter 
   * sort 
   * aggregate (e.g. sum)
   * count 
   * map (convert the items into an output of another form)
   * ...
* You want to process the items within a collection in a parallel manner
* You want to achieve the above mentioned points with concise and readable code
   
## How does it work?
### Old way vs. new way
Let's have a first example in order to show how the stream api can be applied instead of an iteration with a loop. What 
we want to achieve is to print all strings from a list.  
First let's have a look how it would look with a for loop:
```Java
    //Prints "Hello how are you? "
    public void iterateWithLoop() {
        List<String> input = List.of("Hello", "how", "are", "you?");
        for(String word : input) {
            System.out.println(word);
        }
    }
``` 
When using the stream api the same would look like this:
```Java
    //Prints "Hello how are you? "
    public void useStream() {
        List<String> input = List.of("Hello", "how", "are", "you?");
        input.stream().forEach(word -> System.out.println(word));
    }
```
Here you can see how to create a stream and apply methods to it:
* First we call [stream()](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) on the collection 
in order to create a stream
* Then we call [forEach()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEach-java.util.function.Consumer-) 
on the stream which takes a [Consumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) as a 
parameter (a Consumer is a functional interface which takes one input parameter and returns void. You can find more information 
on lambda expressions and functional interfaces in the [Lambda Expressions](../lambda-expressions/lambda-expressions.md) 
chapter).  

Let's have a look at another example where we get a list with integers and want to get a list with the same integers multiplied 
by 2.  
We loop over the list and multiply each item with 2. This is how we would do it with a loop: 
```Java
    // Returns a list (2,4,6,8,14)
    public List<Integer> multiplyByTwo() {
        List<Integer> input = List.of(1,2,3,4,7);
        List<Integer> result = new LinkedList<>();
        for(Integer number : input) {
            result.add(number * 2);
        }
        return result;
    }
```

And the following gives the same result by using a stream: 
```Java
    // Returns a list (2,4,6,8,14)
    public List<Integer> multiplyByTwoWithStream() {
        List<Integer> input = List.of(1,2,3,4,7);
        List<Integer> result = input.stream().map(number -> number * 2).collect(Collectors.toList());
        return result;
    }
```
* We first call [stream()](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) on the List in 
order to create a stream
* Then we call [map(Function<? super T,? extends R> mapper)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) 
on the stream. The [map(Function<? super T,? extends R> mapper)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) 
method takes the functional interface [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html) 
as a parameter, and returns a stream of elements that result by applying the passed function. In this case the function is 
taking a number as argument and returning the number multiplied by 2 (`number -> number * 2`). If you struggle with functional 
interfaces and lambda expression have a look at the [Lambda Expressions](../lambda-expressions/lambda-expressions.md) chapter. 

### General structure when applying a stream
When working with streams there are actually always three steps which are done:  
1. The stream is created by calling the [stream()](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) 
method on a collection. This method returns a stream of the elements that are within the collection.
2. Optionally there can be a chain (one or more) methods applied to the stream that again return a stream which can be 
processed further. Some examples are: 
   * [map(Function<? super T,? extends R> mapper)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) which 
   applies a function to a stream and returns a stream of elements that result from applying that function. (There are also map 
   methods with pre defined types of stream that are returned, e.g. if you want a stream of )
   * [sorted()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#sorted--) which returns a stream 
   with the elements in sorted order
   * [filter(Predicate<? super T> predicate)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#filter-java.util.function.Predicate-) 
   which returns a stream of elements that match a given predicate
   * ...
3. Terminal operations that don't return a stream as a result (but can return something else), e.g.
   * [forEach(Consumer<? super T> action)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEach-java.util.function.Consumer-) 
   which performs an operation on each element of the stream and returns nothing
   * [toArray()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#toArray--) which returns an array 
   of the elements of the stream
   * [count()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#count--) returns the count of the 
   elements of the stream
   * ... 
   






## Good to know