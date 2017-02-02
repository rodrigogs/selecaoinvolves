package com.selecaoinvolves.repository.intf;

import java.util.HashMap;
import java.util.List;

public interface Cities {
    long count();

    long countDistinct(String property);

    List<HashMap<String, String>> filter(String property, String value);
}
