package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MailActions extends GmailAbstractPage {

    private static final String GO_TO_MAIL = ".WaidBe";
    private static final String COMPOSE_MAIL = "//*[contains(@class, 'T-I-KE')]";
    private static final String SET_RECEIVER = "to";
    private static final String SET_SUBJECT = "subjectbox";
    private static final String CONTENT = "//div[@aria-label='Message Body']";
    private static final String GET_RECEIVER = "//span[contains(text(),'%s')]";
    private static final String GET_SUBJECT = "//input[@name='subject']";
    private static final String SEND_EMAIL = "//div[text()='Send']";
    private static final String CLOSE_ITEM = "//img[@aria-label='Save & Close']";
    private static final String CLICK_ON_FOLDER = "//a[contains(@title, '%s')]";
    private static final String SELECT_ITEM = "//span[contains(text(), '%s')]";
    private static final String SELECT_DRAFT_EMAIL = "//span[contains(text(), '%s')]";
    private static final String GOOGLE_ACCOUNT = "//span[@class='gb_ab gbii']";
    private static final String LOG_OFF = "//a[text()='Sign out']";
    private static final String MESSAGE_WINDOW = "//div[@class='nH Hd']";
    private static final String EMPTY_DRAFT = "//td[@class='TC']";

    public MailActions(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = GO_TO_MAIL)
    private WebElement goToMail;

    @FindBy (xpath =  COMPOSE_MAIL)
    private WebElement composeEmail;

    @FindBy (name = SET_RECEIVER)
    private WebElement setReceiver;

    @FindBy (name = SET_SUBJECT)
    private WebElement setSubject;

    @FindBy (xpath =  CONTENT)
    private WebElement content;

    @FindBy (xpath =  GET_RECEIVER)
    private WebElement getReceiver;

    @FindBy (xpath = GET_SUBJECT)
    private WebElement getSubject;

    @FindBy (xpath =  SEND_EMAIL)
    private WebElement sendEmail;

    @FindBy (xpath =  CLOSE_ITEM)
    private WebElement closeItem;

    @FindBy (xpath =  CLICK_ON_FOLDER)
    private WebElement clickOnFolder;

    @FindBy (xpath =  SELECT_ITEM)
    private WebElement selectItem;

    @FindBy (xpath =  SELECT_DRAFT_EMAIL)
    private WebElement selectDraftEmail;

    @FindBy (xpath =  GOOGLE_ACCOUNT)
    private WebElement googleAccount;

    @FindBy (xpath =  LOG_OFF)
    private WebElement logOff;

    @FindBy (xpath =  MESSAGE_WINDOW)
    private List<WebElement> messageWindow;

    @FindBy (xpath =  EMPTY_DRAFT)
    private WebElement emptyDraft;

    public MailActions composeAndSaveAsDraft(String receiver, String sbj, String cnt) {
        goToMail.click();
        composeEmail.click();
        setReceiver.sendKeys(receiver);
        setSubject.sendKeys(sbj);
        content.sendKeys(cnt);
        closeItem.click();
        return new MailActions(driver);
    }

    public MailActions goToFolder(String name) {
        String folder = String.format(CLICK_ON_FOLDER, name);
        clickOnFolder = driver.findElement(By.xpath(folder));
        clickOnFolder.click();
        return new MailActions(driver);
    }

    public boolean isItemExists(String name) {
        String item = String.format(SELECT_ITEM, name);
        selectItem = driver.findElement(By.xpath(item));
        return selectItem.isDisplayed();
    }

    public String [] getRequiredData(String name, String sbj) {
        String data [] = new String[3];
        String email = String.format(SELECT_DRAFT_EMAIL, sbj);
        String receiver = String.format(GET_RECEIVER, name);
        selectDraftEmail = driver.findElement(By.xpath(email));
        selectDraftEmail.click();
        waitForElement(messageWindow);
        getReceiver = driver.findElement(By.xpath(receiver));
        data[0] = getReceiver.getText().toString();
        data[1] = getSubject.getAttribute("value").toString();
        data[2] = content.getText().toString();
        return data;
    }

    public MailActions sendMail() {
        sendEmail.click();
        return new MailActions(driver);
    }

    public boolean isDraftEmpty() {
        return emptyDraft.isDisplayed();
    }

    public void logOut() {
        googleAccount.click();
        logOff.click();
    }

}
