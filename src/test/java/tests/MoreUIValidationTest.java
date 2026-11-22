package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class MoreUIValidationTest extends TestBase {

    @Test
    public void ChildWindowHandle(){
        Locator blinkingText = page.locator(".blinkingText");
        Page newPage = context.waitForPage(() -> blinkingText.first().click());
        newPage.waitForLoadState();

        String childText = Objects.requireNonNull(
                newPage.locator(".red").textContent(),
                "Expected the child window to contain contact text");
        String[] contactText = childText.split("at ", 2);
        if (contactText.length < 2) {
            throw new IllegalStateException("Expected contact text to contain an email address");
        }
        String[] emailText = contactText[1].split("\\s+", 2);
        if (emailText.length == 0 || emailText[0].isBlank()) {
            throw new IllegalStateException("Expected contact text to contain an email address");
        }
        String emailId = emailText[0];
        System.out.println(emailId);
        page.getByLabel("Username:").fill(Objects.requireNonNull(emailId));
        page.waitForTimeout(3000);

        System.out.println(page.getByLabel("Username:").inputValue());
        //page.locator("#password")



    }

    @Test
    public void uiControl(){
        Locator userRadio = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User"));
        userRadio.click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Okay")).click();
        Assert.assertTrue(userRadio.isChecked());
        Locator checkBoxTerms =  page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));
        checkBoxTerms.check();
        Assert.assertTrue(checkBoxTerms.isChecked());


        // we do no have the label so directly we cannot use selectOptions but AriaRole.COMBOBOX can be used once per
        // page/script coz multiple combobox will confuse the Playwright

        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");
        page.waitForTimeout(5000);

    }

}
