package browser;

import logger.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BrowserManager {

    private WebDriver webDriver;
    private static BrowserManager instance = null;


    private BrowserManager(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static BrowserManager getInstance() {
            if (instance != null) {
                return instance;
            }
            return instance = init();
    }

    private static BrowserManager init() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return new BrowserManager(webDriver);
    }

    public void open(String url) {
        Logger.info("Open the page " + url);
        webDriver.get(url);
    }

    public void waitForElement(By locator) {
        if (webDriver.findElements(locator).size() > 0) {
            Logger.info("Element " + webDriver.findElement(locator).getText() + " exist");
            new WebDriverWait(webDriver, 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } else {
            Logger.error("Element by locator " + locator + " is not found");
            System.exit(1);
        }
    }

    private void highlightElement(By locator) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].style.border='5px solid green'", webDriver.findElement(locator));
    }

    private void unHighlightElement(By locator) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].style.border='0px'", webDriver.findElement(locator));
    }

    public void click(By locator) {
        waitForElement(locator);
        Logger.info("Highlight element " + locator);
        highlightElement(locator);
        Logger.info("Click on element " + webDriver.findElement(locator).getText() + " Element is located: " + locator);
        webDriver.findElement(locator).click();
        Logger.info("Unhighlight element " + locator);
        unHighlightElement(locator);
    }

    public void type(By locator, String text) {
        waitForElement(locator);
        Logger.info("Highlight element " + webDriver.findElement(locator).getText());
        highlightElement(locator);
        Logger.info("Type on element " + webDriver.findElement(locator).getText() + " Element is located: " + locator);
        webDriver.findElement(locator).sendKeys(text);
        Logger.info("Unhighlight element " + webDriver.findElement(locator).getText());
        unHighlightElement(locator);
    }

    public boolean isDisplayed(By locator) {
        boolean isPresent = webDriver.findElements(locator).size() > 0;
        if (isPresent) {
            Logger.info("Element " + webDriver.findElement(locator).getText() + " is present and highlighted.");
            highlightElement(locator);
            Logger.info("Element " + webDriver.findElement(locator).getText() + " is unhighlighted.");
            unHighlightElement(locator);
        } else {
              Logger.error("Element " + webDriver.findElement(locator) + " is not present.");
        }
        return isPresent;
    }

    public void takeScreenshot() {
        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = "screenshots/screenshot" + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            Logger.info("Screenshot of page is saved in screenshots directory: " + screenshotName);
        } catch (IOException e) {
            Logger.error("Failed to make screenshot");
        }
    }

    public static void close() {
        Logger.info("Close the browser");
        if (instance != null) {
           try {
                instance.webDriver.quit();
           } finally {
                instance = null;
            }
        }
    }
}



