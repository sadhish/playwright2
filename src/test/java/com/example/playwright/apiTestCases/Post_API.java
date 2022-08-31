package com.example.playwright.apiTestCases;

import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;

@Listeners(ExtentReportListener.class)
public class Post_API extends PlaywrightFactory {

    Playwright playwright;
    APIRequestContext apiRequestContext;

    @BeforeClass
    void setUpApi() {
        initAPI(); //init playwright from playwright factory class
    }

    @Test
    void getApiCall() throws JSONException {
        HashMap<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("title", "any");
        reqMap.put("body", "body");
        reqMap.put("userId", "kkk");

//       apiRequestContext=getPlaywright().request().newContext(new APIRequest.NewContextOptions()
//            .setBaseURL("https://jsonplaceholder.typicode.com"));
//      String response=apiRequestContext.post("/posts", RequestOptions.create().setData(reqMap)).text();


        apiRequestContextThreadLocal.set(getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://jsonplaceholder.typicode.com"))); //set apirequest value top threadlocal
        String response = apiRequestContextThreadLocal.get().post("/posts", RequestOptions.create().setData(reqMap)).text();


        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        System.out.println(jsonObject.get("userId"));
        jsonObject.entrySet().stream().forEach(e -> System.out.println(e.getKey().toString()));

    }

    @AfterMethod
    void tearDown() {
        getApiRequestContext().dispose();
        getPlaywright().close();


    }

}
