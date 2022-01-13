package helpers

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.Browser
import org.openqa.selenium.remote.CapabilityType

import org.openqa.selenium.WebDriver

import java.time.Duration

class DriverManager {
    private static WebDriver driver
    private final static Map<String,Closure> browsers = [
            (Browser.CHROME.browserName()): { h ->
                WebDriverManager.chromedriver().setup()
                new ChromeDriver(chromeOptions(h)) },
            (Browser.FIREFOX.browserName()): { h ->
                WebDriverManager.firefoxdriver().setup()
                new FirefoxDriver(firefoxOptions(h)) }
    ]

    static WebDriver selectBrowser() {
        def conf = ConfigReader.loadConfig()
        def waitTime = conf.wait.default_wait
        def headless = conf.driver.headless
        def browser = conf.driver.browser

        if (driver == null) {
            driver = browsers[browser](headless)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40))
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100))
            driver.manage().window().maximize()
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime))
        }
        driver
    }

    static void stopDriver() {
        driver.quit()
        driver = null
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions()
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>()
        chromePrefs.put("profile.default.content_settings.popups", 0)
        chromePrefs.put("credentials_enable_service", false)
        chromePrefs.put("profile.password_manager_enabled", false)
        options.setExperimentalOption("prefs", chromePrefs)
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
        options.setExperimentalOption("excludeSwitches", ["enable-automation"])
        options.setExperimentalOption("useAutomationExtension",false)

        if (headless) {
            options.setHeadless(true)
            options.addArguments("window-size=2000,1080")
        }
        options
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions()
        //option.addPreference("browser.download.folderList", 2);
        //option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        //option.addPreference("browser.download.manager.showWhenStarting", false);
        //option.addPreference("pdfjs.disabled", true);
        if (headless) {
            options.setHeadless(true)
        }
        options
    }
}
