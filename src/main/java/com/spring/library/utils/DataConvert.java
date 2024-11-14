package com.spring.library.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.library.interfaces.IDataConvert;

public class DataConvert implements IDataConvert {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getDataFromJson(String response, Class<T> classType) {
        try {
            return mapper.readValue(response, classType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
