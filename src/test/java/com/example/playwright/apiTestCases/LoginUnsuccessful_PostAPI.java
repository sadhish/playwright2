package com.example.playwright.apiTestCases;

import com.example.playwright.utilities.PlaywrightFactory;
import com.example.playwright.utilities.SchemaValidation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

@Slf4j
public class LoginUnsuccessful_PostAPI extends PlaywrightFactory {
    APIResponse apiResponse;
    SchemaValidation schemaValidation=new SchemaValidation();

    @BeforeClass
    void setUpApi() {
        initAPI(); //init playwright from playwright factory class
    }

    @Test
    void loginUsuccessfulApiCheck(){
        HashMap<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("email", "peter@klaven");
        apiRequestContextThreadLocal.set(getPlaywright().request().newContext(new APIRequest
                .NewContextOptions().setBaseURL("https://reqres.in/")));
        apiResponse = apiRequestContextThreadLocal.get().post("api/login", RequestOptions.create().setData(reqMap));
        Assert.assertEquals(400,apiResponse.status());
        log.info("Status code is:"+apiResponse.status());
        System.out.println(apiResponse.text());
        JsonObject jsonObject=new Gson().fromJson(apiResponse.text(),JsonObject.class);
        String expectedMsg=jsonObject.get("error").getAsString();
        Assert.assertEquals(expectedMsg,"Missing password");

    }
}
