package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LoginPage extends GmailAbstractPage {

    private static final String USERNAME_FIELD = "identifierId";
    private static final String PASSDOWR_FIELD = "password";
    private static final String NEXT_BTN = "//div[@id='identifierNext']|//div[@id='passwordNext']";
    private static final String VERIFY_ACCOUNT = "paz5i";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = USERNAME_FIELD)
    private WebElement username;

    @FindBy(name = PASSDOWR_FIELD)
    private List<WebElement> password;

    @FindBy(xpath = NEXT_BTN)
    private WebElement nextButton;

    @FindBy(className = VERIFY_ACCOUNT)
    private WebElement verifyAccount;

    public LoginPage logInAccount(String uName, String passwd) {
        username.sendKeys(uName);
        nextButton.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(password));
        password.get(0).sendKeys(passwd);
        nextButton.click();
        return new LoginPage(driver);
    }

    public boolean isLoggedIn() {
        return verifyAccount.isDisplayed();
    }
}
