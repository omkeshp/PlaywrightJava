
package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public class TestBase {

    Playwright playwright;
    Page page;
    Browser browser;
    BrowserContext context;
    String baseURL;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {

        // Load configuration properties
        Properties properties = new Properties();

        try (FileInputStream fileInputStream =
                     new FileInputStream("src/test/resources/config.properties")) {

            properties.load(fileInputStream);
        }

        // Read browser from Maven command line or config.properties
        String browserName = System.getProperty("browser");

        if (browserName == null || browserName.isBlank()) {
            browserName = properties.getProperty("browser", "chromium");
        }

        // Read environment from Maven command line or config.properties
        String envName = System.getProperty("env");

        if (envName == null || envName.isBlank()) {
            envName = properties.getProperty("env", "qa");
        }

        // Normalize values
        browserName = browserName.trim().toLowerCase();
        envName = envName.trim().toLowerCase();

        // Validate browser name before initializing Playwright
        if (!browserName.equals("chrome")
                && !browserName.equals("chromium")
                && !browserName.equals("firefox")
                && !browserName.equals("safari")
                && !browserName.equals("webkit")) {

            throw new IllegalArgumentException(
                    "Invalid browser name: " + browserName
            );
        }

        // Read and validate base URL
        baseURL = properties.getProperty(envName + ".baseurl");

        if (baseURL == null || baseURL.isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid environment or missing base URL: " + envName
            );
        }

        // Initialize Playwright
        playwright = Playwright.create();

        // Launch the selected browser
        switch (browserName) {

            case "chrome":
            case "chromium":
                browser = playwright.chromium().launch();
                break;

            case "firefox":
                browser = playwright.firefox().launch();
                break;

            case "safari":
            case "webkit":
                browser = playwright.webkit().launch();
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid browser name: " + browserName
                );
        }

        // Create browser context
        context = browser.newContext();

        // Start tracing
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        // Create page
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        try {
            // Stop tracing and save trace file
            if (context != null) {
                context.tracing().stop(
                        new Tracing.StopOptions()
                                .setPath(Paths.get("trace.zip"))
                );
            }

        } finally {

            // Close browser context
            if (context != null) {
                context.close();
            }

            // Close browser
            if (browser != null) {
                browser.close();
            }

            // Close Playwright
            if (playwright != null) {
                playwright.close();
            }
        }
    }
}
