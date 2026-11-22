package bo.edu.ucb.internship.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** Shared browser lifecycle for the team's Selenium acceptance tests. */
public abstract class BaseTest {

    private static final Dotenv LOCAL_ENVIRONMENT = loadLocalEnvironment();

    protected WebDriver driver;
    protected Path downloadDirectory;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() throws IOException {
        WebDriverManager.chromedriver().setup();

        Path downloadRoot = Path.of(
                System.getProperty("acceptance.downloadRoot", "target/downloads")
        ).toAbsolutePath();
        Files.createDirectories(downloadRoot);
        downloadDirectory = Files.createTempDirectory(downloadRoot, "run-");

        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("browser.headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1440,1000");

        Map<String, Object> preferences = new HashMap<>();
        preferences.put("download.default_directory", downloadDirectory.toString());
        preferences.put("download.prompt_for_download", false);
        preferences.put("download.directory_upgrade", true);
        preferences.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", preferences);

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
        String configuredUrl = System.getProperty("app.baseUrl");
        if (configuredUrl == null || configuredUrl.isBlank()) {
            configuredUrl = environmentValue("APP_BASE_URL");
        }
        return configuredUrl == null || configuredUrl.isBlank()
                ? "http://localhost:5173"
                : configuredUrl.trim();
    }

    protected String environmentValue(String name) {
        return LOCAL_ENVIRONMENT.get(name);
    }

    private static Dotenv loadLocalEnvironment() {
        Path directory = Path.of(System.getProperty("user.dir", "."))
                .toAbsolutePath()
                .normalize();

        while (directory != null) {
            if (directory.getFileName() != null
                    && "acceptance-tests".equals(directory.getFileName().toString())
                    && Files.isRegularFile(directory.resolve(".env"))) {
                return dotenvFrom(directory);
            }

            Path acceptanceTestsDirectory = directory.resolve("acceptance-tests");
            if (Files.isRegularFile(acceptanceTestsDirectory.resolve(".env"))) {
                return dotenvFrom(acceptanceTestsDirectory);
            }
            directory = directory.getParent();
        }

        return Dotenv.configure().ignoreIfMissing().load();
    }

    private static Dotenv dotenvFrom(Path directory) {
        return Dotenv.configure()
                .directory(directory.toString())
                .ignoreIfMissing()
                .load();
    }
}
