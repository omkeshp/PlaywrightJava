package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;
import java.util.HashMap;

public class FrameworkDataProvider extends TestBase {

    @DataProvider(name = "eventDataProvider")
    public Object[][] eventDataProvider() throws IOException {
        return utils.DataProvider.JsonFileDataProvider(
                "src/test/resources/EventBooking.json"
        );

    }
    @Test(dataProvider= "eventDataProvider", description = "Create an Event and Book, Verify it is created", groups = {"eventBooking"})
    public void DemoTest(HashMap<String, String> eventData) {

        LoginPage loginPage = new LoginPage(page, baseURL);
        DashboardPage dashboardPage = loginPage.LoginApplication();

        dashboardPage.waitForEventsToLoad(page);
        AdminEventPage adminEventPage =  new AdminEventPage(page);
        adminEventPage.goToAdminEventPage();

        // String title, String description, String category, String city, String venue, String dateTime, String price, String totalSeats){
        //        page.locator(eventTitleInput).fill(title);
        adminEventPage.createEvent(eventData.get("eventTitle"), eventData.get("eventDescription"), "Concert", eventData.get("city"),
                eventData.get("venue"), eventData.get("DateTime"), eventData.get("price"), eventData.get("totalSeats")
                );



        // Creating and submitting the event card
        EventsPage eventsPage = new EventsPage(page);
        eventsPage.goToEventsPage();

        eventsPage.waitForEventsToLoad();
        eventsPage.findEventCard();
        eventsPage.submitEvents();

        BookingPage bookingPage = new BookingPage(page);
        bookingPage.SubmitBookingForm(eventData.get("FullName"), eventData.get("email"), eventData.get("phone"));

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
