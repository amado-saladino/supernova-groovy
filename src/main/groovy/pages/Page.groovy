package pages

import helpers.FileLoader
import helpers.WaitUtils
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.Select

abstract class Page {
    protected WebDriver driver
    protected JavascriptExecutor jsRunner
    WaitUtils wait

    Page(WebDriver driver) {
        this.driver = driver
        PageFactory.initElements(driver, this)
        jsRunner = (JavascriptExecutor) driver
        wait = new WaitUtils()
    }
    protected <T> T runScript(String script, Object... args) {
        (T) jsRunner.executeScript(script, args)
    }
    void selectDropdownItem(WebElement comboElement, String option) {
        Select(comboElement).selectByVisibleText(option)
    }
    void selectDropdownItem(WebElement comboElement, int index) {
        new Select(comboElement).selectByIndex(index)
    }
    WebElement getSelectedDropdownItem(WebElement comboBox) {
        new Select(comboBox).getFirstSelectedOption()
    }
    void scrollToBottom() {
        runScript("scrollTo(0, document.body.scrollHeight)")
    }
    void injectScriptFile(String file) {
        String script = new FileLoader("scripts\\" + file).toString()
        runScript(script)
    }
}