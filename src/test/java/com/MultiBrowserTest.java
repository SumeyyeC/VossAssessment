package com;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MultiBrowserTest {
    protected WebDriver driver;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentReports report;
    protected static ExtentTest extentLogger;

    @Parameters("browserName")
    @BeforeMethod
    public void setupMethod(String browserName) {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }
        htmlReporter = new ExtentHtmlReporter("C:/Users/sumey/IdeaProjects/VossAssessment/test-output/reports.html");
        htmlReporter.loadXMLConfig("C:/Users/sumey/IdeaProjects/VossAssessment/html-cofiguration.xml");
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        extentLogger=report.createTest("Voss Automation");

        //1) Browsing to the url
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(ConfigurationReader.getProperties("url"));
    }

    @Parameters("browserName")
    @Test
    public void test(String browserName) throws IOException {
        //verifying the page title
        Assert.assertTrue(driver.getTitle().contains("Automation Practice - Ultimate QA"));
        extentLogger.createNode(browserName);
        extentLogger.createNode(System.getProperty("os.name"));
        extentLogger.log(Status.INFO,"Title Verified, First Page "+browserName.toUpperCase());
        //Taking screenshot
        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots" +time+ ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        //2) Window maximizing
        driver.manage().window().maximize();
        //login part cancelled due to Captcha
        // 5) Browse to "Fill out forms" page and complete all forms, followed by submit action
      driver.findElement(By.xpath("//div[@class='et_pb_text_inner']/ul/li[4]/a")).click();
      driver.findElement(By.xpath("//input[@id='et_pb_contact_name_0']")).sendKeys("John");
      driver.findElement(By.xpath("//textarea[@id='et_pb_contact_message_0']")).sendKeys("My sons hero");

      //left submit
      driver.findElement(By.xpath("//*[@id='et_pb_contact_form_0']/div[2]/form/div/button")).click();
      driver.findElement(By.xpath("//input[@id='et_pb_contact_name_1']")).sendKeys("Wick");
      driver.findElement(By.xpath("//textarea[@id='et_pb_contact_message_1']")).sendKeys("My sons hero");

      //right submit with addition
      driver.findElement(By.xpath("//input[@class='input et_pb_contact_captcha']")).sendKeys("8");
      driver.findElement(By.xpath("//*[@id='et_pb_contact_form_1']/div[2]/form/div/button")).click();
      extentLogger.log(Status.INFO,"Forms submitted");

      //6) Browse to the "Fake Pricing Page" and Purchase the Basic package
      driver.navigate().to(ConfigurationReader.getProperties("url"));
      Assert.assertTrue(driver.getTitle().contains("Automation Practice - Ultimate QA"));
      extentLogger.log(Status.INFO,"Title Verified, Fake Pricing Page "+browserName.toUpperCase());
      driver.findElement(By.xpath("//div[@class='et_pb_text_inner']/ul/li[3]/a")).click();
      driver.findElement(By.xpath("//*[@id=\"et-boc\"]/div/div/div[1]/div[2]/div[2]/div/div/div/div[4]/a")).click();
      //after selecting basic package nothing happens
    }

    @AfterMethod
    public void tearDown(){
        report.flush();
        driver.close();
    }
    }
