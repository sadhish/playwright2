package com.example.playwright.testCases;

import com.example.playwright.pageObjectModel.SwagLabsLogin;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.LinkedHashMap;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(ExtentReportListener.class)
public class CheckProductsPage extends PlaywrightFactory {

    private LinkedHashMap<String,String> testData=new LinkedHashMap<>();
    SwagLabsLogin swagLabsLogin;
    @BeforeSuite
    public static String getClassName(){return SwagLabs.class.getSimpleName();}


    @BeforeClass
    void setUp() throws IOException {
        initBrowser("chrome");
        swagLabsLogin=new SwagLabsLogin(getPage());
        testData=getTestData(getClassName());
    }
    @Test
    void swagLabsProducts() throws Exception {
        try {
            swagLabsLogin.doLogin(testData);
            System.out.println(getPage().locator("text=Products").textContent());
            assertThat(getPage().locator("text=Products")).hasText("Products");

        }catch (Exception e){
            throw new Exception(e);
        }
    }
    @AfterClass
    void tearDown(){
        getPage().close();
        getBrowserContext().close();
       getBrowser().close();
    }

}
