package com.recipiesbook.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName) ;

    boolean cleanDataFile(String dataFileName);

    File getDataFile();

    Path createTempFile(String suffix);
}
