import helpers.ConfigReader
import helpers.DriverManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.WebDriver

class TestCase {
    protected static WebDriver driver

    @BeforeAll
    static void setup() {
        def conf = ConfigReader.loadConfig()
        driver = DriverManager.selectBrowser()
        driver.get(conf.uri.web)
    }
    @AfterAll
    static void cleanup() {
        DriverManager.stopDriver()
    }
}
