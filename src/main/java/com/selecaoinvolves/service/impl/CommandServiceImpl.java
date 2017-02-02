package com.selecaoinvolves.controller.impl;

import com.selecaoinvolves.controller.CommandController;
import com.selecaoinvolves.enums.Commands;
import com.selecaoinvolves.repository.CityRepository;
import com.selecaoinvolves.repository.impl.CityRepositoryImpl;

import java.io.IOException;

public class CommandControllerImpl implements CommandController {

    private CityRepository cityRepository;

    public CommandControllerImpl() throws IOException {
        this.cityRepository = new CityRepositoryImpl();
    }

    @Override
    public String execute(Commands command) {
        String result = "";
        if (command.equals(Commands.COUNT)) {
            
        }
        return null;
    }

    @Override
    public String execute(Commands command, String property) {
        return null;
    }

    @Override
    public String execute(Commands command, String property, String value) {
        return null;
    }
}
