# String API Enhancements
Java 11 introduces some useful methods on strings. These are
* [isBlank()]
* [lines()]
* [repeat(int count)]
* [strip()], [stripLeading()], [stripTrailing()]


## Examples
You can find all the examples from this tutorial in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8) 
in the [holidaydrills-java8 repository](https://github.com/Holidaydrills/holidaydrills-Java8/tree/master/src/main/java/com/holidaydrills/...)
directory.

## How does it work?
### isBlank()
[isBlank()] returns true is the string is empty or if it contains only white space.
```Java
public static void isBlankExample() {
    String emptyString = "";
    System.out.println(emptyString.isBlank()); // true

    String stringOfBlanks = "   ";
    System.out.println(stringOfBlanks.isBlank()); // true

    String notOnlyBlanks = "         not  only    blank   ";
    System.out.println(notOnlyBlanks.isBlank()); // false
}
```

### lines()
[lines()] returns a stream of lines of the string. Lines are determined by a *line feed character* `\n`, *carriage return character* 
`\r` or by a carriage return directly followed by a line feed `\r\n`  

```Java
public static void linesExample() {
    String multiLineString = "First line\nSecond line\nThird line";
    System.out.println(multiLineString);
    /*
    Output:
    First line
    Second line
    Third line
     */

    System.out.println(multiLineString.lines().collect(Collectors.toList()));
    /*
    Output:
    [First line, Second line, Third line]
     */
}
```

### repeat()
[repeat(int count)] concatenates a string *count* times and returns it. If the string is empty or the count is zero, then 
an empty string is returned.
```java
public static void repeatExample() {
    String stringThatShouldBeRepeated = "Jump!";
    System.out.println(stringThatShouldBeRepeated.repeat(5)); // Output: Jump!Jump!Jump!Jump!Jump!
}
```

### strip(), stripLeading(), stripTrailing()
[strip()], [stripLeading()], [stripTrailing()] remove either leading and trailing white spaces, only leading whitespaces or only 
trailing whitespaces.  
The difference to [trim()] is:
* [trim()] only identifies a white space when it is a unicode [codepoint] equal 
or less than `U+0020` (See the list of whitespace codepoints [here]
* [strip()] uses the [isWhitespace()] method internally which identifies whitespaces more reliably (e.g. [U+2005] or other 
17 whitespace characters of the [separator space category]
```Java
public static void stripExample() {
    String myString = "   Hello there!    ";
    System.out.println(myString.strip()); // Output: "Hello there!"
    System.out.println(myString.stripLeading()); // Output: "Hello there!    "
    System.out.println(myString.stripTrailing()); // Output: "   Hello there!"

    String withUnicodeWhitespace = "\u2005\u2005Hello there!  \n\t";
    System.out.println(withUnicodeWhitespace.strip()); // Output: "Hello there!"
    System.out.println(withUnicodeWhitespace.trim()); // Output: "  Hello there!"
}
```

## Good to know

[isBlank()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#isBlank())
[lines()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#lines())
[repeat(int count)](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#repeat(int))
[strip()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#strip())
[stripLeading()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#stripLeading())
[stripTrailing()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#stripTrailing())
[trim()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#trim())
[codepoint](https://en.wikipedia.org/wiki/Code_point)
[here](https://en.wikipedia.org/wiki/Whitespace_character)
[isWhitespace()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html#isWhitespace(int))
[U+2005](https://www.fileformat.info/info/unicode/char/2005/index.htm)
[separator space category](https://www.fileformat.info/info/unicode/category/Zs/list.htm)




