package com.selecaoinvolves.service.impl;

import com.selecaoinvolves.enums.Commands;
import com.selecaoinvolves.exception.CommandException;
import com.selecaoinvolves.repository.CityRepository;
import com.selecaoinvolves.repository.impl.CityRepositoryImpl;
import com.selecaoinvolves.service.CommandService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CommandServiceImpl implements CommandService {

    private final String breakLine = System.lineSeparator();
    private CityRepository cityRepository;

    public CommandServiceImpl(File csvFile) throws IOException {
        this.cityRepository = new CityRepositoryImpl(csvFile);
    }

    @Override
    public String execute(Commands command, String input) throws CommandException {
        String[] args = parseArgs(command, input);

        if (command.equals(Commands.COUNT)) return count();

        if (command.equals(Commands.COUNT_DISTINCT)) {
            if (args.length < 1) throw new CommandException("Missing arguments");
            return countDistinct(args[0]);
        }

        if (command.equals(Commands.FILTER)) {
            if (args.length < 2) throw new CommandException("Missing arguments");
            return filter(args[0], args[1]);
        }

        if (command.equals(Commands.HELP)) return help();

        if (command.equals(Commands.EXIT)) return "Exiting program...";

        throw new CommandException("Command not found");
    }

    private String count() {
        long count = cityRepository.count();
        return "There are " + count + " records in the csv file";
    }

    private String countDistinct(String property) throws CommandException {
        long count = 0;
        try {
            count = cityRepository.countDistinct(property);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return "There are " + count + " distinct by " + property + " records in the csv file";
    }

    private String filter(String property, String value) throws CommandException {
        List<HashMap<String, String>> records = null;
        try {
            records = cityRepository.filter(property, value);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return "Filtered results:" + breakLine +
            records.stream()
                .map(record -> record.values().toString())
                .collect(Collectors.joining(breakLine));
    }

    private String help() {
        return "Available commands:" + breakLine
            + Arrays.stream(Commands.values())
            .map(cmd -> cmd.getUsage() + " : " + cmd.getSummary())
            .collect(Collectors.joining(breakLine));
    }

    private String[] parseArgs(Commands command, String input) {
        String[] args = Arrays.stream(input
            .replaceFirst(command.getCommand(), "")
            .trim()
            .split(" "))
            .filter(arg -> !arg.isEmpty())
            .toArray(String[]::new);

        if (command.equals(Commands.FILTER)) {
            if (args.length < 2) return new String[]{};
            args = new String[]{
                args[0].trim(),
                String.join(" ", Arrays.copyOfRange(args, 1, 2)).trim()
            };
        }

        return args;
    }
}