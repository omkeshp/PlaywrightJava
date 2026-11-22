package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminEventPage {
    Page page;
    private static final String EVENT_TITLE_INPUT = "#event-title-input";
    private static final String EVENT_DESCRIPTION_INPUT = "Describe the event…";
    private static final String CATEGORY_INPUT = "Category";
    private static final String CITY_INPUT = "City";
    private static final String VENUE_INPUT = "Venue";
    private static final String EVENT_DATE_INPUT = "Event Date & Time";
    private static final String PRICE_INPUT = "Price ($)";
    private static final String TOTAL_SEATS_INPUT = "Total Seats";
    private static final String ADD_EVENT_BUTTON = "#add-event-btn";
    private static final String EVENT_MESSAGE = "Event created!";
    public AdminEventPage(Page page) {
        this.page = page;
    }
    public void goToAdminEventPage(){
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");


    }
    public void createEvent(String title, String description, String category, String city, String venue, String dateTime, String price, String totalSeats){
        if (title == null) throw new RuntimeException("title is NULL");
        if (description == null) throw new RuntimeException("description is NULL");
        if (category == null) throw new RuntimeException("category is NULL");
        if (city == null) throw new RuntimeException("city is NULL");
        if (venue == null) throw new RuntimeException("venue is NULL");
        if (dateTime == null) throw new RuntimeException("dateTime is NULL");
        if (price == null) throw new RuntimeException("price is NULL");
        if (totalSeats == null) throw new RuntimeException("totalSeats is NULL");
        page.locator(EVENT_TITLE_INPUT).fill(title);
        page.getByPlaceholder(EVENT_DESCRIPTION_INPUT).fill(description);
        page.getByLabel(CATEGORY_INPUT).selectOption(category);
        page.getByLabel(CITY_INPUT).fill(city);
        page.getByLabel(VENUE_INPUT).fill(venue);
        page.getByLabel(EVENT_DATE_INPUT).fill(dateTime);
//        page.waitForTimeout(5000);
        page.getByLabel(PRICE_INPUT).fill(price);
        page.getByLabel(TOTAL_SEATS_INPUT).fill(totalSeats);
        page.locator(ADD_EVENT_BUTTON).click();
        assertThat(page.getByText(EVENT_MESSAGE)).isVisible();

    }


}
