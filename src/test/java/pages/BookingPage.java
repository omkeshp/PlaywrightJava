package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookingPage {
    Page page;
    String bookingRef;
    public static final String CUSTOMER_NAME_LOCATOR = "#customerName";
    public static final String EMAIL_LOCATOR = "you@email.com";
    public static final String PHONE_NUMBER_LOCATOR = "phone";
    public static final String BOOKING_REF_LOCATOR = ".booking-ref";

    public static final String BOOKING_CARD_LOCATOR = "#booking-card";
    public static final String VIEW_MY_BOOKING_LOCATOR = "View My Bookings";
    public static final String CONFIRM_BOOKING_BUTTON_LOCATOR = "Confirm Booking";
    public BookingPage(Page page) {
        this.page = page;
    }

    public void SubmitBookingForm( String customerName, String email, String phoneNumber) {
        page.locator(CUSTOMER_NAME_LOCATOR).fill(Objects.requireNonNull(customerName));
        page.getByPlaceholder(EMAIL_LOCATOR).fill(Objects.requireNonNull(email));
        page.getByLabel(PHONE_NUMBER_LOCATOR).fill(Objects.requireNonNull(phoneNumber));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(CONFIRM_BOOKING_BUTTON_LOCATOR)).click();
    }

    public void ValidateBookingConfirmation() {
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();
        bookingRef = page.locator(BOOKING_REF_LOCATOR).innerText();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(VIEW_MY_BOOKING_LOCATOR)).click();
        System.out.println(bookingRef);

    }

    public void ValidateBookingInMyBookings() {
        Locator bookingCards = page.locator(BOOKING_CARD_LOCATOR);
        Locator targetedBooking = bookingCards.filter(
                new Locator.FilterOptions().setHasText(Objects.requireNonNull(bookingRef)));
        assertThat(targetedBooking).isVisible();

    }
}