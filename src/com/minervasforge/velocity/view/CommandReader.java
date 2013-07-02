package com.minervasforge.velocity.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.apache.commons.lang3.StringUtils.join;

public class CommandReader {
    private BufferedReader bufferedReader;
    private StringTokenizer stringTokenizer;


    public CommandReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public Command nextCommand() {

        try {
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        } catch (IOException e) {
            return new Command("");
        }

        String commandName = getCommandName();

        return (stringTokenizer.hasMoreTokens()) ? getCommandWithArguments(commandName) : new Command(commandName);
    }

    private Command getCommandWithArguments(String command) {
        List<String> arguments = new ArrayList<String>();

        while (stringTokenizer.hasMoreTokens()) {
            arguments.add(stringTokenizer.nextToken());
        }

        return new Command(command, join(arguments, " "));
    }

    private String getCommandName() {
        String firstWord = stringTokenizer.nextToken();

        return "add".equals(firstWord) ? "add " + stringTokenizer.nextToken() : firstWord;
    }
}