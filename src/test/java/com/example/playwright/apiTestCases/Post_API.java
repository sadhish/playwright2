package com.example.playwright.apiTestCases;

import com.example.playwright.testCases.SwagLabs;
import com.example.playwright.utilities.ExtentReportListener;
import com.example.playwright.utilities.PlaywrightFactory;
import com.example.playwright.utilities.SchemaValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import com.networknt.schema.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.HashMap;
@Slf4j
@Listeners(ExtentReportListener.class)
public class Post_API extends PlaywrightFactory {
    APIResponse apiResponse;
    SchemaValidation schemaValidation=new SchemaValidation();

    @BeforeClass
    void setUpApi() {
        initAPI(); //init playwright from playwright factory class
    }

    @Test
    void postApiCall() throws JSONException, JsonProcessingException {
        HashMap<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("title", "any");
        reqMap.put("body", "body");
        reqMap.put("userId", "kkk");
        //String reqStr=new ObjectMapper().writeValueAsString(reqMap);
        apiRequestContextThreadLocal.set(getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://jsonplaceholder.typicode.com"))); //set apirequest value top threadlocal
        //String response = apiRequestContextThreadLocal.get().post("/posts", RequestOptions.create().setData(reqMap)).text();
        apiResponse = apiRequestContextThreadLocal.get().post("/posts", RequestOptions.create().setData(reqMap));
        Assert.assertEquals(201,apiResponse.status());
        log.info("Status code is"+apiResponse.status());
        System.out.println("status"+apiResponse.text());
        String response= apiResponse.text();
        schemaValidation.validateSchema("schema",response,"V4");
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        System.out.println(jsonObject.get("userId"));
//       boolean expected= jsonObject.get("userId").toString().equalsIgnoreCase("kkk");
//       Assert.assertEquals(expected,true);
        jsonObject.entrySet().stream().forEach(e -> System.out.println(e.getKey().toString()+","+e.getValue()));

    }

    @AfterMethod
    void tearDown() {
        getApiRequestContext().dispose();
        getPlaywright().close();

    }

}
