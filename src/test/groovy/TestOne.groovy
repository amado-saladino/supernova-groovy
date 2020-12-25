import helpers.Faker
import helpers.Screenshot
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import pages.PageOne

class TestOne extends TestCase{
    static PageOne pg1

    @BeforeAll
    static void before(){
        pg1 = new PageOne(driver)
    }
    @Test
    void "search Google for keyword"() {
        println('Search Google!')
        println driver == null
    }
    @Test
    void 'run jquery'(){
        pg1.findSearchByjQuery()
    }
    @Test
    void "show page elements"() {
        printf("driver is null? %s%n", driver == null)
        pg1.getElement()
        pg1.getTopMenu()
        Screenshot.takeScreenshot('page-elements')
    }
    @Test
    void 'generate some fake data'() {
        printf("driver is null? %s%n", driver == null)
        Faker faker = new Faker()
        String day = faker.getRandomDate().day()
        String month = faker.getRandomDate().month()
        String year = faker.getRandomDate().year()

        printf("Random day: %s%n",day)
        printf("Random month: %s%n",month)
        printf("Random year: %s%n",year)

        printf("Random text: %s%n",faker.getRandomMessage())
    }
    @Test
    void "wait for colors"() {
        String path = System.getProperty("user.dir") + "/nginx-deploy/colors.html"
        driver.get(path)
        pg1.waitForColors()
        Screenshot.takeScreenshot("colors")
    }
}
