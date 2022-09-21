package com.example.playwright.apiTestCases;

import com.example.playwright.utilities.PlaywrightFactory;
import com.example.playwright.utilities.SchemaValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
@Slf4j
public class Post_API_WithInvalidPayload  extends PlaywrightFactory {
    APIResponse apiResponse;
    SchemaValidation schemaValidation=new SchemaValidation();

    @BeforeClass
    void setUpApi() {
        initAPI(); //init playwright from playwright factory class
    }

    @Test
    void postApiCallWithInvalidPayload() throws JSONException, JsonProcessingException {
        HashMap<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("name", "any");
        reqMap.put("gender","male");
        reqMap.put("status","active");
        apiRequestContextThreadLocal.set(getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://gorest.co.in"))); //set apirequest value to threadlocal
        //String response = apiRequestContextThreadLocal.get().post("/posts", RequestOptions.create().setData(reqMap)).text();
        apiResponse = apiRequestContextThreadLocal.get()
                .post("/public-api/users", RequestOptions.create()
                        .setData(reqMap));
        System.out.println(apiResponse.statusText()+","+apiResponse.status());
        System.out.println(apiResponse.text());
        JsonObject jsonObject=new Gson().fromJson(apiResponse.text(),JsonObject.class);
        System.out.println(jsonObject.get("code").getAsString());
        //schemaValidation.validateSchema("postApiCallWithInvalidPayload",apiResponse.text(),"V4");

    }

    @AfterMethod
    void tearDown() {
        getApiRequestContext().dispose();
        getPlaywright().close();

    }
}
