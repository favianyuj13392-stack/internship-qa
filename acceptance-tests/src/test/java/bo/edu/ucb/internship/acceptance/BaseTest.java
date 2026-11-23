package bo.edu.ucb.internship.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/** Shared browser lifecycle for the team's Selenium acceptance tests. */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("browser.headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1440,1000");

        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected String appBaseUrl() {
        return System.getProperty("app.baseUrl", "http://localhost:5173");
    }
}
