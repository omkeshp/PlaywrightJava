package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasicTests extends TestBase {

    // Invoke Browser -> Invoke tab -> enter url

    // Playwright is headless by default


        //headLess Mode
        //Chromium Engine
//        Browser browser = playwright.chromium().launch();

        // Firefox Engine
        // Browser browser = playwright.firefox().launch();

        /* Safari Engine
        Browser browser = playwright.webkit().launch();

         */
        //Chromium Engine





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

//        page.navigate("https://eventhub.rahulshettyacademy.com/events");
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add New Event")).click();
//         page.locator("#event-title-input").fill("QA SUMMIT");
//        page.getByPlaceholder("Describe the event…").fill("SUBMIT FORM");
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.locator("#event-title-input").fill("QA SUMMIT");
        page.getByPlaceholder("Describe the event…").fill("SUBMIT FORM");
        page.locator("#admin-event-form div textarea").fill("I want to submit the form");
        page.getByLabel("Category").selectOption("Concert");
        page.getByLabel("City").fill("Mumbai");
        page.getByLabel("Venue").fill("AB Stadium");
        page.getByLabel("Event Date & Time").fill("2026-10-29T15:23");
//        page.waitForTimeout(5000);
        page.getByLabel("Price ($)").fill("50");
        page.getByLabel("Total Seats").fill("2");
        page.locator("#add-event-btn").click();
        assertThat(page.getByText("Event created!")).isVisible();

        page.locator("#nav-events").click();

        // Iterating via multiple cards/ events and check for the one event which we created

        Locator eventCards = page.getByTestId("event-card");
        page.waitForTimeout(2000);
        System.out.println(eventCards.count());
        Locator targetedCard = eventCards.filter(new Locator.FilterOptions().setHasText("QA SUMMIT"));
        assertThat(targetedCard).isVisible();


        // instead of checking at page level like below
        // page.getByText("seats left!").innerText();

        // use below to get at particular card level as we already found our event card
        String seatsText = targetedCard.getByText("seats left!").innerText();

        System.out.println(seatsText);

        targetedCard.getByTestId("book-now-btn").click();
        //targetedCard.getByText("Book Now").click();

        page.locator("#customerName").fill("Demo Test Name");
        page.getByPlaceholder("you@email.com").fill("demo123@gmail.com");
        page.getByLabel("phone").fill("8472101010");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        //System.out.println(Confirmation);
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();
        String bookingRef = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View My Bookings")).click();
        System.out.println(bookingRef);
        Locator bookingCards = page.locator("#booking-card");

        //filter
        Locator targetedBooking = bookingCards.filter(new Locator.FilterOptions().setHasText("D-BRIESB"));
        assertThat(targetedBooking).isVisible();

        //seat count reduction check

        page.locator("#nav-events").click();

        // Iterating via multiple cards/ events and check for the one event which we created

        Locator eventCardsAfterBooking = page.getByTestId("event-card");
        page.waitForTimeout(8000);
        System.out.println(eventCardsAfterBooking.count());
        Locator targetedCardAfterbooking = eventCardsAfterBooking.filter(new Locator.FilterOptions().setHasText("QA SUMMIT"));
        assertThat(targetedCardAfterbooking).isVisible();
        String seatsTextAfterBooking = targetedCardAfterbooking.getByText("seats left!").innerText();

        System.out.println(seatsTextAfterBooking);

        page.waitForTimeout(8000);


    }
}
