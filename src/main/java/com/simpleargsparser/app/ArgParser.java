package com.simpleargsparser.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArgParser {
    
    public ArgParser(String[] args){
        this.args = Arrays.asList(args);
    }

    public class Flag {
        private String name;
        private boolean hasArgument;
        private boolean isCompulsory;
        private String arg;

        public Flag(String name, boolean hasArgument, boolean isCompulsory){
            this.name = name;
            this.hasArgument = hasArgument;
            this.isCompulsory = isCompulsory;
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
    }

    public class Cmd {
        private String name;
        private int numberOfArgs;
        private List<String> args;

        public Cmd(String name, int numberOfArgs){
            this.name = name;
            this.numberOfArgs = numberOfArgs;
            this.args = new ArrayList<>();
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

        public void addArg(String arg){
            this.args.add(arg);
        }
    }

    private List<String> args = new ArrayList<>();

    private Cmd cmd;
    private Map<String, Flag> flags = new HashMap<>();

    private Map<String, Flag> alloWedflags = new HashMap<>();
    private Map<String, Cmd> allowedCmd = new HashMap<>();

    public void Parse() throws IllegalArgumentException {
        
        Iterator<String> iter = args.iterator();
        while(iter.hasNext()){
            String arg = iter.next();
            if(this.cmd == null && allowedCmd.containsKey(arg)){
                Cmd templateCmd = allowedCmd.get(arg);
                this.cmd = new Cmd(arg, templateCmd.getNumberOfArgs());
            }
            else if(arg.startsWith("-") && alloWedflags.containsKey(arg)){
                Flag templateFlag = alloWedflags.get(arg);
                Flag flag = new Flag(arg, templateFlag.hasArgument(), templateFlag.isCompulsory());
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

    private void checkParsingValidity() throws IllegalArgumentException{
        if(cmd.getNumberOfArgs() != -1 && cmd.getNumberOfArgs() != cmd.getArgs().size()){
            throw new IllegalArgumentException("Unrequired number of arguments for the command "+ cmd.getName()+ " needed " + cmd.getNumberOfArgs() + " given "+cmd.getArgs().size());
        }
        alloWedflags.forEach((k,v) -> {
            if(v.isCompulsory() == true && !flags.containsKey(k) ){
                throw new IllegalArgumentException("The command "+cmd.getName() + " requires the usage of flag "+ v.getName());
            }
        });

    }

    public String getFlagArg(String flagName) throws IllegalArgumentException{
        if(flags.containsKey(flagName))  return flags.get(flagName).getArg();
        else throw new IllegalArgumentException("Flag does not exist");
    }

    public List<String> getCmdArgs(){
        return cmd.getArgs();
    }

    public Cmd getCmd(){
        return this.cmd;
    }

    public Map<String, Flag> getFlags(){
        return this.flags;
    }

    public void addFlag(String name, boolean hasArgument, boolean isCompulsory){
        this.alloWedflags.put(name, new Flag(name, hasArgument, isCompulsory));
    }

    public void addCmd(String name, int numberOfArgs){
        this.allowedCmd.put(name, new Cmd(name, numberOfArgs));
    }

}
