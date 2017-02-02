package com.selecaoinvolves.service;

import com.selecaoinvolves.enums.Commands;
import com.selecaoinvolves.exception.CommandException;
import com.selecaoinvolves.service.impl.CommandServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CommandServiceTest {

    private static final String COUNT_RESULT = "There are 5565 records in the csv file";
    private static final String COUNT_DISTINCT_BY_UF_RESULT = "There are 27 distinct by uf records in the csv file";
    private static final String FILTER_BY_IBGE_ID_RESULT = "Filtered results:" + System.lineSeparator() + "[Cacoal, RO, , Alta Floresta D'Oeste, 1100015, Alta Floresta D'Oeste, -61.9998238963, , -11.9355403048, Leste Rondoniense]";
    private static final String FILTER_BY_UNEXISTING_RESULT = "Filtered results:" + System.lineSeparator();
    private static final String MISSING_ARGUMENTS_ERROR = "Missing arguments";
    private static final String INVALID_PROPERTY_ERROR = "Property invalid_prop does not exist";

    private static CommandService commandService;

    @BeforeClass
    public static void setUp() throws IOException {
        File csvFile = new File(CommandServiceTest.class.getResource("/cidades-test.csv").getFile());
        commandService = new CommandServiceImpl(csvFile);
    }

    @Test(expected = IOException.class)
    public void newInstanceFileNotFound() throws IOException {
        File csvFile = new File("404");
        new CommandServiceImpl(csvFile);
    }

    @Test
    public void count() throws CommandException {
        String result = commandService.execute(Commands.COUNT, "");
        Assert.assertEquals(result, COUNT_RESULT);
    }

    @Test
    public void countDistinctByUf() throws CommandException {
        String result = commandService.execute(Commands.COUNT_DISTINCT, "uf");
        Assert.assertEquals(result, COUNT_DISTINCT_BY_UF_RESULT);
    }

    @Test(expected = CommandException.class)
    public void countDistinctByInvalidProperty() throws CommandException {
        try {
            commandService.execute(Commands.COUNT_DISTINCT, "invalid_prop");
        } catch (CommandException ex) {
            Assert.assertEquals(ex.getMessage(), INVALID_PROPERTY_ERROR);
            throw ex;
        }
    }

    @Test(expected = CommandException.class)
    public void countDistinctWithoutProperty() throws CommandException {
        try {
            commandService.execute(Commands.COUNT_DISTINCT, "");
        } catch (CommandException ex) {
            Assert.assertEquals(ex.getMessage(), MISSING_ARGUMENTS_ERROR);
            throw ex;
        }
    }

    @Test
    public void filterByIbgeId() throws CommandException {
        String result = commandService.execute(Commands.FILTER, "ibge_id 1100015");
        Assert.assertEquals(result, FILTER_BY_IBGE_ID_RESULT);
    }

    @Test
    public void filterByUnexistentValue() throws CommandException {
        String result = commandService.execute(Commands.FILTER, "uf unexistent_value");
        Assert.assertEquals(result, FILTER_BY_UNEXISTING_RESULT);
    }

    @Test(expected = CommandException.class)
    public void filterByIbgeIdWithoutValue() throws CommandException {
        try {
            commandService.execute(Commands.FILTER, "ibge_id");
        } catch (CommandException ex) {
            Assert.assertEquals(ex.getMessage(), MISSING_ARGUMENTS_ERROR);
            throw ex;
        }
    }

    @Test(expected = CommandException.class)
    public void filterByIbgeIdWithoutPropertyAndValue() throws CommandException {
        try {
            commandService.execute(Commands.FILTER, "");
        } catch (CommandException ex) {
            Assert.assertEquals(ex.getMessage(), MISSING_ARGUMENTS_ERROR);
            throw ex;
        }
    }

    @Test(expected = CommandException.class)
    public void filterByInvalidProperty() throws CommandException {
        try {
            commandService.execute(Commands.FILTER, "invalid_prop value");
        } catch (CommandException ex) {
            Assert.assertEquals(ex.getMessage(), INVALID_PROPERTY_ERROR);
            throw ex;
        }
    }
}
