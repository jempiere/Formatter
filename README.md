# Formatter
A simple class for simple formatting. See the docs for more details at https://jempiere.github.io/Formatter/.


## Abilities


the Formatter class has several methods that allow the user to reformat strings by appending, prepending, replacing, case-changing, and separating -- all within a String full of commands. The method returns a String with the commands enacted.
The commands are available in the formatter(String data, String format) method of the javadoc. Happy Coding!

## Update 1.3!


The Formatter class now has a new sister class - EFormatter! The EFormatter class can be extended (though its functionality is much more limited than the original Formatter class--for now!) and used to implement custom formatter functionality! The original commands are untouched as of now, ands will still exist (so do not try to override them -- it won't do anything good). There is also a new example class (PersonalFormartterExample.java) that shows any beginners (or anybody unwilling to read my code) how to use it!
