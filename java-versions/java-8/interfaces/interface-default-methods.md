# Interface - Default Methods

With Java 8 it is possible to define not only abstract methods in interfaces, but you can also provide methods with a default implementation.

#### How does it work?

It's quite simple: you just need to use the `default` keyword in the method declaration and provide an implementation as you would usually do in a Java class.

```text
public interface FancyInterface {

    default String sayHello() {
        System.out.println("Hi there");
    }

}
```

That's actually it. Now this method can be called through the instance of an implementing class even if this method is not implemented by that class:

```text
public class FancyInterfaceImpl implements FancyInterface{
    // Nothing here, still you could call the 'sayHello()' method e.g. from another class
}

public class AnotherClass {
    public void callFancyMethod() {
        MyFancyInterfaceImpl fancyInstance = new MyFancyInterfaceImpl();
        fancyInstance.sayHello(); // Will print 'Hi there'
    }
}
```

If the class that inherits from the `FancyInterface` implements the `sayHello` method then this implementation will be used instead of the default one:

```text
public class FancyInterfaceImpl implements FancyInterface{
    // Now the sayHello method is imeplemented in the concrete class
    public void sayHello() {
        System.out.println("Hello everyone!");
    }
}

public class AnotherClass {
    public void callFancyMethod() {
        MyFancyInterfaceImpl fancyInstance = new MyFancyInterfaceImpl();
        fancyInstance.sayHello(); // Will print 'Hello everyone!' instead of 'Hi there'
    }
}
```

### Why should I use it?

Suppose you provide an interface that is implemented by a lot of other classes. Now you want to add additional feature to your interface and you would do that by adding another method.  
Imagine what would happen if you just add an additional abstract method to the interface. Every implementing class would need to adopt by implementing that additional method.  
Luckily you can avoid that by declaring the new method as a `default` one and provide an implementation right in your interface. All classes implementing that interface can now stay unchanged and just use the default implementation you provided. As soon as they need or want to have an own implementation of that method they could just implement is \(as in our second example above\).

### Good to know

#### What happens when interfaces with default implementations get extended?

When you extend an interface that contains a default implementation of a method there are three possibilities: 

1. You just don't mention the method at all: Then the default implementation is still valid 
2. You declare the default method in the extending interface \(that means you provide an abstract method with the same name\): That makes the method abstract so that an implementing class must provide an implementation. 
3. You provide a new default implementation: That way the default implementation from the extending interface becomes valid

Example 1: You don't mention the default method

```text
public interface FancyInterface {

    default String sayHello() {
        System.out.println("Hi there");
    }

}

public interface EvenMoreFancyInterface extends FancyInterface {
    // Nothing here. The original default implementation is still valid.
}

public class EvenMoreFancyInterfaceImpl implements EvenMoreFancyInterface {
    // The sayHello method isn't implemented so the original default implementation will be used.
}

public class AnotherClass {
    EvenMoreFancyInterfaceImpl fancyInterfaceImpl = new EvenMoreFancyInterfaceImpl();
    fancyInterfaceImpl.sayHello(); // Will print "Hi there"
}
```

Example 2: You declare the default method in the extending interface

```text
public interface FancyInterface {

    default String sayHello() {
        System.out.println("Hi there");
    }

}

public interface EvenMoreFancyInterface extends FancyInterface {
    // No you declare the sayHello method without providing a default imeplementation.
    String sayHello();
}

public class EvenMoreFancyInterfaceImpl implements EvenMoreFancyInterface {
    // As it is now an abstract method you have to implement it.
    public String sayHello() {
        System.out.println("Hello over there!");
    }
}

public class AnotherClass {
    EvenMoreFancyInterfaceImpl fancyInterfaceImpl = new EvenMoreFancyInterfaceImpl();
    fancyInterfaceImpl.sayHello(); // Prints "Hello over there!" as it is not using the default implementation anymore.
}
```

Example 3: You provide a new default implementation

```text
public interface FancyInterface {

    default String sayHello() {
        System.out.println("Hi there");
    }

}

public interface EvenMoreFancyInterface extends FancyInterface {
    // Here a new default implementation is provided.
    default String sayHello() {
        System.out.println("Hey!");
    }
}

public class EvenMoreFancyInterfaceImpl implements EvenMoreFancyInterface {
    // Nothing here. The default implementation of the second interface is valid.
}

public class AnotherClass {
    EvenMoreFancyInterfaceImpl fancyInterfaceImpl = new EvenMoreFancyInterfaceImpl();
    fancyInterfaceImpl.sayHello(); // Prints "Hey!" which is the default implementation of the second interface.
}
```

