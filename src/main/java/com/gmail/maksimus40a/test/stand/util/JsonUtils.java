package com.gmail.maksimus40a.test.stand.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {

    }

    static public <T> List<T> mapJsonFileToListEntities(String pathToFile, Class cl) throws IOException {
        URL urlToSource = JsonUtils.class.getClassLoader().getResource(pathToFile);
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, cl);
        return objectMapper.readValue(urlToSource, collectionType);
    }
}