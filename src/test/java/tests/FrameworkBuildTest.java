package tests;

import org.testng.annotations.Test;

import pages.AdminEventPage;
import pages.BookingPage;
import pages.DashboardPage;
import pages.EventsPage;
import pages.LoginPage;

public class FrameworkBuildTest extends TestBase {
    @Test(description = "Create an Event and Book, Verify it is created")
    public void DemoTest() {

        LoginPage loginPage = new LoginPage(page, baseURL);
        DashboardPage dashboardPage = loginPage.LoginApplication();

        dashboardPage.waitForEventsToLoad(page);
        AdminEventPage adminEventPage =  new AdminEventPage(page);
        adminEventPage.goToAdminEventPage();
        adminEventPage.createEvent("QA SUMMIT", "QA Summit is a premier event for QA Profession", "Concert", "Mumbai",
                "BKC", "2027-11-12T14:58", "100", "10000"
                );



        // Creating and submitting the event card
        EventsPage eventsPage = new EventsPage(page);
        eventsPage.goToEventsPage();

        eventsPage.waitForEventsToLoad();
        eventsPage.findEventCard();
        eventsPage.submitEvents();

        BookingPage bookingPage = new BookingPage(page);
        bookingPage.SubmitBookingForm("Demo Test Name", "demo123@gmail.com", "8472101010");

        bookingPage.ValidateBookingConfirmation();

        bookingPage.ValidateBookingInMyBookings();

        System.out.println("tickets after booking");

        // Verify the event card is created
        // Customer checkout / booking details form


        // Assert successful ticket reservation


        // Filter and assert your booking card is displayed

        // Seat count reduction check
//        page.locator("#nav-events").click();
//
//        // Target the specific event card again post-booking
//        Locator eventCardsAfterBooking = page.getByTestId("event-card");
//        page.waitForTimeout(8000);
//        System.out.println(eventCardsAfterBooking.count());
//
//        Locator targetedCardAfterbooking = eventCardsAfterBooking.filter(new Locator.FilterOptions().setHasText("QA SUMMIT"));
//        assertThat(targetedCardAfterbooking.first()).isVisible();
//
//        // FIXED LOGIC: Changed locator text query from "seats left!" to regex match "seats available"
//        String seatsTextAfterBooking = targetedCardAfterbooking.getByText(java.util.regex.Pattern.compile("seats available")).innerText();
//
//        System.out.println(seatsTextAfterBooking);
//
//        page.waitForTimeout(8000);
    }
}
