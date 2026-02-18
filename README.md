# simple-parser

I wanted to build a command line tool (a clone of git) in java for fun. Thus, i needed to parse command line arguments and though i would do everything from scratch because why not.  

**Here is *simple-parser*, a functionnal enough parser for my needs.**

## Quick-Start

Import simple-parser to your project and create a new parser for your program. 

```java 
ArgParser parser = new Argparser(args);
```
Then, declare allowed flags and commands for your program, i've add a sort of light fluent api/builder pattern to help declare only necessary parameters.
Here is an example

```java
parser.addCmd(Cmd.nammed("add").withAnyNumberOfArgs().withDescription("add some files to staged files"));
parser.addFlag(Flag.nammed("-o").withArgument().compulsory().withDescription("Use the flag argument as the output file")); 
```

Finally, parse

```java
parser.Parse()
```

You can get everything needed using the getters.

```java
parser.getFlagArg("-o");
parser.getFlags(); // returns a list of flags
parser.getCmdArg(); // return a list of the command arguments
parser.getCmd(); // return the command
// ...
```

## RoadMap

If i had to continue this project, i would implement the following:

- Support aliases for flags (ex : -v for --verbose).
- Support for combined flags (-rf instead of -r -f).
- Support for multi arguments flags (currently all flags are distinct and must have between 0 and 1 arguments).
- Support for different data types (currently only strings are parsed).

