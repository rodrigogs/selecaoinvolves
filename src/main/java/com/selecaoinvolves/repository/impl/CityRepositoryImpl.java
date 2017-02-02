package com.selecaoinvolves.repository.impl;

import com.selecaoinvolves.repository.CityRepository;
import com.selecaoinvolves.utils.CSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CityRepositoryImpl implements CityRepository {

    private List<String> headers = new ArrayList<>();
    private List<HashMap<String, String>> cities;

    public CityRepositoryImpl(File csvFile) throws IOException {
        this.cities = CSV.read(csvFile);
        if (this.cities.size() > 0) {
            this.headers = this.cities.get(0).keySet().stream().collect(Collectors.toList());
        }
    }

    @Override
    public long count() {
        return cities.size();
    }

    @Override
    public long countDistinct(String property) throws Exception {
        if (!headers.contains(property)) throw new Exception("Property " + property + " does not exist");
        return this.cities
            .stream()
            .map(city -> city.get(property))
            .distinct()
            .count();
    }

    @Override
    public List<HashMap<String, String>> filter(String property, String value) throws Exception {
        if (!headers.contains(property)) throw new Exception("Property " + property + " does not exist");
        return this.cities
            .stream()
            .filter(city -> city.get(property).equals(value))
            .collect(Collectors.toList());
    }
}
