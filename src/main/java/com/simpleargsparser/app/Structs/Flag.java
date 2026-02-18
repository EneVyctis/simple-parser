package com.simpleargsparser.app.Structs;

/**
 * A data structure representing the flag (option) of a command line.
 * <p>
 * A flag can be optional or compulsory and can requires, or not, an unique additional argument
 * (ex: {@code -f filename}).
 * </p>
 */
public class Flag {
    private String name;
    private boolean hasArgument;
    private boolean isCompulsory;
    private String arg;
    private String description;


    private Flag(String name){
        this.name = name;
        this.hasArgument = false;
        this.isCompulsory = false;
        this.description = "";
    } 

    /**
     * Create a new flag with all the given parameters.
     * @param name
     * @param hasArgument
     * @param isCompulsory
     * @param description
     */
    public Flag(String name, boolean hasArgument, boolean isCompulsory, String description){
        this.name = name;
        this.hasArgument = hasArgument;
        this.isCompulsory = isCompulsory;
        this.description = description;
    }

    /**
     * Returns a new flag with the specified name and defaults values.
     * @param name
     * @return
     */
    public static Flag named(String name){
        return new Flag(name);
    }

    /**
     * Specifies that the flag must have an argument
     * @return
     */
    public Flag withArgument(){
        this.hasArgument = true;
        return this;
    }

    /**
     * Specifies that the flag is required for the command
     * @return
     */
    public Flag compulsory(){
        this.isCompulsory = true;
        return this;
    }

    /**
     * Specifies the description that will be printed by the helper flag.
     * @param description
     * @return
     */
    public Flag withDescription(String description){
        this.description = description;
        return this;
    }

    public String getName(){
        return this.name;
    }
        
    public void setArg(String arg){
        this.arg = arg;
    }

    public String getArg(){
        return this.arg;
    }

    public boolean hasArgument(){
        return this.hasArgument;
    }

    public boolean isCompulsory(){
        return this.isCompulsory;
    }

    public String getDescription(){
        return this.description;
    }
}
