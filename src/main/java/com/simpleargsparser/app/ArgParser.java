package com.simpleargsparser.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.simpleargsparser.app.Structs.Cmd;
import com.simpleargsparser.app.Structs.Flag;

public class ArgParser {
    
    public ArgParser(String[] args){
        this.args = Arrays.asList(args);
    }

    private List<String> args = new ArrayList<>();

    private Cmd cmd;
    private Map<String, Flag> flags = new HashMap<>();

    private Map<String, Flag> alloWedflags = new HashMap<>();
    private Map<String, Cmd> allowedCmd = new HashMap<>();

    /**
     * Parse the line of arguments, detects the first command that appears as the command, flags if they start with "-". consider everything else as command arguments.
     * @throws IllegalArgumentException
     */
    public void Parse() throws IllegalArgumentException {
        
        Iterator<String> iter = args.iterator();
        while(iter.hasNext()){
            String arg = iter.next();
            if(arg.matches("--help") || arg.matches("-h") || args.isEmpty()){
                displayHelpBasedOnContext();
                break;
            }
            if(this.cmd == null && allowedCmd.containsKey(arg)){
                Cmd templateCmd = allowedCmd.get(arg);
                this.cmd = new Cmd(arg, templateCmd.getNumberOfArgs());
            }
            else if(arg.startsWith("-") && alloWedflags.containsKey(arg)){
                Flag templateFlag = alloWedflags.get(arg);
                Flag flag = new Flag(arg, templateFlag.hasArgument(), templateFlag.isCompulsory(), "");
                flags.put(arg, flag);
                if(iter.hasNext() && flag.hasArgument()){
                    arg = iter.next();
                    if(arg.startsWith("-")) throw new IllegalArgumentException("Flag "+flag.getName()+"Requires an argument and were given none");
                    flag.setArg(arg);
                }
            } 
            else if (arg.startsWith("-")) throw new IllegalArgumentException("Unknow flag "+arg);
            else if(this.cmd != null) {
                cmd.addArg(arg);
            }
            else{
                throw new IllegalArgumentException("Unknow flag or command");
            }
        }

        checkParsingValidity();
    }

    // Check the parsing validity based on some criterias: A command should exist and have the proper number of arguments. Mandatory flags are presents...
    private void checkParsingValidity() throws IllegalArgumentException{
        if(cmd == null){
            throw new IllegalArgumentException("No command provided, a command must be provided");
        }
        if(cmd.getNumberOfArgs() != -1 && cmd.getNumberOfArgs() != cmd.getArgs().size()){
            throw new IllegalArgumentException("Unrequired number of arguments for the command "+ cmd.getName()+ " needed " + cmd.getNumberOfArgs() + " given "+cmd.getArgs().size());
        }
        alloWedflags.forEach((k,v) -> {
            if(v.isCompulsory() == true && !flags.containsKey(k) ){
                throw new IllegalArgumentException("The command "+cmd.getName() + " requires the usage of flag "+ v.getName());
            }
        });

    }

    private void displayHelpBasedOnContext(){
        if(cmd != null){
            System.out.println(cmd.getDescription());
        }
        else{
            System.out.printf("List of commands %n%n");
            allowedCmd.forEach((k,v) ->{
                System.out.printf("%s : %s %n", k,v.getDescription());
            });
            System.out.println("List of flags %n%n");
            alloWedflags.forEach((k,v) -> {
                System.out.printf("%s : %s %n",k,v.getDescription());
            });
        }
    }

    /**
     * Return the argument of the flag corresponding to the given name or throws an error if the flag does not exist.
     * @param flagName
     * @return
     * @throws IllegalArgumentException
     */
    public String getFlagArg(String flagName) throws IllegalArgumentException{
        if(flags.containsKey(flagName))  return flags.get(flagName).getArg();
        else throw new IllegalArgumentException("Flag does not exist");
    }

    /**
     * Returns a List of the command arguments
     * @return
     */
    public List<String> getCmdArgs(){
        return cmd.getArgs();
    }

    public Cmd getCmd(){
        return this.cmd;
    }

    public Map<String, Flag> getFlags(){
        return this.flags;
    }

    /**
     * Add a flag to the list of the allowed flags
     * @param flag
     */
    public void addFlag(Flag flag){
        this.alloWedflags.put(flag.getName(), flag);
    }

    /**
     * Add a command to the list of the allowed commands
     * @param cmd
     */
    public void addCmd(Cmd cmd){
        this.allowedCmd.put(cmd.getName(), cmd);
    }

}
