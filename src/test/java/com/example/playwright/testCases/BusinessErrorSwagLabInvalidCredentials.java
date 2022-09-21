package com.example.playwright.testCases;

import com.example.playwright.pageObjectModel.SwagLabsLogin;
import com.example.playwright.utilities.PlaywrightFactory;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.LinkedHashMap;

@Slf4j
public class BusinessErrorSwagLabInvalidCredentials extends PlaywrightFactory {

    SwagLabsLogin swagLabsLogin;
    private LinkedHashMap<String,String> testData=new LinkedHashMap<>();

    @BeforeSuite
    public static String getClassName(){return BusinessErrorSwagLabInvalidCredentials.class.getSimpleName();}

    @BeforeClass
    void setUp() throws IOException {
        initBrowser("chrome");
        swagLabsLogin=new SwagLabsLogin(getPage());
        testData=getTestData(getClassName());
    }

@Test
    void checkInvalidCredentialsMessage(){
        swagLabsLogin.doLogin(testData);
        String expectedErrorMsg=getPage().locator("//*[@id=\"login_button_container\"]/div/form/div[3]/h3").textContent();
        log.info("ErrorMsgis:"+expectedErrorMsg);
        Assert.assertEquals(expectedErrorMsg,"Epic sadface: Username and password do not match any user in this service");
}
    @AfterClass
    void tearDown() {
        getPage().close();
        getBrowserContext().close();
        getBrowser().close();
    }
}
