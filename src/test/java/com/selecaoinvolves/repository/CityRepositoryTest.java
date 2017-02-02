package com.selecaoinvolves.repository;

import com.selecaoinvolves.repository.impl.CityRepositoryImpl;
import com.selecaoinvolves.service.CommandServiceTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityRepositoryTest {

    private static final long COUNT_RESULT = 5565L;
    private static final long COUNT_DISTINCT_BY_UF_RESULT = 27;
    private static final String INVALID_PROPERTY_ERROR = "Property invalid_prop does not exist";
    private static final List<HashMap<String, String>> FILTER_BY_UNEXISTING_RESULT = new ArrayList<>();
    private static final List<HashMap<String, String>> FILTER_BY_IBGE_ID_RESULT = new ArrayList<HashMap<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("ibge_id", "1100015");
            put("uf", "RO");
            put("name", "Alta Floresta D'Oeste");
            put("capital", "");
            put("lon", "-61.9998238963");
            put("lat", "-11.9355403048");
            put("no_accents", "Alta Floresta D'Oeste");
            put("alternative_names", "");
            put("microregion", "Cacoal");
            put("mesoregion", "Leste Rondoniense");
        }});
    }};

    private static CityRepository cityRepository;

    @BeforeClass
    public static void setUp() throws IOException {
        File csvFile = new File(CommandServiceTest.class.getResource("/cidades-test.csv").getFile());
        cityRepository = new CityRepositoryImpl(csvFile);
    }

    @Test(expected = IOException.class)
    public void newInstanceFileNotFound() throws IOException {
        File csvFile = new File("404");
        new CityRepositoryImpl(csvFile);
    }

    @Test
    public void count() {
        long count = cityRepository.count();
        Assert.assertEquals(count, COUNT_RESULT);
    }

    @Test
    public void countDistinctByUf() throws Exception {
        long count = cityRepository.countDistinct("uf");
        Assert.assertEquals(count, COUNT_DISTINCT_BY_UF_RESULT);
    }

    @Test(expected = Exception.class)
    public void countDistinctByInvalidProperty() throws Exception {
        try {
            cityRepository.countDistinct("invalid_prop");
        } catch (Exception ex) {
            Assert.assertEquals(ex.getMessage(), INVALID_PROPERTY_ERROR);
            throw ex;
        }
    }

    @Test
    public void filterByIbgeId() throws Exception {
        List<HashMap<String, String>> result = cityRepository.filter("ibge_id", "1100015");
        Assert.assertEquals(result, FILTER_BY_IBGE_ID_RESULT);
    }

    @Test(expected = Exception.class)
    public void filterByInvalidProperty() throws Exception {
        try {
            cityRepository.filter("invalid_prop", "1100015");
        } catch (Exception ex) {
            Assert.assertEquals(ex.getMessage(), INVALID_PROPERTY_ERROR);
            throw ex;
        }
    }

    @Test
    public void filterByUnexistentValue() throws Exception {
        List<HashMap<String, String>> result = cityRepository.filter("uf", "unexistent_value");
        Assert.assertEquals(result, FILTER_BY_UNEXISTING_RESULT);
    }
}
