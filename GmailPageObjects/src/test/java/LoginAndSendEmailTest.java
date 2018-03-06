import PageFactory.Constants;
import PageFactory.LoginPage;
import PageFactory.MailActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static PageFactory.Constants.URL;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LoginAndSendEmailTest {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void init() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
    }

    @Test
    public void login() {
        boolean isLoggedIn = new LoginPage(driver).logInAccount(Constants.USERNAME, Constants.PASSWORD).isLoggedIn();
        Assert.assertTrue(isLoggedIn, "Login issue");
    }

    @Test(dependsOnMethods = "login")
    public void composeAndSave() {
        boolean isSavedInDrafts = new MailActions(driver).composeAndSaveAsDraft(Constants.RECEIVER, Constants.SUBJECT,
                Constants.CONTENT).goToFolder(Constants.DRAFTS).isItemExists(Constants.SUBJECT);
        Assert.assertTrue(isSavedInDrafts, "Item is not saved in drafts");
    }

    @Test(dependsOnMethods = "composeAndSave")
    public void checkFields() {
        new MailActions(driver).selectDraftItem( Constants.SUBJECT);
        String receiver = new MailActions(driver).getReceiver(Constants.RECEIVER);
        String subject = new MailActions(driver).getSubject();
        String content = new MailActions(driver).getContent();
        Assert.assertEquals(receiver, Constants.RECEIVER, "Invalid receiver");
        Assert.assertEquals(subject, Constants.SUBJECT, "Invalid subject");
        Assert.assertEquals(content, Constants.CONTENT, "Invalid content");
    }

    @Test(dependsOnMethods = "checkFields")
    public void send() {
        boolean isSent = new MailActions(driver).sendMail().goToFolder(Constants.SENT_MAIL).isItemExists(Constants.SUBJECT);
        Assert.assertTrue(isSent, "Item is not sent");
    }

    @Test(dependsOnMethods = "send")
    public void checkDraft() {
        boolean isAbsent = new MailActions(driver).isDraftEmpty();
        Assert.assertTrue(isAbsent, "Item exists in draft folder.");
    }

    @AfterClass
    public void quit() {
        new MailActions(driver).logOut();
        driver.quit();
    }
}
