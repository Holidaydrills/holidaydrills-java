# Interface - Static Methods

With Java 8 it is possible to define static methods in interfaces.

#### How does it work?

Just declare a method as static and provide an implementation as you would do in a Java class:

```text
public interface MyFancyInterface {
    static void myFancyStaticMethod() {
        System.out.println("This is a static interface method");
    }
}
```

You can call such static interface methods only within the interface itself or through the interface. What that means is, that you can't call a static interface method through an instance of a class that is implementing the interface:

```text
public class myFancyClass {

    public void doSomethingFancy() {
        //As you see below, you call the method through the interface itself. 
        //You don't need a class that implements the interface in order to call it.
        //In fact, it's even not possible to call the method through the instance of a class implementing the interface.
        MyFancyInterface.myFancyStaticMethod();
    }

}
```

#### Why should I use it?

* Static interface methods can be used to provide utility methods
* When you don't want the implementing class to override a method a static method can be used

#### Use case

You're planning to open your first online shop. You've already implemented the product catalogue, some fancy search capabilities and the user management. Now you got to the point where you create the checkout process. You've already implemented the checkout form and the validation for the user input. E.g. in case someone enters a zip code that isn't valid the user gets an error. What's still missing is a way to provide a consistent error message.

You think about the problem and come to the conclusion that a static interface method could be the right thing for that because:

* it gives you a way to provide a common way how error message are created 
* it avoids that implementing classes override the method
* it provides a utility method that can be used by any class that needs it

You can explore the sample code on this use case in the [holidaydrills-java8 repository](https://github.com/dholde/holidaydrills-Java8) under the [src/com/holidaydrills/interfaces/webshopexample ](https://github.com/dholde/holidaydrills-Java8/tree/master/src/com/holidaydrills/interfaces/webshopexample)directory. There you will find:

> * a [FormValidator](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/FormValidator.java) interface which contains a static utility method which provides an error message. 
> * a [WebshopCheckout](https://github.com/dholde/holidaydrills-Java8/blob/master/src/com/holidaydrills/interfaces/webshopexample/WebShopCheckout) class which which [calls the static method](https://github.com/dholde/holidaydrills-Java8/blob/51f3517984e303b1cae0697deb032303a71f1cd5/src/com/holidaydrills/interfaces/webshopexample/WebShopCheckout.java#L42) in order to return a proper error message
> * Some other interfaces which are not interesting for this specific example

