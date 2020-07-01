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
// If aString is null, you get a NullPointerException here
Optional<String> optional = Optional.of(aString);
```
3. Create an Optional with an object that could potentially be null
```
// If the string is null an empty Optional is created (no NullpointerException here)
Optional<String> optional = Optional.ofNullable(aStringThatIsPotentiallyNull);
```

### Retrieve the value that is wrapped by an Optional
You can get values that are wrapped by an Optional in several ways. Also you can check if an Optional contains a value 
or not:  
1. `get()`: Will return value in case it is present. Will throw *NoSuchElementException* in case value is not present.
```
    // Will return the String passed with the optional if present. 
    // Otherwise will throw a NoSuchElementException
    public String unpackOptionalWithGet(Optional<String> optional) {
        return optional.get(); 
    }
```
2. `orElse(T other)`: Will return value in case it is present. Will return `other` value provided in case value is not 
present.
```
    // Will return the String passed with the optional if present. 
    // Otherwise will return "Some Standard String"
    public String unpackOptionalWithOrElse(Optional<String> optional) {
        return optional.orElse("Some Standard String");
    }
```
3. `orElseGet(Supplier<? extends T> supplier)`: Will return value in case it is present. Will return the result of the
of the Supplier function.
```
    // Will return String passed with the optional if present.  
    // Otherwise will return "This is some String computed with a complex logic: Hello there!"
    public String unpackOptionalWithOrElseGet(Optional<String> optional) {
        return optional.orElseGet(() -> {
            // Add here some fancy logic here
            String a = "Hello";
            String b = "there!";
            String result = String.format("This is some String computed with a complex logic: %s %s", a, b);
            return result;
    });
}
```
4. `orElseThrow(Supplier<? extends Throwable> exceptionSupplier)`: Will return value in case it is present. Will the 
exception that is provided by the Supplier function.
```
    // Will return String passed with the optional if present. 
    // Otherwise will throw an IllegalArgumentException with the message "Please provide a non null value"
    public String unpackOptionalWithOrElseThrow(Optional<String> optional) {
        return optional.orElseThrow(() -> new IllegalArgumentException("Please provide a non null value"));
    }
```
5. `isPresent()`: Returns *true* if value is present, otherwise returns *false*
```
    public boolean checkIfValueIsPresent(Optional<String> optional) {
        return optional.isPresent();
    }
```

### Retrieve values from nested objects
For more complex cases, like retrieving some value from a nested object in a safe way the Optional's `map` and `flatMap`
methods can be used:
* The `map` method applies a mapper function to the value that is inside the Optional and returns the value wrapped
again in an Optional. If there is no value, then an empty Optional is returned
* The `flatMap` method has an additional step. It first unwraps the value inside the Optional and then applies the 
 the mapper function to it if the value does exist. Otherwise it also returns an empty Optional. The `FlatMap` method is
 handy when you are dealing with fields that are already wrapped by an Optional.  
 
Let's have some examples to demonstrate the methods:  
Assume we have a `Customer` class that has two fields: `String firstName` which is a string and 
`Optional<String> lastName` which is an Optional wrapping a string.
```
public class Customer {
    private String firstName;
    private Optional<String> lastName;

    public String getFirstName() {
      return firstName;
    }
    public Optional<String> getLastName() {
      return lastName;
    }
}
```
#### Map

If we want to access the `firstName` in a save way we can use the `map()` method:  

```
    public String useMapOnNonOptionalField(Customer customer) {
        Optional<Customer> customerOptional = Optional.ofNullable(customer);
        Optional<String> firstNameOptional = customerOptional.map(Customer::getFirstName);
        String firstName = firstNameOptional.orElse("First name is not available");
        return firstName;
    }
```
* The customer object is first wrapped in an Optional, as we don't know if customer is null
* Then we apply the `map()` method which returns the `firstName` field wrapped in an Optional
* Then we use the `orElse()` method to retrieve the `firstName`.

Now we want to get the `lastName` of the customer. Remember, this field is already an Optional of type String. What
happens when we use the `map()` method here?

```
    public String useMapOnOptionalField(Customer customer) {
        Optional<Customer> customerOptional = Optional.ofNullable(customer);
        Optional<Optional<String>> lastNameOptionalOfOptional = customerOptional.map(Customer::getLastName);
        Optional<String> lastNameOptional = lastNameOptionalOfOptional.get();
        String lastName = lastNameOptional.orElse("Last name is not available");
        return lastName;
    }
```
Here we would have an additional step as the `map()` method wraps the result into an Optional. In this case this 
would result in an Optional of an Optional. For a better understanding the steps above explained:
* The customer object is first wrapped in an Optional, as we don't know if customer is null
* Then we apply the `map()` method which returns the `lastName` field wrapped in an Optional. As the field was already
an Optional before, it is now an Optional of an Optional
* We apply the `get()` method to retrieve the inner Optional which holds the `lastName`
* Then we apply the `orElse()` method to get the `lastName` string as a result  

As you see it is quite of an overhead when using the `map()` method on fields that are already wrapped in an Optional.
A better solution for that is to use the `flatMap()` method. 

#### FlatMap
//TODO: FlatMap



```
    // Will return the 'country' field of the customer if customer and address and country are not null.
    // Otherwise returns the string "Country is null"
    public String avoidNullPointerWithOptionals(Customer customer) {
        Optional<Customer> customerOptional = Optional.ofNullable(customer);
        String country = customerOptional
                .map(Customer::getAddress)
                .map(Customer.Address::getCountry)
                .orElse("Country is null");
        return country;
    }
``` 
Without using Optional the same logic would result in some ugly nested if statements:
```
    public String avoidNullPointerWithoutOptionals(Customer customer) {
        if (customer != null && customer.getAddress() != null && customer.getAddress().getCountry() != null) {
            Customer.Address address = customer.getAddress();
            if (address != null) {
                String country = address.getCountry();
                if (country != null) {
                    return country;
                }
            }
        }
        return "Country is null";
    }
```