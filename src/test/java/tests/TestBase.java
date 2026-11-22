package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
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
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
        properties.load(fileInputStream);
        // dynamically running different browsers based on the value provided in the config.properties file or through command line argument
        String browserName = System.getProperty("browser") != null? System.getProperty("browser") : properties.getProperty("browser");

        String envName = System.getProperty("env") != null? System.getProperty("env") : properties.getProperty("env");

        if(browserName.equalsIgnoreCase("chrome")){
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        } else if(browserName.equalsIgnoreCase("firefox")){
            playwright = Playwright.create();
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else if(browserName.equalsIgnoreCase("safari")){
            playwright = Playwright.create();
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        //playwright = Playwright.create();

        //with Headed Mode
//        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        //headLess Mode
        //Chromium Engine
//        Browser browser = playwright.chromium().launch();

        // Firefox Engine
        // Browser browser = playwright.firefox().launch();

        /* Safari Engine
        Browser browser = playwright.webkit().launch();

         */
        //Chromium Engine


        // running the tests in local browser
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        // Browser browser = playwright.chromium().launch();
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
        baseURL = properties.getProperty(envName + ".baseurl");

    }

    @AfterMethod
    public void tearDown(){
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Objects.requireNonNull(Paths.get("trace.zip"))));
    }

}
