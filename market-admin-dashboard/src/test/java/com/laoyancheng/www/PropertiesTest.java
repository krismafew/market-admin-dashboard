package com.laoyancheng.www;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/22 16:29
 */
public class PropertiesTest {
    @Test
    public void testProperties() throws IOException {
        ClassLoader classLoader = PropertiesTest.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String filePath = properties.getProperty("filePath");

        filePath += "/1.jpg";

        File file = new File(filePath);
        file.mkdirs();
    }
}
