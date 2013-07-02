package com.minervasforge.velocity.userInterface;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Command {
    private String commandName;
    private String arguments;

    public Command(String commandName) {
        this.commandName = commandName;
    }
    public Command(String commandName, String commandArguments) {
        this.commandName = commandName;
        this.arguments = commandArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandName='" + commandName + '\'' +
                ", arguments='" + arguments + '\'' +
                '}';
    }
}