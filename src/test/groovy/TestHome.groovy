import helpers.Faker
import helpers.Screenshot
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import pages.HomePage

class TestHome extends TestCase{
    private static HomePage homePage

    @BeforeAll
    static void before(){
        homePage = new HomePage(driver)
    }

    @Test
    void 'run jquery'(){
        homePage.findSearchByjQuery()
    }

    @Test
    void "show page elements"() {
        printf("driver is null? %s%n", driver == null)
        homePage.getElement()
        homePage.getTopMenu()
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
}
