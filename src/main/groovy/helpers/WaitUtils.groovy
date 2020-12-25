package helpers;

import com.google.common.base.Function
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration

class WaitUtils {
    private WebDriver driver
    WaitUtils() {
        this.driver = Browser.selectBrowser()
    }

    WebElement waitVisibilityOf(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout))
        wait.until(ExpectedConditions.elementToBeClickable(by))
    }

    void waitVisibilityOf(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout))
        wait.until(ExpectedConditions.visibilityOf(element))
    }

    void waitForCondition(ExpectedCondition condition, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout))
        wait.until(condition)
    }

    public <T> T waitForCustomCondition(Function<WebDriver,T> func, int timeout) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(timeout))
                .ignoring(NoSuchElementException.class)

        wait.until(func)
    }
}
