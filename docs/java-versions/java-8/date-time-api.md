# Date and Time Api
Java 8 introduces the [java.time package] which provides a convenient way to deal with dates and times. 

## Examples
You can find all the examples from this tutorial in the [holidaydrills-java8 repository].

## What is it good for?
The the date apis have some big advantages compared to the older date apis:
* **Thread safety**: The different date and time classes in the [java.time package] are thread safe. So you don't have to worry about thread safety 
in your code. This is different for older date classes like [java.util.Date] where you have to handle thread safety on your own.  
* **Convenient way of calculating dates**: The new api provides convenient ways to calculate dates and times e.g. adding or subtracting on days, months, ...
* **Consist but not restrictive**: The api is based on the [ISO8601 Standard] which makes it consistent and more intuitive. However 
it is not restricted to that and allows also to conveniently work with different calendar systems.

## How does it work?
### Instantiating Dates
The Date classes of the [java.time package] provide factory methods which make creating new date objects really
 convenient. Some of them are:
 * [now()] which will create a date from the current time
 * [of()] which will create a date from the constituents of a date, like year, month and day
 * [parse()] which creates a date from a String (or to be specific a [CharSequence])
 * [from()] which creates a date by converting another type which must be a [TemporalAccessor]
```Java
LocalDate dateNow = LocalDate.now();
LocalDateTime dateTimeNow = LocalDateTime.now();

LocalTime someTime = LocalTime.of(22, 55, 30);
LocalDate someDate = LocalDate.of(1990, 10, 03);
LocalDate againTheSameDate = LocalDate.of(1990, Month.APRIL, 03);
// LocalDateTime with year, month, day, hour, minutes and seconds
LocalDateTime someDateTime = LocalDateTime.of(1990, 10,03,22,55,30);
LocalDateTime againTheSameDateTime = LocalDateTime.of(someDate, someTime);

LocalDate dateFromString = LocalDate.parse("1990, 10, 03");

LocalDate oneMoreDate = LocalDate.from(Instant.now());
```

### Operations on Dates
You can perform operations on dates by adding or subtracting the different parts of a date or time. There are some
 points that have to be considered:  
* When you call an operation on a date object then the return value represents the changed date. The date object you
 call the operation on is not changed.
* You can chain the methods in order to manipulate every part of a date in one line (see last example)

```Java
LocalDateTime localDateTime = LocalDateTime.of(1990, 10,03,22,55,30);

LocalDateTime newLocalDateTime = localDateTime.plusYears(2);
// localDate is not changed, only the return value reflect the change
System.out.println(localDateTime); // 1990-10-03T22:55:30
System.out.println(newLocalDateTime); // 1992-10-03T22:55:30

newLocalDateTime = localDateTime.plusMonths(1);
System.out.println(newLocalDateTime); // 1990-11-03T22:55:30

newLocalDateTime = localDateTime.plusDays(5);
System.out.println(newLocalDateTime); // 1990-11-08T22:55:30

newLocalDateTime = localDateTime.minusHours(7);
System.out.println(newLocalDateTime); // 1990-10-03T15:55:30

newLocalDateTime = localDateTime.plusMinutes(1);
System.out.println(newLocalDateTime); // 1990-11-08T23:56:30

newLocalDateTime = localDateTime.plusSeconds(10);
System.out.println(newLocalDateTime); // 1990-11-08T23:55:40

// Method chaining
newLocalDateTime = localDateTime.plusYears(2).plusMonths(1).plusDays(5).minusHours(7).plusMinutes(1).plusSeconds(10);
System.out.println(newLocalDateTime); // 1992-11-08T15:56:40
```

### Working with time zones
The [java 8 specification][java.time package] clearly states that it is recommended to use LocalDate (LocalTime
, LocalDateTime) and keep
 the handling of time zones away from the application logic e.g. by handling it in the persistence (this could for
  example be done by an ORM framework like hibernate). However sometime you're forced to use time zones explicitly
  . In that cases you can add time zone information:   

```Java
 System.out.println(ZoneId.getAvailableZoneIds()); //[Asia/Aden, America/Cuiaba, Etc/GMT+9, Etc/GMT+8, Africa/Nairobi, ...]
        
ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("Europe/Brussels"));
System.out.println(zonedDateTime); // 2020-08-25T08:26:46.648786+02:00[Europe/Brussels]
```

### Working with time periods
It is also possible to define time [periods][Period] and [durations][Duration]:
* A [Period] defines years, months and days between two points in time
   * It has the format `P1Y2M3D` 
   * *P* indicates that it is a period, *Y*, *M* and *D* indicate year, month and
    day
   * The numbers in the example are the number of years, months and day. In the example it would be 1 year, 3
     months and 3 days
* A [Duration] defines the difference between two points in time in seconds and nanoseconds   

**Example for Period:**  
In this example we do the following
* Instantiate a LocalDate `initialDate`
* Instantiate a LocalDate `secondDate` by adding years, months and days with the help of the [Period] class
* Getting the period between `initialDate` and `secondDate`
* Print the Period, which tells a period of 2 years, 3 months and 8 days.
* Print the different parts of the period (years, months, days) separately
```Java
LocalDate initialDate = LocalDate.now();
LocalDate secondDate = initialDate.plus(Period.ofYears(2)).plus(Period.ofDays(8));

Period periodBetweenDates = Period.between(initialDate, secondDate);
System.out.println(periodBetweenDates); // P2Y0M8D
System.out.println(periodBetweenDates.getYears()); // 2
System.out.println(periodBetweenDates.getMonths()); // 0
System.out.println(periodBetweenDates.getDays());  // 8
```

**Example for Duration:**  
In this example we do the following
* Instantiate a LocalTime `initialTime`
* Instantiate a LocalTime `secondTime` by adding hours, minutes, seconds, milliseconds and nanoseconds
* Getting the duration between `initialTime` and `secondTime`
* Print the Duration, which is `PT3H5M30.123056789S`
   * *PT* for period time
   * *3H* for 3 hours
   * *5M* for 5 minutes
   * *30.123056789S* for seconds. This contains 30 seconds, 123 milliseconds and the 56789 nanoseconds we added
```Java
LocalTime initialTime = LocalTime.now();
LocalTime secondTime = initialTime.plus(Duration.ofHours(3))
                                    .plus(Duration.ofMinutes(5))
                                    .plus(Duration.ofSeconds(30))
                                    .plus(Duration.ofMillis(123))
                                    .plus(Duration.ofNanos(56789));

Duration durationBetweenTimes = Duration.between(initialTime, secondTime);
System.out.println(durationBetweenTimes); // PT3H5M30.123056789S
System.out.println(durationBetweenTimes.getSeconds()); // 11130
```

### ChronoUnit
[ChronoUnit] is a set of date periods units that enable unit-based access to manipulate a date. You can also use
 [ChronoUnit] for getting the number of days, months etc. between two dates. The difference to [Period] is that
 * [ChronoUnit] calculates the total number of the specified unit (e.g. days, hours, ...)
 * [Period] holds the period between two dates in a combined way of years, months and days  
 
Here is one example which shows the difference:
* `daysFromPeriod` return 0 as the days don't differ (the period holds the difference in the *month* part which would
 be 3)
* `daysFromChronoUnit` returns 91 which is the total number of days  

```Java
LocalDate initialDate = LocalDate.of(2020, 1, 1);
LocalDate secondDate = LocalDate.of(2020, 4, 1);

int daysFromPeriod = Period.between(initialDate, secondDate).getDays();
long daysFromChronoUnit = ChronoUnit.DAYS.between(initialDate, secondDate);

System.out.println(daysFromPeriod); // 0
System.out.println(daysFromChronoUnit); // 91
```


[java.time package]: https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
[holidaydrills-java8 repository]: https://github.com/Holidaydrills/holidaydrills-java8/blob/master/src/main/java/com/holidaydrills/timepackage/TimeExamples.java
[java.util.Date]: https://docs.oracle.com/javase/7/docs/api/java/util/Date.html
[ISO8601 Standard]: https://en.wikipedia.org/wiki/ISO_8601
[now()]: https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#now--
[of()]: https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#of-int-int-int-
[parse()]: https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#parse-java.lang.CharSequence-
[from()]: https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#from-java.time.temporal.TemporalAccessor-
[CharSequence]: https://docs.oracle.com/javase/8/docs/api/java/lang/CharSequence.html
[TemporalAccessor]: https://docs.oracle.com/javase/8/docs/api/java/time/temporal/TemporalAccessor.html
[Duration]: https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html
[Period]: https://docs.oracle.com/javase/8/docs/api/java/time/Period.html
[ChronoUnit]: https://docs.oracle.com/javase/8/docs/api/java/time/temporal/ChronoUnit.html
