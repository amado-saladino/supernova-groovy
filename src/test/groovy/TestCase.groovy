import helpers.Browser
import helpers.ConfigReader
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.WebDriver

class TestCase {
    static WebDriver driver

    @BeforeAll
    static void setup() {
        def conf = ConfigReader.loadConfig()
        driver = Browser.selectBrowser()
        driver.get(conf.uri.web)
    }
    @AfterAll
    static void cleanup() {
        Browser.stopDriver()
    }
}
