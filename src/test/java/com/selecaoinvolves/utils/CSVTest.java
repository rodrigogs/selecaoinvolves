package com.selecaoinvolves;

import com.selecaoinvolves.utils.CSV;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVTest extends TestCase {

    private static final int CSV_HEADERS = 10;
    private static final int CSV_ROWS = 5565;

    public void testRead() throws IOException {
        List<HashMap<String, String>> data = CSV.read(new File(this.getClass().getResource("/cidades.csv").getFile()));
        Set<String> headers = data.size() > 0 ? data.get(0).keySet() : new HashSet<>();

        assertEquals(headers.size(), CSV_HEADERS);
        assertEquals(data.size(), CSV_ROWS);
    }
}
