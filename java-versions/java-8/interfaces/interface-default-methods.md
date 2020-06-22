# Interface - Default Methods

With Java 8 it is possible to define not only abstract methods in interfaces, but you can also provide methods with a default implementation.  
You can find the examples used below in the [holidaydrills-java8 repository](https://github.com/dholde/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/interfaces/simpleexample).

## How does it work?

It's quite simple: you just need to use the `default` keyword in the method declaration and provide an implementation as you would usually do in a Java class.

```java
public interface FancyInterface {
    default String sayHello() {
        return "Hi there";
    }

    default String sayHelloAgain() {
        return "Hi there";
    }

    default String sayGoodBye() {
        return "Goodbye my friend!";
    }
}
```

That's actually it. Now this method can be called through the instance of an implementing class even if this method is not implemented by that class:

```java
public class FancyInterfaceImpl implements FancyInterface {
    // sayHello() is not implemented here. Still it can be called on an instance of FancyInterfaceImpl
}

class AnotherClass {
    public void checkFancyInterfaceImpl() {
        FancyInterfaceImpl fancyInterfaceImplementation = new FancyInterfaceImpl();
        System.out.println(fancyInterfaceImplementation.sayHello()); // Will print "Hi there"
    }
}
```

If the class that inherits from the `FancyInterface` implements the `sayHello` method then this implementation will be used instead of the default one:

```java
public class FancyInterfaceImpl implements FancyInterface {
    // sayHelloAgain is overridden and will return now "Hello all! How are you?" instead of "Hi there"
    @Override
    public String sayHelloAgain() {
        return "Hello all! How are you?";
    }
}

class AnotherClass {
    public void checkFancyInterfaceImpl() {
        FancyInterfaceImpl fancyInterfaceImplementation = new FancyInterfaceImpl();
        System.out.println(fancyInterfaceImplementation.sayHelloAgain()); // Will print "Hello all! How are you?"
    }
}
```

## What is it good for?

* With interface default methods you can extend your existing interfaces without breaking the users of it.  
 (Remember: a class that implements an interface must also implement all methods of that interface that are abstract. By providing a default implementation the implementing class can just use the default implementation without the need to implement that method itself)

## Good to know

### What happens when interfaces with default implementations get extended?

When you extend an interface that contains a default implementation of a method there are three possibilities:

1. You just don't mention the method at all: Then the default implementation is still valid 
2. You declare the default method in the extending interface \(that means you provide an abstract method with the same name\): That makes the method abstract so that an implementing class must provide an implementation. 
3. You provide a new default implementation: That way the default implementation from the extending interface becomes valid

Example 1: You don't mention the default method

```java
public interface FancyInterface {
    default String sayHello() {
        return "Hi there";
    }
}

public interface EventMoreFancyInterface extends FancyInterface {
    // The method sayHello() is not overridden here, so the default implementation from FancyInterface is still valid
}

public class EvenMoreFancyInterfaceImpl implements EventMoreFancyInterface{
    // The method sayHello() is not implemented here, so the default implementation from FancyInterface is valid
}

class YetAnotherClass {
    public void checkEvenMoreFancyInterfaceImpl() {
        EvenMoreFancyInterfaceImpl evenMoreFancyInterfaceImplementation = new EvenMoreFancyInterfaceImpl();
        // Will print "Hi there" provided by FancyInterface
        System.out.println(evenMoreFancyInterfaceImplementation.sayHello());
    }
}
```

Example 2: You declare the default method in the extending interface

```java
public interface FancyInterface {
    default String sayHelloAgain() {
        return "Hi there";
    }
}

public interface EventMoreFancyInterface extends FancyInterface {
    // Here the default implementation is overridden by a new default implementation
    @Override
    default String sayHelloAgain() {
        return "I want to say hello again";
    }
}

public class EvenMoreFancyInterfaceImpl implements EventMoreFancyInterface{
    // The method sayHelloAgain() is not implemented here, so the default implementation from FancyInterface is valid
}

class YetAnotherClass {
    public void checkEvenMoreFancyInterfaceImpl() {
        EvenMoreFancyInterfaceImpl evenMoreFancyInterfaceImplementation = new EvenMoreFancyInterfaceImpl();
        // Will print "I want to say hello again" provided by EvenMoreFancyInterface
        System.out.println(evenMoreFancyInterfaceImplementation.sayHelloAgain());
    }
}
```

Example 3: You provide a new default implementation

```java
public interface FancyInterface {
    default String sayGoodBye() {
        return "Goodbye my friend!";
    }
}

public interface EventMoreFancyInterface extends FancyInterface {
    // Here the default implementation is overridden by an abstract method. That means that a class that implements
    // EventMoreFancyInterface has to provide an implementation.
    @Override
    String sayGoodBye();
}

public class EvenMoreFancyInterfaceImpl implements EventMoreFancyInterface{
    // Must be implemented here since EvenMoreFancyInterfaceImpl declares the method as abstract
    @Override
    public String sayGoodBye() {
        return "See you in a while, crocodile!";
    }
}

class YetAnotherClass {
    public void checkEvenMoreFancyInterfaceImpl() {
        EvenMoreFancyInterfaceImpl evenMoreFancyInterfaceImplementation = new EvenMoreFancyInterfaceImpl();
        // Will print "See you in a while, crocodile!" provided by EvenMoreFancyInterfaceImpl
        System.out.println(evenMoreFancyInterfaceImplementation.sayGoodBye());
    }
}
```

## Use Case

You're planning to open your first online shop in Finland. You've already implemented the product catalogue, some fancy search capabilities and the user management. But as you want to earn money you need some kind of checkout process. You think about what's needed and the first step would be to make sure, that customers provide their information in the correct form. So you decide to implement a validation for the checkout form. In order to keep the code clean and make it reusable in case you need to serve some other online shops with slightly different requirements you decide to have the following structure:

* A [FormValidator](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/FormValidator.java) interface which provides the generic methods that are valid across all forms in all online shops
* A [FormValidatorFIN](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/FormValidatorFIN.java) which provides an interface for your Finnish online shop \(later other country specific interfaces can follow\)
* The [FormValidatorFINImpl](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/FormValidatorFINImpl.java) class which implements the [FormValidatorFIN](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/FormValidatorFIN.java) interface
* The [WebShopCheckout](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/WebShopCheckout.java) class which is responsible for the whole checkout process. As one of the steps of the checkout process it performs the validation by using the 
* [FormValidatorFINImpl](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/CheckoutFormValidator.java)

