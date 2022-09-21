package com.example.playwright.testCases;

import com.example.playwright.pageObjectModel.SwagLabsLogin;
import com.example.playwright.utilities.PlaywrightFactory;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Hdfctest extends PlaywrightFactory {
    @BeforeClass
    void setUp() throws IOException {

        initBrowser("chrome");

    }

    @Test
    void openPage(){
        getPage().navigate("https://leads.hdfcbank.com/applications/misc/LST/loantracker.aspx");
        getPage().locator("//*[@id=\"txtLoanAppRefNo\"]").fill("133948525");
        getPage().locator("//*[@id=\"txtMobNo\"]").fill("9443714134");
        getPage().locator("//*[@id=\"btnSubTracking\"]").click();

        System.out.println(getPage().locator("//*[@id=\"lblstatus\"]").textContent());

    }
}

