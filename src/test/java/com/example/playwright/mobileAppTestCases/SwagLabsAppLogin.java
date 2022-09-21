package com.example.playwright.mobileAppTestCases;
import com.example.playwright.utilities.AppiumFactory;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.LinkedHashMap;

@Listeners(ExtentReportListener.class)
public class SwagLabsAppLogin extends AppiumFactory {

    PlaywrightFactory playwrightFactory=new PlaywrightFactory();
    private LinkedHashMap<String, String> testData = new LinkedHashMap<>();

    @BeforeSuite
    public static String getClassName() {
        return SwagLabsAppLogin.class.getSimpleName();
    }

    @BeforeClass @Parameters({"platform-a","platform-b"})
    void setUp() throws IOException {
        testData = playwrightFactory.getTestData(getClassName());
        initAppiumIosDriver("ios", testData.get("IOSUDID"), testData.get("iosAppName"));
    }

    @SneakyThrows
    @Test
    void loginApp() {

       getappiumDriver()
               .findElement(By.xpath("//XCUIElementTypeTextField[@name=\"test-Username\"]"))
               .sendKeys(testData.get("username"));
        getappiumDriver()
                .findElement(By.xpath("//XCUIElementTypeSecureTextField[@name=\"test-Password\"]"))
                .sendKeys(testData.get("password"));
        getappiumDriver().findElement(By.xpath("//XCUIElementTypeOther[@name=\"test-LOGIN\"]")).click();
        getappiumDriver().findElement(By.xpath("//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")).click();


    }
@AfterClass
    void tearDown(){
    if(getappiumDriver()!=null) {
        getappiumDriver().quit();
    }
}

}
