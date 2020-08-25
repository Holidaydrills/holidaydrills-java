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
* A [Duration] defines the difference between two points in time in seconds and nanoseconds

```Java

```

## Good to know


[java.time package]: https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
[holidaydrills-java8 repository]: https://github.com/Holidaydrills/holidaydrills-java11/blob/master/src/main/java/com/holidaydrills/timepackage/TimeExamples.java
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
