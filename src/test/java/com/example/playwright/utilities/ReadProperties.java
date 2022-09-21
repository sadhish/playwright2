package com.example.playwright.utilities;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Component
public class ReadProperties {

   private InputStream inputStream;
   private Properties properties;
    public Properties getProperties() throws IOException {
        inputStream=getClass().getClassLoader().getResourceAsStream("application.properties");
        properties=new Properties();
        properties.load(inputStream);
        return properties;
    }


}
