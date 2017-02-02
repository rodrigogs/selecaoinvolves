package com.selecaoinvolves.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVTest {

    private static final int CSV_HEADERS = 10;
    private static final int CSV_ROWS = 5565;

    @Test
    public void read() throws IOException {
        List<HashMap<String, String>> data = CSV.read(new File(this.getClass().getResource("/cidades-test.csv").getFile()));
        Set<String> headers = data.size() > 0 ? data.get(0).keySet() : new HashSet<>();

        Assert.assertEquals(headers.size(), CSV_HEADERS);
        Assert.assertEquals(data.size(), CSV_ROWS);
    }
}
