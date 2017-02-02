package com.selecaoinvolves.service;

import com.selecaoinvolves.enums.Commands;
import com.selecaoinvolves.exception.CommandException;

public interface CommandService {
    String execute(Commands command, String input) throws CommandException;
}
