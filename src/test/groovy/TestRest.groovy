import helpers.ConfigReader
import helpers.Faker
import helpers.FileLoader
import helpers.RestUtils
import io.restassured.RestAssured
import io.restassured.http.Method
import io.restassured.response.Response
import models.User
import models.UserReqRes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.hamcrest.Matchers.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestRest {
    RestUtils requests
    Faker faker
    def jsonAPI

    @BeforeAll()
    void init() {
        def conf = ConfigReader.loadConfig()
        RestAssured.baseURI = conf.uri.rest
        jsonAPI = conf.uri.RESTJSON
        requests = new RestUtils()
        faker = new Faker()
    }

    @Test
    void "get all users"() {
        def res = requests.sendWithQueryParams("api/users","",Method.GET,['per_page':20])
        assertEquals(12, res.data.size)

        List<UserReqRes> users = res.data
        assertEquals(12, users.size())
        println users*.first_name
    }

    @Test
    @DisplayName("json-server users")
    void "add user from json file"() {
        String user_txt = new FileLoader("data/user-payload.json")
        def res = requests.send("http://${jsonAPI}/users", user_txt,Method.POST)
        User user = res
        println user
    }

    @Test
    void "resolve placeholders with values"(){
        String user_txt = new FileLoader("data/user-placeholders.json")
        def name = faker.getFemaleFirstName()
        def body = requests.resolveVarsInString(user_txt,['name': name, 'job': 'DevOps Engineer'])
        def user = requests.send('api/users',body,Method.POST)
        println user.toString()
        assertEquals(name, user.name)
    }

    @Test
    void "get a single random user"(){
        //id should be between 1 and 12
        def id = new Random().nextInt(13) + 1
        def res = requests.send("api/users/{id}","",Method.GET,id)
        UserReqRes user = res.data
        println user.toString()
        assertEquals(id, user.id)
    }

    @Test
    void "add user from model"(){
        int id = faker.getRandom().person().hashCode();
        String email = faker.getEmail()
        String fname = faker.getMaleFirstName()
        String lname = faker.getFemaleFirstName()
        String avatar = faker.getWebsite()

        UserReqRes payload = new UserReqRes(id, email, fname, lname, avatar)
        def user = requests.send("api/users", payload, Method.POST)
        println user.toString()
        assertEquals(email, user.email)
        assertEquals(fname, user.first_name)
        assertEquals(lname, user.last_name)
    }

    @Test
    void "modify user with PUT method"(){
        String name = faker.getMaleFirstName()
        int age = faker.getRandom().person().getAge()
        String city = faker.getCity()
        def payload = new User(['name': name, 'age': age, 'city': city])
        def user = requests.send("http://${jsonAPI}/users/{id}", payload,Method.PUT,3)
        assertEquals(name, user.name)
        assertEquals(age, user.age)
        assertEquals(city, user.city)
    }

    @Test
    void "update user details with PATCH method"(){
        String city = faker.getCity()
        int age = faker.getRandom().person().getAge()
        def payload =  requests.trimPOGO( new User(['age': age, 'city': city]), 'name')
        def user = requests.send("http://${jsonAPI}/users/{id}", payload,Method.PATCH,"BOwl-Rb")
        println user.toString()
        assertEquals(age, user.age)
        assertEquals(city, user.city)
    }

    @Test
    void "update user from json string"() {
        String payload =
                """
                    {"job": "Cloud Engineer"}
                """
        def user = requests.send("api/users/{id}",payload,Method.PATCH,"4")
        println user.toString()
        assertEquals("Cloud Engineer", user.job)
    }

    @Test
    void "delete a user"(){
        Response res = requests.sendRequest("api/users/{id}","",Method.DELETE,"12")
        res.then().statusCode(equalTo(204))
    }
}
