package com.spring.library.interfaces;

public interface IDataConvert {
    <T> T getDataFromJson(String response, Class <T> classType);
}
