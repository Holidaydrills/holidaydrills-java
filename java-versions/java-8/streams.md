# Streams

Java streams \([java.util.stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)\) are the second fundamental feature apart from [lambda expressions](lambda-expressions.md) that were introduced with Java 8.

 **A stream is a sequence of elements supporting sequential and parallel aggregate operations**

Streams provide you an alternative way to iterate over collections while applying operations to the items of the collection. Streams are tightly coupled with [lambda expressions](lambda-expressions.md) which makes it possible to write concise and readable code. Furthermore Java streams provide methods that enable you to process the items of a collection in a parallel manner.

Streams have the following characteristics:

* Streams do not store data as collections so but it processes elements from a source with help of a pipeline of operations.
* Operations which are done on a Stream do not modify the source but produce a new result
* Many operations on a stream are lazy \(e.g. intermediate operations are lazy\), that means not all of the 

  elements need to be visited if not necessary \(e.g. when the first element matching to some predicate should be found, no further 

  elements will be visited after this element was found\)

* Streams can possibly be infinite. There are terminal operations which allow to complete a stream like `findAny()`
* A stream is consumed, that means that each element in a stream can be only visited once. In order to visit the elements 

  of a stream again a new stream has to be generated

## Examples

You can find all the examples from this tutorial in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8) in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/streams) directory.

## What is it good for?

* You want to loop over the items in a collection
* You want to apply operations to the items in a collection while looping over the collection, e.g.
  * filter 
  * sort 
  * aggregate \(e.g. sum\)
  * count 
  * map \(convert the items into an output of another form\)
  * ...
* You want to process the items within a collection in a parallel manner
* You want to achieve the above mentioned points with concise and readable code
* In general if you want to support functional-style operations on the elements of a stream. A typical example would be 

  map-reduce \(which means transforming the elements of a stream and reduce them to one output\).

## How does it work?

### Old way vs. new way

Let's have a first example in order to show how the stream api can be applied instead of an iteration with a loop. What we want to achieve is to print all strings from a list.  
First let's have a look how it would look with a for loop:

```java
    //Prints "Hello how are you? "
    public void iterateWithLoop() {
        List<String> input = List.of("Hello", "how", "are", "you?");
        for(String word : input) {
            System.out.println(word);
        }
    }
```

When using the stream api the same would look like this:

```java
    //Prints "Hello how are you? "
    public void useStream() {
        List<String> input = List.of("Hello", "how", "are", "you?");
        input.stream().forEach(word -> System.out.println(word));
    }
```

Here you can see how to create a stream and apply methods to it:

* First we call [stream\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) on the collection 

  in order to create a stream

* Then we call [forEach\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEach-java.util.function.Consumer-) 

  on the stream which takes a [Consumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) as a 

  parameter \(a Consumer is a functional interface which takes one input parameter and returns void. You can find more information 

  on lambda expressions and functional interfaces in the [Lambda Expressions](lambda-expressions.md) 

  chapter\).  

Let's have a look at another example where we get a list with integers and want to get a list with the same integers multiplied by 2.  
We loop over the list and multiply each item with 2. This is how we would do it with a loop:

```java
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

```java
    // Returns a list (2,4,6,8,14)
    public List<Integer> multiplyByTwoWithStream() {
        List<Integer> input = List.of(1,2,3,4,7);
        List<Integer> result = input.stream().map(number -> number * 2).collect(Collectors.toList());
        return result;
    }
```

* We first call [stream\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) on the List in 

  order to create a stream

* Then we call [map\(Function&lt;? super T,? extends R&gt; mapper\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) 

  on the stream. The [map\(Function&lt;? super T,? extends R&gt; mapper\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) 

  method takes the functional interface [Function](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html) 

  as a parameter, and returns a stream of elements that result by applying the passed function. In this case the function is 

  taking a number as argument and returning the number multiplied by 2 \(`number -> number * 2`\). If you struggle with functional 

  interfaces and lambda expression have a look at the [Lambda Expressions](lambda-expressions.md) chapter. 

### Stream operations

When working with streams there are actually always three different kinds of operations that are possible:  
1. The stream is created by calling the [stream\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#stream--) method on a collection. This method returns a stream of the elements that are within the collection. 2. Optionally there can be a chain \(one or more\) methods applied to the stream that again return a stream which can be processed further. Some examples are:

* [map\(Function&lt;? super T,? extends R&gt; mapper\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#map-java.util.function.Function-) which 

  applies a function to a stream and returns a stream of elements that result from applying that function. \(There are also map 

  methods with pre defined types of stream that are returned, e.g. if you want a stream of \)

* [sorted\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#sorted--) which returns a stream 

  with the elements in sorted order

* [filter\(Predicate&lt;? super T&gt; predicate\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#filter-java.util.function.Predicate-) 

  which returns a stream of elements that match a given predicate

* ...
  1. Terminal operations that don't return a stream as a result \(but can return something else\), e.g.
* [forEach\(Consumer&lt;? super T&gt; action\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#forEach-java.util.function.Consumer-) 

  which performs an operation on each element of the stream and returns nothing

* [toArray\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#toArray--) which returns an array 

  of the elements of the stream

* [count\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#count--) returns the count of the 

  elements of the stream

* ... 

### Parallel processing of streams

In cases where the order of processing the elements within a stream doesn't matter you can use [parallelStream\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--). As the name already reveals this method returns a \(possibly\) parallel stream which can improve the runtime of your code. Here is an example of how to use a [parallelStream\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html#parallelStream--):

```java
    public void processParallel() {
        List<Integer> input = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
        // Prints the numbers in a non predictable way as they're processed in parallel.
        // One possible output could be "7 12 18 11 20 6 19 15 17 14 9 16 3 10 2 8 1 5 4 13 "
        input.parallelStream().forEach(number -> System.out.print(number + " "));
    }
```

The following steps are performed in the code above:

* We create a parallel stream from the `input` list
* Each element of the list is printed to the console  

If you run this code several times it is highly possible that you get a different result for each run. This you can also see by the sample result which was produced when testing the code.  
As parallel stream is created the sequence of processing is not predictable. So the possibly better runtime has the trade of of not knowing the exact order of execution.

### Collecting streams

Most of the time you want to have the results of your stream operations stored in some way. Therefore the stream api provides [Collectors](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html) that provide reduction operations like accumulating elements into collections, summarizing elements based on some criteria, grouping elements based on on some criteria and more.  
Let's have some examples which show how Collectors work:

#### Collecting elements in a list \(that example we already know\):

```java
    public List<Integer> multiplyByTwoWithStream() {
        List<Integer> input = List.of(1,2,3,4,7);
        List<Integer> result = input.stream().map(number -> number * 2).collect(Collectors.toList());
        return result;
    }
```

#### Collecting elements in a map:

```java
    public void collectInMap() {
        // Prepare list of data
        List<Book> books = List.of(
                new Book("9780062316097", "Sapiens - A Brief History of Humankind", "Yuval Noah Harari"),
                new Book("9780141033570", "Thinking, Fast and Slow", "Daniel Kahneman"),
                new Book("9780141983769", "Why We Sleep", "Matthew Walker"));

        // Stream the list and collect in a map
        Map<String, Book> UuidToPerson = books
                .stream()
                .collect(Collectors.toMap(Book::getIsbn, Function.identity()));

        // Print values of the map
        for(Map.Entry<String, Book> entry : UuidToPerson.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getTitle());
        }
    }
```

#### Grouping elements:

```java
    public Map<String, List<Book>> groupBooksByAuthor() {
        List<Book> books = List.of(
                new Book("Yuval Noah Harari", "Sapiens - A Brief History of Humankind"),
                new Book("Yuval Noah Harari", "Homo Deus"),
                new Book("Yuval Noah Harari", "21 Lessons for the 21st Century"),
                new Book("Daniel Kahneman", "Thinking, Fast and Slow"),
                new Book("Daniel Kahneman", "Heuristics and Biases"),
                new Book("Matthew Walker", "Why We Sleep")
        );

        // The classification function is book.getAuthor().
        // The result of the classification function is used as key for the resulting map.
        Map<String, List<Book>> authorToBooks = books.stream().collect(Collectors.groupingBy(Book::getAuthor));

        return authorToBooks;
    }
```

In the example above the following happens:

* First a list of books is created containing author and title called `books`
* `books` is the source for the stream that is created
* The reduction operation [collect\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#MutableReduction) 

  is called on the stream

  * `collect()` is a reduction operation because it accumulates elements based on a given function into a container

* We use the [Collectors](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html) [groupingBy\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-) 

  method to tell that the author should be used as a key for the resulting map

* The result is a Map that contains the author and a list of the books belonging to the author as key-value pairs. The result would 

  look something like this:

  ```text
  Map[
    key: "Yuval Noah Harari", 
    value: List[Book("Yuval Noah Harari", "Sapiens - A Brief History of Humankind"), Book("Yuval Noah Harari", "Homo Deus"), Book("Yuval Noah Harari", "21 Lessons for the 21st Century")],
    key: "Daniel Kahneman", 
    value: List[Book("Daniel Kahneman", "Thinking, Fast and Slow"), Book("Daniel Kahneman", "Heuristics and Biases")],
    key: "Matthew Walker", 
    value: List[Book("Matthew Walker", "Why We Sleep")
  ]
  ```

### Summing numbers

In some cases you want to sum the values of elements. There are different ways to do so.

#### Using [java.util.stream.Collectors](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html):

```java
    public double getSumOfBookPricesWithCollector() {
        List<Book> books = List.of(
                new Book("Sapiens - A Brief History of Humankind", 19.00),
                new Book("Thinking, Fast and Slow", 20.00),
                new Book("Why We Sleep", 21.00));

        double sumOfBookPrices = books.stream().collect(Collectors.summingDouble(Book::getPrice));

        // Will return 60.00
        return sumOfBookPrices;
```

* The Collectors class provides also a [summingInt\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#summingInt-java.util.function.ToIntFunction-) 

  and a [summingLong\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#summingLong-java.util.function.ToLongFunction-) 

  method.

### Using reduction function directly on the stream:

```java
    public double getSumOfBookPricesPrices() {
        List<Book> books = List.of(
                new Book("Sapiens - A Brief History of Humankind", 19.00),
                new Book("Thinking, Fast and Slow", 20.00),
                new Book("Why We Sleep", 21.00));

        double sumOfBookPrices = books.stream().mapToDouble(Book::getPrice).sum();

        return sumOfBookPrices;
    }
```

* There is also a [mapToInt\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#mapToInt-java.util.function.ToIntFunction-) 

  and a [mapToLong\(\)](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#mapToLong-java.util.function.ToLongFunction-) 

  method which you can use to produce Integer and Long streams which you can sum up then



