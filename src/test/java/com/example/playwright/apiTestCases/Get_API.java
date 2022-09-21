package com.example.playwright.apiTestCases;

import com.example.playwright.utilities.PlaywrightFactory;
import com.example.playwright.utilities.SchemaValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class Get_API extends PlaywrightFactory {
    APIResponse apiResponse;
    ObjectMapper objectMapper = new ObjectMapper();
    SchemaValidation schemaValidation = new SchemaValidation();

    @BeforeClass
    void setUpApi() {
        initAPI(); //init playwright from playwright factory class
    }

    @Test
    void getApiCall() throws JSONException, JsonProcessingException {
        apiRequestContextThreadLocal.set(getPlaywright().request().newContext(new APIRequest
                .NewContextOptions().setBaseURL("https://reqres.in/")));
        apiResponse = apiRequestContextThreadLocal.get().get("api/users?page=2");
        Assert.assertEquals(200, apiResponse.status());
        log.info("Status code is:" + apiResponse.status());
        schemaValidation.validateSchema("GetAPI_Schema", apiResponse.text(), "V6");
        //  JsonNode jsonNode = objectMapper.readValue(objectMapper.writeValueAsString(apiResponse.text()), JsonNode.class);
        JSONObject jsonObject = new JSONObject(apiResponse.text().toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        // System.out.println(jsonArray);
        JSONObject object = jsonArray.getJSONObject(0);
        System.out.println(object.get("last_name"));

    }
}
