package helpers

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType

import org.openqa.selenium.WebDriver

class Browser {
    private static WebDriver driver
    private final static Map<String,Closure> browsers = [
            'chrome': { h -> new ChromeDriver(chromeOptions(h)) },
            'firefox': { h -> new FirefoxDriver(firefoxOptions(h)) }
    ]

    static WebDriver selectBrowser(String browser='chrome',boolean headless=false) {
        if (driver == null) {
            driver = browsers[browser](headless)
            driver.manage().window().maximize()
        }
        driver
    }

    static void stopDriver() {
        driver.quit()
        driver = null
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        def conf = ConfigReader.loadConfig()
        System.setProperty('webdriver.chrome.driver', conf.driver.chrome)
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
        def conf = ConfigReader.loadConfig()
        System.setProperty("webdriver.gecko.driver", conf.driver.firefox)
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
