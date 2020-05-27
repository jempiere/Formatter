# Formatter

A simple class for simple formatting. See the docs for more details at https://jempiere.github.io/Formatter/.


## Abilities

the Formatter class has several methods that allow the user to reformat strings by appending, prepending, replacing, case-changing, and separating -- all within a String full of commands. The method returns a String with the commands enacted.
The commands are available in the formatter(String data, String format) method of the javadoc. Happy Coding!


## Update 1.3!

The Formatter class now has a new sister class - EFormatter! The EFormatter class can be extended (though its functionality is much more limited than the original Formatter class--for now!) and used to implement custom formatter functionality! The original commands are untouched as of now, ands will still exist (so do not try to override them -- it won't do anything good). There is also a new example class (PersonalFormartterExample.java) that shows any beginners (or anybody unwilling to read my code) how to use it!


## Installation Instructions

> intellij IDEA:
1. download the jar located in out >> artifacts >> Formatter_jar
2. move it into your project (location is irrelevant)
3. right click > add as Library
4. if it is now in the `External Libraries` folder and it hasn't been automatically removed from its initial location in your classpath, you may delete it
4. `import com.jempiere.util.Formatter` or `import com.jempiere.util.home.EFormatter` in your code!
5. enjoy! reference the javadoc at any time at https://jempiere.github.io/Formatter/ for information on usage.

> Eclipse IDE:
1. Download the jar located in out >> artifacts >> Formatter_jar
2. Right click the project file
3. Select Build Path > Configure Build Path > Libraries > Add external JARs
4. Select Formatter.jar from the directory
5. `import com.jempiere.util.Formatter` or `import com.jempiere.util.home.EFormatter` in your code!
6. Click Apply and Ok

> NetBeans IDE:
1. download the jar located in out >> artifacts >> Formatter_jar
2. Right click Libraries in Project list, then hit Add and select the jar you downloaded
3. `import com.jempiere.util.Formatter` or `import com.jempiere.util.home.EFormatter` in your code!
