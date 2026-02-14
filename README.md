# simple-parser

I wanted to build a command line tool (a clone of git) in java for fun. Thus, i needed to parse command line arguments and though i would do everything from scratch because why not.  

Here is simple-parser, a functionnal enough parser for my needs.

Import simple-parser to your project and create a new parser for your program. 

```java 
ArgParser parser = new Argparser(args);
```
Then, declare allowed flags and commands for your program

```java
parser.addCmd(String "cmdName", int -1); // -1 is used to specify that the program accepts any number of arguments
parser.addFlag( String "flagName", boolean hasArgument, boolean isCompulsory); // Replace hasArgument & isCompulsory with true or false depending on your needs
```

Finally, parse

```java
parser.Parse()
```

You can get everything needed using the getters.

```java
parser.getFlagArg(String "flagName");
parser.getFlags();
parser.getCmdArg();
parser.getCmd();
```

