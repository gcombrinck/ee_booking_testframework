package com.equalexperts;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.testng.Assert.*;

import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.BookingPage;
import utils.HelperMethods;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTests extends HelperMethods {
    private WebDriver driver;
    protected BookingPage bookingPage;

    @BeforeClass
    public void testUp() {
        System.out.println("*** Initializing Webdriver ***");
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("http://hotel-test.equalexperts.io/");
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void goHome() {
        bookingPage = new BookingPage(driver);
        assertEquals(bookingPage.getPageTitle(), "Hotel booking form", "Incorrect page title.");
    }

    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("screenshots/" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}