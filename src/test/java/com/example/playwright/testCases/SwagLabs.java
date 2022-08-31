package com.example.playwright.testCases;

import com.example.playwright.pageObjectModel.SwagLabsLogin;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.LinkedHashMap;

@Listeners(ExtentReportListener.class)

public class SwagLabs extends PlaywrightFactory {

    SwagLabsLogin swagLabsLogin;
    private LinkedHashMap<String,String> testData=new LinkedHashMap<>();

    @BeforeSuite
    public static String getClassName(){return SwagLabs.class.getSimpleName();}

    @BeforeClass
    void setUp() throws IOException {
    initBrowser("chrome");
    swagLabsLogin=new SwagLabsLogin(getPage());
    testData=getTestData(getClassName());
    }

    @Test
    void swagLabsLogin() throws Exception {
        try {
           swagLabsLogin.doLogin(testData);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @AfterClass
    void tearDown(){
        getPage().close();
        getBrowser().close();
    }
}
