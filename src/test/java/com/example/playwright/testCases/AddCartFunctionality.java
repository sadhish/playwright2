package com.example.playwright.testCases;

import com.example.playwright.pageObjectModel.SwagLabsLogin;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.LinkedHashMap;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(ExtentReportListener.class)
public class AddCartFunctionality extends PlaywrightFactory {
    SwagLabsLogin swagLabsLogin;
    private LinkedHashMap<String, String> testData = new LinkedHashMap<>();

    @BeforeSuite
    public static String getClassName() {
        return SwagLabs.class.getSimpleName();
    }

    @BeforeClass
    void setUp() throws IOException {
        initBrowser("chrome");
        swagLabsLogin = new SwagLabsLogin(getPage());
        testData = getTestData(getClassName());
    }

    @Test
    void addToCartFunctionality() {
        swagLabsLogin.doLogin(testData);
        assertThat(getPage().locator("//*[@id=\"item_4_title_link\"]/div")).hasText("Sauce Labs Backpack");
        getPage().locator("//*[@id=\"add-to-cart-sauce-labs-backpack\"]").click();
        getPage().locator("//*[@id=\"shopping_cart_container\"]/a").click();
        assertThat(getPage().locator("//*[@id=\"item_4_title_link\"]/div")).hasText("Sauce Labs Backpack");
        assertThat(getPage().locator("//*[@id=\"checkout\"]")).isVisible();

    }

    @AfterClass
    void tearDown() {
        getPage().close();
        getBrowserContext().close();
        getBrowser().close();
    }
}
