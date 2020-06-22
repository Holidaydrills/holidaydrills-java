# Optionals
If you struggle with Null Pointer Exceptions (NPE) or you were annoyed by verbose null checks then you will
like the new `Optional` class that was introduced in Java 8. It is a wrapper class that can contain an object 
and behaves in different ways dependent on if the object is null or not. If it exists, then the wrapper returns
it, if it is null then an `Optional` provides you different ways how to handle it.

## What is it good for?
In general Optionals provide capabilities to protect your code from NPE and which simplify the handling 
of null pointers. These are some situations where the use of `Optional` would make sense:
* You struggle with NPE in your code and you want to make your code more stable in that regard
* You have a lot of null-checks in your code which are quite verbose and hard to read
* You provide an API where specific values can be null by design. You want to make sure that it is transparent
 to consumers of that API  
 
## How does it work?
### Define/Create an Optional
There are several ways how to create an optional:
1. Create an empty Optional, that does not contain any object
```
Optional<String> optional = Optional.empty()
```
2. Create an Optional containing an object
```
String aString = "Hello there";
Optional<String> optional = Optional.of(aString);
```
3. Create an Optional with an object that could potentially be null
```
String aString = getStringThatPotentiallyIsNull();
Optional<String> optional = Optional.ofNullable(aString);
```

### Retrieve the value that is wrapped by an Optional