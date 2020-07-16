# Method References

A method reference is a lambda expression that provides a clean and short syntax to refer to a method by its name. 
You can find the examples used below in the 
[holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/methodreference).

## What is it good for?

Lambda expressions are used to define unnamed/anonymous methods. But sometimes a lambda expression does
nothing else than just calling another method. In that case you can make use of method references to 
make your code cleaner and more readable.  
**An example will make it more clear**:  
Suppose you have a car fleet with a bunch of cars (of type Car) that are stored in a list. Now you want to find out
what car models are contained. Therefore you extract a list that contains only the car names. Below you can see 
an example that shows both ways - without and with the use of method reference. 
```
// Without method reference
public List<String> getAllAvailableModelNames() {
        return cars.stream()
                .map(aCar -> aCar.getModelName())
                .collect(Collectors.toList());
}

// With method reference
public List<String> getAllAvailableModelNames() {
        return cars.stream()
                .map(Car::getModelName)
                .collect(Collectors.toList());
}
```

## How does it work?

There are different types of method references:
1. Reference to a static method - the syntax is `ClassName::staticMethodName`
1. Reference to an instance method of a particular instance: `instance::instanceMethodName`
1. Reference to an instance method of a arbitrary instance of a particular type: `ClassName::instanceMethodName`
1. Reference to a constructor: `ClassName::new`

Below there are examples for each of the different method reference types. They all refer to methods of the
Car.class:
```
public class Car {

    private UUID id;
    private String producerName;
    private String modelName;
    private LocalDate releaseDate;
    private int horsePower;

    public Car(String producerName, String modelName, LocalDate releaseYear, int horsePower) {
        this.id = UUID.randomUUID();
        this.producerName = producerName;
        this.modelName = modelName;
        this.releaseDate = releaseYear;
        this.horsePower = horsePower;
    }

    public Car(String modelName) {
        this.id = UUID.randomUUID();
        this.modelName = modelName;
    }

    public static int compareByReleaseDate(Car a, Car b) {
        return a.getReleaseDate().compareTo(b.getReleaseDate());
    }

    public String getTechnicalDescription() {
        return String.format("Producer: %s | Model: %s | Horse Power: %s",
                producerName, modelName, horsePower);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return car.id.equals(this.id);
    }

    // ... getters and setters below
}
``` 

### Reference to a static method
The syntax for a reference to a static method is `ClassName::staticMethodName`. Here the list of cars is
sorted by release date with help of the static `compareByReleaseDate(Car a, Car b)` method. As you can see the
parameters are omitted when using a method reference:
```
    public List<Car> getCarsSortedByReleaseDate() {
        cars.sort(Car::compareByReleaseDate);
        return cars;
    }
```

### Reference to an instance method of a particular instance
The syntax for a reference of a specific instance to an instance method is `instanceName::instanceMethodName`:
```
    public List<Car> getCarsSortedByModelName() {
        CarComparator carComparator = new CarComparator();
        cars.sort(carComparator::compare);
        return cars;
    }

    // The comparator class:
    public class CarComparator implements Comparator<Car> {
        @Override
        public int compare(Car a, Car b) {
            return a.getModelName().compareTo(b.getModelName());
        }
    }
```

### Reference to an instance method of an arbitrary instance
When referencing an instance method with an arbitrary instance of a class the syntax looks like this:
`ClassName::instanceMethodName`. You can see below that this can be used in case you don't have a specific
instance declared like e.g. when streaming some collection:
```
    public List<String> getAllAvailableModelNames() {
        return cars.stream()
                .map(Car::getModelName)
                .collect(Collectors.toList());
    }
```
### Reference to a constructor
When referencing a constructor the syntax is the following `ClassName::new`. In the example below some
new cars are added to the car fleet. As you can see the `modelName` parameter which is part of the Car 
constructor `public Car(String modelName)` is omitted when using a method reference.
```
    public List<Car> addNewCars(List<String> modelNames) {
        List<Car> newCars = modelNames.stream().map(Car::new).collect(Collectors.toList());
        this.cars.addAll(newCars);  
        return this.cars;
    }
```
