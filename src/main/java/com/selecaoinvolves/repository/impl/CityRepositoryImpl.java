package com.selecaoinvolves.repository.impl;

import com.selecaoinvolves.repository.intf.Cities;
import com.selecaoinvolves.utils.CSV;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CitiesRepository implements Cities {

    private List<HashMap<String, String>> cities;

    public CitiesRepository() throws IOException {
        this.cities = CSV
            .read(new File(this.getClass().getResource("/cidades.csv").getFile()));
    }

    @Override
    public long count() {
        return cities.size();
    }

    @Override
    public long countDistinct(String property) {
        return this.cities
            .stream()
            .map(city -> city.get(property))
            .distinct()
            .count();
    }

    @Override
    public List<HashMap<String, String>> filter(String property, String value) {
        return this.cities
            .stream()
            .filter(city -> city.get(property).equals(value))
            .collect(Collectors.toList());
    }
}
