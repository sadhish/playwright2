package com.example.playwright.utilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

public class ExtentReportListener extends PlaywrightFactory implements ITestListener {

    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";
    private  static ExtentReports extentReports = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    private static ExtentReports init() {

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Playwright Automation Test Results");
        extentReports.attachReporter(reporter);
        return extentReports;
    }

    public synchronized void onTestStart(ITestResult result) {
        ExtentTest extentTest = extentReports.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));

    }

    public synchronized void onTestSuccess(ITestResult result) {
        //test.get().pass(String.valueOf(result.getParameters()));

        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass(result.getMethod().getMethodName());
        test.get().pass("testPassed");
        if(getPage()!=null) {
            test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        }
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        if(getPage()!=null) {
            test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {

    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");

        // System.out.println(ReportLocation + "  ReportLocation");
      //  reports = new ExtentReports(ReportLocation + "ExtentReport.html");
        //test = reports.startTest("RestAssured");

    }

    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extentReports.flush();
        test.remove();
    }
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}