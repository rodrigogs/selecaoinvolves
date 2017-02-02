package com.selecaoinvolves.utils.service;

import com.selecaoinvolves.service.CommandService;
import com.selecaoinvolves.service.impl.CommandServiceImpl;
import junit.framework.TestCase;

import java.io.IOException;

public class CommandServiceTest extends TestCase {
    private CommandService commandService;

    protected void setUp() throws IOException {
        commandService = new CommandServiceImpl();
    }

    public void testCount() {

    }
}
