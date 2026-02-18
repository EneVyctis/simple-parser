package com.simpleargsparser.app.Structs;

import java.util.ArrayList;
import java.util.List;

/**
 * A data structure representing the command of a command line.
 * <p>
 * A command is compulsory by design and can requires, or not, a fixed number of arguments.
 * (ex: {@code -f filename}).
 * </p>
 */
public class Cmd {
    private String name;
    private int numberOfArgs;
    private List<String> args;
    private String description;

    /**
     * Returns a new Cmd with the specified name and default values.
     * @param name
     * @return
     */
    public static Cmd named(String name){
        return new Cmd(name);
    }

    private Cmd(String name){
        this.name = name;
        this.numberOfArgs = 0;
        this.args = new ArrayList<>();
        this.description = "";

    }
    /**
     * 
     * @param name Complete name of the command (ex: add).
     * @param numberOfArgs  Fixed number of arguments, use -1 if the command can take any number of arguments. 
     */
    public Cmd(String name, int numberOfArgs){
        this.name = name;
        this.numberOfArgs = numberOfArgs;
        this.args = new ArrayList<>();
    }

    /**
     * Set the expected number of arguments to the specified number
     * @param numberOfArgs
     * @return
     */
    public Cmd withNumberOfArgs(int numberOfArgs){
        this.numberOfArgs = numberOfArgs;
        return this;
    }

    /**
     * Set the expected number of arguments to any.
     * @return
     */
    public Cmd withAnyNumberOfArgs(){
        this.numberOfArgs = -1;
        return this;
    }

    /**
     * Specifies the description that will be printed by the helper flag.
     * @param description
     * @return
     */
    public Cmd withDescription(String description){
        this.description = description;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public int getNumberOfArgs(){
        return this.numberOfArgs;
    }

    public List<String> getArgs(){
        return List.copyOf(this.args);
    }

    public String getDescription(){
        return this.description;
    }

    /**
     * Add an argument to the command
     * @param arg
     */
    public void addArg(String arg){
        this.args.add(arg);
    }
}
