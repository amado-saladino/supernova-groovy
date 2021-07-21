package helpers

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.BrowserType
import org.openqa.selenium.remote.CapabilityType

import org.openqa.selenium.WebDriver

import java.time.Duration

class Browser {
    private static WebDriver driver
    private final static Map<String,Closure> browsers = [
            (BrowserType.CHROME): { h ->
                WebDriverManager.chromedriver().setup()
                new ChromeDriver(chromeOptions(h)) },
            (BrowserType.FIREFOX): { h ->
                WebDriverManager.firefoxdriver().setup()
                new FirefoxDriver(firefoxOptions(h)) }
    ]

    static WebDriver selectBrowser(String browser='chrome',boolean headless=true) {
        def conf = ConfigReader.loadConfig()
        if (driver == null) {
            driver = browsers[browser](headless)
            driver.manage().window().maximize()
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(conf.wait.default_wait))
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
