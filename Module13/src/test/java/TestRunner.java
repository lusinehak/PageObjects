import browser.BrowserManager;
import logger.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestRunner {

    @Test(description = "Login to GMail Account")
    public void login() {
        Logger.log("Test \'Login to GMail Account\' is starting");
        boolean isLoggedIn = new LoginPage().open().loginToEmail().isAccountInfoPresent();
        Assert.assertTrue(isLoggedIn, "Login issue");
        Logger.log("Test \'Login to GMail Account\' is completed successfully");
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            new LoginPage().takeScreenshot();
        }
    }

    @AfterClass
    public void close() {
        BrowserManager.close();
    }

}
