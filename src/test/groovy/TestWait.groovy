import helpers.Screenshot
import org.junit.jupiter.api.Test
import pages.ColorPage

class TestWait extends TestCase {
    @Test
    void testColorWait() {
        ColorPage colorPage = new ColorPage(driver)
        String path = "${System.getProperty("user.dir")}/static/colors.html"
        driver.navigate().to("file://${path}")
        colorPage.waitForColors()
        Screenshot.fullPageScreenshot("colors")
    }
}
