package com.selecaoinvolves.utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {

    //            Rows Row     Header  Value
    public static List<HashMap<String, String>> read(File csvFile) throws IOException {
        InputStream input = new FileInputStream(csvFile);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        String[] headers = buffer.readLine().split(",");
        return buffer
            .lines()
            .map(line -> line.split(","))
            .filter(values -> values.length == headers.length)
            .map(values -> {
                HashMap<String, String> row = new HashMap<>();
                for (int i = 0; i < values.length; i++) {
                    row.put(headers[i], values[i]);
                }
                return row;
            })
            .collect(Collectors.toList());
    }
}
