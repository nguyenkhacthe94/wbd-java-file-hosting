package com.upload.utils;

import java.io.File;

public class StorageUtils {
    public static final String FEATURE_LOCATION = "E:/upload-features";
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex);
    }

    public static void removeFeature(String fileName) {
        File file = new File(FEATURE_LOCATION + "/" + fileName);
        if(file.exists()){
            file.delete();
        }
    }
}
