package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

import com.google.common.base.Function

class HomePage extends Page{
    @FindBy(css='a.ico-register')
    private WebElement linkReg

    @FindBy(css = 'ul.top-menu')
    private WebElement menuTop

    HomePage(WebDriver driver) {
        super(driver)
        injectScriptFile("scripts/jquery.js")
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
}
