package com.example.playwright.utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlaywrightFactory {
     Playwright playwright;
      Browser browser;
     BrowserContext browserContext;
      Page page;



    APIRequestContext apiRequestContext;
    LinkedHashMap<String, String> result = new LinkedHashMap<>();
    ObjectMapper objectMapper=new ObjectMapper();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    public static ThreadLocal<APIRequestContext> apiRequestContextThreadLocal=new ThreadLocal<>();
    public static  ThreadLocal<Response> responseThreadLocal=new ThreadLocal<>();
    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static APIRequestContext getApiRequestContext() {
        return apiRequestContextThreadLocal.get();
    }
    public void initAPI(){
        tlPlaywright.set(Playwright.create());
    }


    public Page initBrowser(String browserName) {
        tlPlaywright.set(Playwright.create());
        switch (browserName.toLowerCase()) {
            case "chrome":
                //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100)));
                break;
            case "webkit":
                //browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            default:
                System.out.println("please pass the right browser name......");
                break;

        }

        tlBrowserContext.set(getBrowser().newContext());
        tlPage.set(getBrowserContext().newPage());
        return getPage();
    }

    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".jpg";
        if (getPage()!=null) {
            getPage().screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(path))
                    .setFullPage(true));
            return path;
        }
        return null;
    }
    public LinkedHashMap<String, String> getTestData(String className) throws IOException
    {
        JsonFactory jsonFactory = objectMapper.getFactory();
        String path = System.getProperty("user.dir");
        File file = ResourceUtils.getFile(path + "//test-input//testdata.json");
        JsonNode jsonNode = objectMapper.readValue(new String(Files.readAllBytes(file.toPath())), JsonNode.class);
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if(entry.getKey().equalsIgnoreCase(className)){
                result=objectMapper.
                        convertValue(entry.getValue(), new TypeReference<LinkedHashMap<String, String>>(){});

                return result;
            }

        }
        return null;
    }


}
