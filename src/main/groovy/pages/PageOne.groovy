package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

import com.google.common.base.Function

class PageOne extends Page{
    @FindBy(css='a.ico-register')
    private WebElement linkReg

    @FindBy(css = 'ul.top-menu')
    private WebElement menuTop

    PageOne(WebDriver driver) {
        super(driver)
        injectScriptFile("jquery.js")
    }
    void getElement() {
        String t = runScript("return arguments[0].text", linkReg)
        printf("Text of the link: %s%n", t)
    }
    void getTopMenu() {
        List<WebElement> items = runScript("return arguments[0].querySelectorAll('li')", menuTop)
        items.each { println it.getText() }
    }
    void findSearchByjQuery() {
        String srch = runScript("return \$('#small-searchterms').attr('placeholder')")
        printf("Placeholder: %s%n", srch)
    }
    /**
     * https://www.techbeamers.com/webdriver-fluent-wait-command-examples/
     */
    void waitForColors() {
        wait.waitForCustomCondition(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                WebElement element = d.findElement(By.id("dynamicColor"))
                String color = element.getCssValue("color")
                println("The button text has color : $color")
                if (color.equals("rgba(255, 255, 0, 1)")) {
                    return true
                }
                return false
            }
        }, 30)
    }
}
