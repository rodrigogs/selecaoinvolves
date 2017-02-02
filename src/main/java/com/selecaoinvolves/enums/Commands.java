package com.selecaoinvolves.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Commands {
    COUNT("count *", "count *", "Count the records in the csv file"),
    COUNT_DISTINCT("count distinct", "count distinct [property]", "Count records in the csv file distincting by the given property"),
    FILTER("filter", "filter [property] [value]", "Filter cvs records by the given property and value"),
    EXIT("exit", "exit", "Exit the program"),
    HELP("help", "help", "Shows help");

    private final String command;
    private final String usage;
    private final String summary;

    Commands(String command, String usage, String summary) {
        this.command = command;
        this.usage = usage;
        this.summary = summary;
    }

    public static Commands find(String command) {
        Optional<Commands> optional = Arrays.stream(Commands.values())
            .filter(cmd -> command.startsWith(cmd.command))
            .findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public String getSummary() {
        return summary;
    }
}
