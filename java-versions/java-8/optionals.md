# Optionals

If you struggle with Null Pointer Exceptions \(NPE\) or you were annoyed by verbose null checks then you will like the new `Optional` class that was introduced in Java 8. It is a wrapper class that can contain an object and behaves in different ways dependent on if the object is null or not. If it exists, then the wrapper returns it, if it is null then an `Optional` provides you different ways how to handle it.

## What is it good for?

In general Optionals provide capabilities to protect your code from NPE and which simplify the handling of null pointers. These are some situations where the use of `Optional` would make sense:

* You struggle with NPE in your code and you want to make your code more stable in that regard
* You have a lot of null-checks in your code which are quite verbose and hard to read
* You provide an API where specific values can be null by design. You want to make sure that it is transparent

  to consumers of that API  

## How does it work?

### Define/Create an Optional

There are several ways how to create an optional: 1. Create an empty Optional, that does not contain any object

```text
Optional<String> optional = Optional.empty()
```

1. Create an Optional containing an object

   ```text
   // If aString is null, you get a NullPointerException here
   Optional<String> optional = Optional.of(aString);
   ```

2. Create an Optional with an object that could potentially be null

   ```text
   // If the string is null an empty Optional is created (no NullpointerException here)
   Optional<String> optional = Optional.ofNullable(aStringThatIsPotentiallyNull);
   ```

### Retrieve the value that is wrapped by an Optional

You can get values that are wrapped by an Optional in several ways. Also you can check if an Optional contains a value or not:  
1. `get()`: Will return value in case it is present. Will throw _NoSuchElementException_ in case value is not present.

```text
    // Will return the String passed with the optional if present. 
    // Otherwise will throw a NoSuchElementException
    public String unpackOptionalWithGet(Optional<String> optional) {
        return optional.get(); 
    }
```

1. `orElse(T other)`: Will return value in case it is present. Will return `other` value provided in case value is not 

   present.

   ```text
    // Will return the String passed with the optional if present. 
    // Otherwise will return "Some Standard String"
    public String unpackOptionalWithOrElse(Optional<String> optional) {
        return optional.orElse("Some Standard String");
    }
   ```

2. `orElseGet(Supplier<? extends T> supplier)`: Will return value in case it is present. Will return the result of the

   of the Supplier function.

   ```text
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

3. `orElseThrow(Supplier<? extends Throwable> exceptionSupplier)`: Will return value in case it is present. Will the 

   exception that is provided by the Supplier function.

   ```text
    // Will return String passed with the optional if present. 
    // Otherwise will throw an IllegalArgumentException with the message "Please provide a non null value"
    public String unpackOptionalWithOrElseThrow(Optional<String> optional) {
        return optional.orElseThrow(() -> new IllegalArgumentException("Please provide a non null value"));
    }
   ```

4. `isPresent()`: Returns _true_ if value is present, otherwise returns _false_

   ```text
    public boolean checkIfValueIsPresent(Optional<String> optional) {
        return optional.isPresent();
    }
   ```

### map\(\) and flatMap\(\) - Retrieve values from nested objects

For more complex cases, like retrieving some value from a nested object in a safe way the Optional's `map` and `flatMap` methods can be used:

* The `map` method applies a mapper function to the value that is inside the Optional and returns the value wrapped

  again in an Optional. If there is no value, then an empty Optional is returned

* The `flatMap` method has an additional step. It first unwraps the value inside the Optional and then applies the 

  the mapper function to it if the value does exist. Otherwise it also returns an empty Optional. The `FlatMap` method is

  handy when you are dealing with fields that are already wrapped by an Optional.  

Let's have some examples to demonstrate the methods:  
Assume we have a `Customer` class that has two fields: `String firstName` which is a string and `Optional<String> lastName` which is an Optional wrapping a string.

```text
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

#### map\(\)

If we want to access the `firstName` in a save way we can use the `map()` method. It does the following: 1. It takes a mapper function as a parameter 1. It checks if the Optional contains a value. If not, it returns an empty Optional. If yes then ... 1. ... it applies the mapper function to the value that is wrapped by the Optional 1. It wraps the value in an Optional \(after the mapping function was applied\) and returns that Optional

```text
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

Now we want to get the `lastName` of the customer. Remember, this field is already an Optional of type String. What happens when we use the `map()` method here?

```text
    public String useMapOnOptionalField(Customer customer) {
        Optional<Customer> customerOptional = Optional.ofNullable(customer);
        Optional<Optional<String>> lastNameOptionalOfOptional = customerOptional.map(Customer::getLastName);
        Optional<String> lastNameOptional = lastNameOptionalOfOptional.get();
        String lastName = lastNameOptional.orElse("Last name is not available");
        return lastName;
    }
```

Here we would have an additional step as the `map()` method wraps the result into an Optional. In this case this would result in an Optional of an Optional. For a better understanding the steps above explained:

* The customer object is first wrapped in an Optional, as we don't know if customer is null
* Then we apply the `map()` method which returns the `lastName` field wrapped in an Optional. As the field was already

  an Optional before, it is now an Optional of an Optional

* We apply the `get()` method to retrieve the inner Optional which holds the `lastName`
* Then we apply the `orElse()` method to get the `lastName` string as a result  

As you see it is quite of an overhead when using the `map()` method on fields that are already wrapped in an Optional. A better solution for that is to use the `flatMap()` method.

#### flatMap\(\)

In case a value that we want to retrieve is already wrapped in an Optional, we can use the `flatMap()` method. It does the following: 1. It takes a mapper function as a parameter 1. It checks if the Optional contains a value. If not, it returns an empty Optional. If yes then ... 1. ... it applies the mapper function to the value that is wrapped by the Optional \(the value has to be an Optional itself\) 1. It returns the value \(which is in that case already and Optional\) after the mapping function was applied

#### Difference between map\(\) and flatMap\(\)

As you can see `map()` and `flatMap` differ in the type they expect as a result by the mapper function \(step 3.\) and they differ in the way they handle the value before returning it \(step 4.\):

* `map`: In step 3. `map()` the mapper can return any type as in step 4. the value is packed into an Optional and returned. 

  That ensures that the return type is always an Optional.

* `flatMap`: In step 3. `flatMap()` expects the mapper function to return an Optional. This is because in step 4. the 

  value **is not** wrapped in an Optional. So the value already has to be an Optional sothat `flatMap` can return it.

#### Retrieve value of a nested object with map\(\)

You can use `map()` and `flatMap()` to retrieve values form nested objects in a save way. The example below shows how `map()` is applied twice in order to first access the address of a customer and then in a second step the country field \(you can find the implementation of the customer class at the bottom\).

```text
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

This is what happens: 1. The customer is wrapped in an Optional as it could be null 1. `map()` is called on the customerOptional

* The `map()` method checks if the Optional contains a value. If it does not contain a value it returns an empty 

  Optional \(see \[map\(\) step 2\]\(\#map\(\)\)

* If it contains a value, the mapper function is applied to that value \(the value in that case is the customer and 

  the mapper function it `getAddress()`\)

* The result of the mapper function is wrapped in an Optional \([see map\(\) step 3 and 4](optionals.md#map%28%29)\)

  If it contains a value it wraps the value \(in this case the address objects\) in an 

  Optional and returns it \(see \[map\(\) step 3 and 4\]\(\#map\(\)\)

  1. Now the `map()` method is applied to the Optional containing the address \(this is the Optional returned in the last

     step\)

* The `map()` method checks if the Optional contains a value \(the country\). If it does not contain a value it 

  returns an empty Optional

* If it contains a value, the mapper function is applied to that value \(the value in that case is the address and 

  the mapper function it `getCountry()`\)

* The result of the mapper function \(which is either null or a string with the country name\) is wrapped in an 

  Optional and returned

  1. The `orElse` method is called on the Optional that is returned by the last step. If it contains a value, then the 

     value is returned. Otherwise "Country is null" is returned.

Without using Optional the same logic would result in less readable nested if statements:

```text
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

**Here you see the customer class that is used in the example below** \([here you can find the complete code](https://github.com/Holidaydrills/holidaydrills-Java8/blob/master/src/main/java/com/holidaydrills/optional/Customer.java):

```text
public class Customer {

    private String firstName;
    private Optional<String> lastName;
    private Address address;

    // Constructors are implemented here ...
    // Getters and setters are implemented here ...

    class Address {

        private String country;
        private String city;
        private String street;
        private String houseNumber;

    // Constructors are implemented here ...
    // Getters and setters are implemented here ...

    }

}
```

