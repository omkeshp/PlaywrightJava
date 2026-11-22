package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage  {
    private static final String EMAIL_PLACEHOLDER = "Email";
    private static final String PASSWORD_PLACEHOLDER = "Password";


    public LoginPage(Page page, String baseURL) {
        this.page = page;
        this.baseURL = baseURL;
    }
    public DashboardPage LoginApplication(){
        page.navigate(Objects.requireNonNull(baseURL));

        System.out.println("execution completed");

        //Passed with Assertion
        assertThat(Objects.requireNonNull(page)).hasTitle("EventHub — Discover & Book Events");

        /* failed assertions
          assertThat(page).hasTitle("EventHub — Discover & Book Events");
         */

        page.getByLabel(EMAIL_PLACEHOLDER).fill("aiintelligence335@gmail.com");
        page.getByLabel(PASSWORD_PLACEHOLDER).fill("Demo@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        DashboardPage dashboardPage = new DashboardPage(page);
        return dashboardPage;
    }
    Page page;
    String baseURL;

    // final stands for constant value, it cannot be changed
    // private stands for encapsulation, it cannot be accessed outside the class
    //static stands for class level variable, it can be accessed without creating an object of the class


}
