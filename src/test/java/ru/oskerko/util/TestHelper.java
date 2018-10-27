package ru.oskerko.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestHelper {

    public static Properties readProperties(String filename){
        Properties properties = new Properties();
        InputStream input = TestHelper.class.getClassLoader().getResourceAsStream(filename);
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
