package pages;

import browser.BrowserManager;

public class AbstractPage {

    protected BrowserManager browserManager;

    protected AbstractPage() {
        this.browserManager = BrowserManager.getInstance();
    }
}
