package com.selecaoinvolves.controller;

import com.selecaoinvolves.enums.Commands;

public interface CommandController {
    String execute(Commands command);

    String execute(Commands command, String property);

    String execute(Commands command, String property, String value);
}
