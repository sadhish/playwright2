package com.example.playwright.mobileAppTestCases;

import com.example.playwright.utilities.AppiumFactory;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.LinkedHashMap;
@Slf4j
@Listeners(ExtentReportListener.class)
public class LoginWithInvalidCredentials extends AppiumFactory {
    PlaywrightFactory playwrightFactory=new PlaywrightFactory();
    private LinkedHashMap<String, String> testData = new LinkedHashMap<>();

    @BeforeSuite
    public static String getClassName() {
        return LoginWithInvalidCredentials.class.getSimpleName();
    }


    @BeforeClass
    void setUp() throws IOException {
        testData = playwrightFactory.getTestData(getClassName());
        initAppiumIosDriver("ios", testData.get("IOSUDID"), testData.get("iosAppName"));
    }

    @SneakyThrows
    @Test
    void loginAppWithInvalidCredentials() {
        getappiumDriver()
                .findElement(By.xpath("//XCUIElementTypeTextField[@name=\"test-Username\"]"))
                .sendKeys(testData.get("username"));
        getappiumDriver()
                .findElement(By.xpath("//XCUIElementTypeSecureTextField[@name=\"test-Password\"]"))
                .sendKeys(testData.get("password"));
        getappiumDriver().findElement(By.xpath("//XCUIElementTypeOther[@name=\"test-LOGIN\"]")).click();
        String expectedMsg=getappiumDriver().findElement
                (By.xpath("//XCUIElementTypeStaticText[@name=\"Username and password do not match any user in this service.\"]")).getText();
       log.info("Expected Message is"+expectedMsg);
        Assert.assertEquals(expectedMsg,"Username and password do not match any user in this service.");

    }
    @AfterClass
    void tearDown(){
        if(getappiumDriver()!=null) {
            getappiumDriver().quit();
        }
    }

}


