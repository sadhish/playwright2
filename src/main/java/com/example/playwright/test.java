package com.example.playwright;

import org.springframework.beans.factory.annotation.Value;

public class test {

    @Value("${appiumURL}")
    public  String appiumUrl;

    public  String initAppiumDriver(){
        System.out.println(appiumUrl);
        return appiumUrl;
    }
static test test=new test();
    public static void main(String[] args) {
     System.out.println(test.initAppiumDriver());

    }
}
