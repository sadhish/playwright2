package com.example.playwright.utilities;

import com.example.playwright.apiTestCases.Post_API;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
@Slf4j
public class SchemaValidation {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows

    public void validateSchema(String schemaPath, String response,String schemaVersion) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema/"+schemaPath+".json");
        JsonSchema jsonSchema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.valueOf(schemaVersion)).getSchema(inputStream);
        JsonNode jsonNode = objectMapper.readTree(response);
        Set<ValidationMessage> validationMessages = jsonSchema.validate(jsonNode);
        String errorsCombined = "";
        for (ValidationMessage error : validationMessages) {
            log.error("validation errosrs:" + validationMessages);
            System.out.println(error);
            errorsCombined += errorsCombined.toString();
        }
        if (validationMessages.size() > 0) {
            throw new RuntimeException("json errors:" + errorsCombined);
        }
        else{
            log.info("Successful schema validation");
        }
    }
}
