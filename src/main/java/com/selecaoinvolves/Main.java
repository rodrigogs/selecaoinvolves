package com.selecaoinvolves;

import com.selecaoinvolves.enums.Commands;
import com.selecaoinvolves.exception.CommandException;
import com.selecaoinvolves.service.CommandService;
import com.selecaoinvolves.service.impl.CommandServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {

    private static final String breakLine = System.lineSeparator();

    private static Scanner scanner;
    private static CommandService commandService;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        try {
            String resourcePath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
            if (new File(resourcePath + "/cidades.csv").exists()) {
                resourcePath = resourcePath + "/cidades.csv";
            } else {
                resourcePath = Main.class.getResource("/cidades.csv").getFile();
            }

            commandService = new CommandServiceImpl(new File(resourcePath));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }

        watchCommands();
    }

    private static void watchCommands() {
        Commands command = null;
        while (command != Commands.EXIT) {
            System.out.println("Enter a command:");
            String input = scanner.nextLine();
            command = Commands.find(input);
            if (command == null) {
                consoleLog("Invalid command. Type \"help\" to get a list of available commands.");
                continue;
            }
            try {
                consoleLog(commandService.execute(command, input));
            } catch (CommandException ex) {
                consoleLog(ex.getMessage());
            }
        }
    }

    private static void consoleLog(String info) {
        System.out.println(info + breakLine);
    }
}
