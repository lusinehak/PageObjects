package pages;

import data.Constants;
import org.openqa.selenium.By;

public class LoginPage extends AbstractPage {

    private static final By USERNAME = By.id("identifierId");
    private static final By PASSWORD = By.name("password");
    private static final By NEXT_BUTTON = By.xpath("//div[@id='identifierNext']");
    private static final By NEXT = By.xpath("//div[@id='passwordNext']");
    private static final By ACCOUNT_INFO = By.className("paz5i");

    public LoginPage open() {
        browserManager.open(Constants.URL);
        return this;
    }

    public LoginPage loginToEmail() {
        browserManager.type(USERNAME, Constants.USERNAME);
        browserManager.click(NEXT_BUTTON);
        browserManager.type(PASSWORD, Constants.PASSWORD);
        browserManager.click(NEXT);
        return this;
    }

    public void takeScreenshot() {
        browserManager.takeScreenshot();
    }

    public boolean isAccountInfoPresent() {
        return browserManager.isDisplayed(ACCOUNT_INFO);
    }
}
