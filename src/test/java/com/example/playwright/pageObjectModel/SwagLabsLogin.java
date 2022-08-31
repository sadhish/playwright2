package com.example.playwright.pageObjectModel;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.LinkedHashMap;

public class SwagLabsLogin  {
    private final Page page;

    public SwagLabsLogin(Page page) {
        this.page = page;
    }
    public void doLogin(LinkedHashMap<String, String> testData){
       page.navigate(testData.get("url"));
        page.locator("[data-test=\"username\"]").type(testData.get("username"));
        page.locator("[data-test=\"password\"]").type(testData.get("password"));
        Locator locator = page.locator("[data-test=\"login-button\"]");
        locator.click();
    }
}
