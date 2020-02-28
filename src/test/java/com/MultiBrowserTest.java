package com;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MultiBrowserTest {
    protected WebDriver driver;
    protected static ExtentReports report;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest extentLogger;

    @Parameters("browserName")
    @BeforeMethod
    public void setupMethod(String browserName) {
        switch ("browserName") {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }
        //1) Browsing to the url and verifying the page title
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(ConfigurationReader.getProperties("url"));
        Assert.assertTrue(driver.getTitle().contains("Automation Practice - Ultimate QA"));

        //Taking screenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots" + ".png";
        File finalDestination = new File(target);

        //2) Window maximizing
        driver.manage().window().maximize();
    }


    @Test
    public void test(){
    //I signed up for a user first
    //3) Login
    driver.findElement(By.xpath("//div[@class='et_pb_text_inner']/ul/li[6]/a")).click();
    driver.findElement(By.id("user[email]")).sendKeys(ConfigurationReader.getProperties("userEmail"));
    driver.findElement(By.id("user[password]")).sendKeys(ConfigurationReader.getProperties("password"));
    driver.findElement(By.xpath("//input[@type='submit']")).click();
    Assert.assertTrue(driver.findElement(By.xpath("//a[@class='dropdown__toggle-button']")).getText().contains("John"));

    //4) Logout
    driver.findElement(By.xpath("//i[@class='fa fa-caret-down']")).click();
    driver.findElement(By.xpath("//ul[@class='dropdown__menu']/li[4]")).click();
    //5) Navigating to "Fill out forms" page
    driver.navigate().to(ConfigurationReader.getProperties("url"));
    driver.findElement(By.xpath("//div[@class='et_pb_text_inner']/ul/li[4]/a")).click();





    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
    }
