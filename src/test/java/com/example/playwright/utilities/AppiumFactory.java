package com.example.playwright.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AppiumFactory {


    AppiumDriver appiumDriver;
    private static ThreadLocal<AppiumDriver> appiumDriverThreadLocal = new ThreadLocal<>();

    public AppiumDriver getappiumDriver() {
        return appiumDriverThreadLocal.get();
    }

    ReadProperties readProperties = new ReadProperties();

    public AppiumDriver initAppiumAndroidDriver(String platformName, String appPackage,
                                                String appActivity, String UDID, String appName) throws IOException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        URL url = new URL(readProperties.getProperties().getProperty("appiumURL"));
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "pixel");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, UDID);
        desiredCapabilities.setCapability("appPackage", appPackage);
        desiredCapabilities.setCapability("appActivity", appActivity);
        //desiredCapabilities.setCapability("autoGrantPermissions","true");
        String appurl = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "main" + File.separator + "resources" + File.separator + appName + ".apk";
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appurl);
        AppiumDriver appiumDriver = new AppiumDriver(url, desiredCapabilities);
        appiumDriverThreadLocal.set(appiumDriver);
        return appiumDriverThreadLocal.get();
        //return new AppiumDriver(url, desiredCapabilities);
    }

    @SneakyThrows
    public AppiumDriver initAppiumIosDriver(String platformName, String UDID, String appName) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        URL url = new URL(readProperties.getProperties().getProperty("appiumURL"));
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, UDID);
        desiredCapabilities.setCapability("simulatorStartupTimeout", 180000);
//        String appUrlIos = System.getProperty("user.dir") + File.separator + "src" +
//                File.separator + "main" + File.separator + "resources" + File.separator +"UIKitCatalog-iphonesimulator.app";
        String appUrlIos = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "main" + File.separator + "resources" + File.separator + appName + ".app";
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appUrlIos);
        AppiumDriver appiumDriver = new IOSDriver(url, desiredCapabilities);
        appiumDriverThreadLocal.set(appiumDriver);
        return appiumDriverThreadLocal.get();
        // return new IOSDriver(url, desiredCapabilities);
    }

}
