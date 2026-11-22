package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import tests.FrameworkDataProvider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {
    Page page;
    public static final String EVENT_BUTTON = "#nav-events";
    String entireCardText;
    Locator eventCards;


    public void goToEventsPage(){
        page.locator(EVENT_BUTTON).click();
    }

    public EventsPage(Page page) {
        this.page = page;
    }

    public Locator waitForEventsToLoad() {
       eventCards = page.getByTestId("event-card");
        assertThat(eventCards.first()).isVisible();
        return eventCards;
    }

    public Locator findEventCard(){
        eventCards = waitForEventsToLoad();
        System.out.println("Total cards found: " + eventCards.count());

        // Filter the specific card
        FrameworkDataProvider frameworkDataProvider = new FrameworkDataProvider();

        Locator targetedCard = eventCards.filter(new Locator.FilterOptions().setHasText("QA SUMMIT WITH DATA PROVIDER"));
        assertThat(targetedCard).isVisible();
        return targetedCard;
    }

    public void submitEvents(){
        Locator targetedCard = findEventCard();

        // APPROACH: Fetch the raw text of the entire card
         entireCardText = targetedCard.innerText();
        System.out.println("--- Full Card Text ---");
        System.out.println(entireCardText);
        System.out.println("----------------------");

        // Click the booking button
        targetedCard.getByTestId("book-now-btn").click();
    }

    public void validateCreatedEvent(){
        System.out.println(entireCardText);
        System.out.println(eventCards.count());       // Verify the event card is created);
        //System.out.println(targetedCard.getByTestId("book-now-btn").innerText());
        // Implemented as needed
    }

}
