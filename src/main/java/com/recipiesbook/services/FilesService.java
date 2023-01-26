package com.recipiesbook.services;

public interface FilesService {

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);
}
