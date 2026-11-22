package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MockWebTest extends TestBase {



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


    @Test
    public void DemoTest() {

        System.out.println("execution completed");
        //Passed with Assertion
        assertThat(Objects.requireNonNull(page)).hasTitle("EventHub — Discover & Book Events");

        /* failed assertions
          assertThat(page).hasTitle("EventHub — Discover & Book Events");
         */

        page.getByLabel("Email").fill("aiintelligence335@gmail.com");
        page.getByLabel("Password").fill("Demo@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
//        //Locator errorMessage = page.getByText("Invalid email or password");
//
//        assertThat(errorMessage).isVisible();
//        assertThat(errorMessage).hasValue("Invalid email or password");
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();


        // create even from admin page
        page.route("**api/events**", route -> route.fulfill(
                new Route.FulfillOptions()
                        .setPath(Objects.requireNonNull(Paths.get("src/test/resources/events_6.json")))
        ));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        Locator eventCards = page.getByTestId("event-card");
        
        // it will wait until the first card is visible
        assertThat(eventCards.first()).isVisible();
        System.out.println(eventCards.count());
        Assert.assertEquals(eventCards.count(), 6 );
        assertThat(page.locator(".mx-1").first()).isVisible();


        // validate less than 6 events

        page.route("**api/events**", route -> route.fulfill(
                new Route.FulfillOptions()
                        .setPath(Objects.requireNonNull(Paths.get("src/test/resources/eventsLessThan_6.json")))
        ));

        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        Locator eventCards4 = page.getByTestId("event-card");

        // it will wait until the first card is visible
        assertThat(eventCards4.first()).isVisible();
        System.out.println(eventCards4.count());
        Assert.assertEquals(eventCards4.count(), 5 );
        assertThat(page.locator(".mx-1").first()).isHidden();
    }
    }
