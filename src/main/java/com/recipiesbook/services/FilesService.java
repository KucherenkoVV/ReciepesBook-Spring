package com.recipiesbook.services;

import com.recipiesbook.exception.NotFindFileException;

import java.io.File;
import java.io.IOException;

public interface FilesService {

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName) ;

    boolean cleanDataFile(String dataFileName);

    File getDataFile();
}
