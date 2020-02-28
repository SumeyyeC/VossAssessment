import com.ConfigurationReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MultiOSMultiBrowserTest {
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

            //1)Browsing to the url
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.get(ConfigurationReader.getProperties("url"));
            driver.manage().window().maximize();

            //reporting part
            report = new ExtentReports();
            String filePath = System.getProperty("user.dir") + "/test-output/report.html";
            htmlReporter = new ExtentHtmlReporter(filePath);
            report.attachReporter(htmlReporter);
            report.setSystemInfo("Environment", "Staging");
            report.setSystemInfo("Browser", browserName);
            report.setSystemInfo("OS", System.getProperty("os.name"));
            htmlReporter.config().setDocumentTitle("VossAssessment");
            htmlReporter.config().setReportName("Multi Browser Automated Test Reports");
            htmlReporter.config().setTheme(Theme.STANDARD);
        }


        @AfterMethod
        public void tearDownMethod(ITestResult result) throws IOException {
            report.flush();
            if (result.getStatus() == ITestResult.FAILURE) {
               // String screenshotLocation = BrowserUtils.getScreenshot(result.getName());
                extentLogger.fail(result.getName());
               // extentLogger.addScreenCaptureFromPath(screenshotLocation);
                extentLogger.fail(result.getThrowable());

            } else if (result.getStatus() == ITestResult.SKIP) {
                extentLogger.skip("Test Case Skipped: " + result.getName());
            }

           driver.close();
        }
    }






