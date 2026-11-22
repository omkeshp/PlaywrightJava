package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UIValidationContinuedTest extends TestBase {



    @Test
    public void popUpValidation(){
        assertThat(page.getByPlaceholder("Hide/Show Example")).isVisible();
        page.locator("#hide-textbox").click();
        assertThat(page.getByPlaceholder("Hide/Show Example")).isHidden();

        // before alert/popup comess you need to put listener so whenever it comes it will capture it

        // putting the listener

        page.onDialog(dialog -> dialog.accept());

        // now hitting the alert, encountering the dialog
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Alert")).click();

        // mouse hover, use directly hover()
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Mouse Hover")).hover();

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Top")).click();

        //page.waitForTimeout(6000);
        FrameLocator framePage = page.frameLocator("#courses-iframe");
        framePage.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Learning paths")).click();
        System.out.println(framePage.locator(".inner-box h1").textContent());
    }
    @Test
    public void screenShotTest(){

        // Page level screenshot set the path using Paths.get
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Objects.requireNonNull(Paths.get("screen.png")))
                .setFullPage(true));

        // you can take the screenshot at Locator level/component level

        //take the compnent in locator
        Locator placeHolderBox = page.getByPlaceholder("Hide/Show Example");

        //take the screenshot at locator level
        placeHolderBox.screenshot(new Locator.ScreenshotOptions()
                .setPath(Objects.requireNonNull(Paths.get("locator.png"))));

        Locator tableSS = page.locator(".table-display");
        tableSS.screenshot(new Locator.ScreenshotOptions()
                .setPath(Objects.requireNonNull(Paths.get("table.png"))));
    }


}
