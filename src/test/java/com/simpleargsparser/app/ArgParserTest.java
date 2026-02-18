package com.simpleargsparser.app;

import org.junit.jupiter.api.Test;

import com.simpleargsparser.app.Structs.Cmd;
import com.simpleargsparser.app.Structs.Flag;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ArgParserTest {

    private ArgParser parser;

    @Test
    public void testParseSingleCommandWithArgs() {
        String[] args = {"add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        parser.Parse();
        List<String> cmdArgs = parser.getCmdArgs();
        assertEquals(2, cmdArgs.size());
        assertEquals("file1", cmdArgs.get(0));
        assertEquals("file2", cmdArgs.get(1));
    }

    @Test
    public void testParseFlagWithoutArgWithCommand() {
        String[] args = {"-v", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-v"));
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        parser.Parse();
        assertTrue(parser.getFlags().containsKey("-v"));
        assertNull(parser.getFlags().get("-v").getArg());
    }

    @Test
    public void testParseFlagWithArgWithCommand() {
        String[] args = {"-f", "input.txt", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-f").withArgument());
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        parser.Parse();
        assertTrue(parser.getFlags().containsKey("-f"));
        assertEquals("input.txt", parser.getFlags().get("-f").getArg());
    }

    @Test
    public void testMissingCompulsoryFlag() {
        String[] args = {"add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-o").withArgument().compulsory());
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testUnknownFlag() {
        String[] args = {"-x", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testFlagMissingArg() {
        String[] args = {"-f", "-v", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-f").withArgument());
        parser.addFlag(Flag.named("-v"));
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testUnknownCommand() {
        String[] args = {"copy", "file1", "file2"};
        parser = new ArgParser(args);
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testNoCommand() {
        String[] args = {"-test"};
        parser = new ArgParser(args);
        parser.addCmd(Cmd.named("add").withAnyNumberOfArgs());
        parser.addFlag(Flag.named("-test").compulsory());
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testWrongNumberOfArgsForCommand() {
        String[] args = {"add", "file1"};
        parser = new ArgParser(args);
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        assertThrows(IllegalArgumentException.class, () -> parser.Parse());
    }

    @Test
    public void testAnyNumberOfArgsForCommand() {
        String[] args = {"add", "file1", "file2", "file3","file4"};
        parser = new ArgParser(args);
        parser.addCmd(Cmd.named("add").withAnyNumberOfArgs());
        parser.Parse();
        assertEquals(4, parser.getCmd().getArgs().size());
    }

    @Test
    public void testGetFlagArg() {
        String[] args = {"-f", "input.txt", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-f").withArgument());
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        parser.Parse();
        assertEquals("input.txt", parser.getFlagArg("-f"));
    }

    @Test
    public void testGetFlagArgUnknownFlag() {
        String[] args = {"-f", "input.txt", "add", "file1", "file2"};
        parser = new ArgParser(args);
        parser.addFlag(Flag.named("-f").withArgument());
        parser.addCmd(Cmd.named("add").withNumberOfArgs(2));
        parser.Parse();
        assertThrows(IllegalArgumentException.class, () -> parser.getFlagArg("-x"));
    }
}
