package com.selecaoinvolves.repository;

import java.util.HashMap;
import java.util.List;

public interface CityRepository {
    long count();

    long countDistinct(String property) throws Exception;

    List<HashMap<String, String>> filter(String property, String value) throws Exception;
}
