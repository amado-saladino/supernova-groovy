import helpers.ConfigReader
import helpers.FileLoader
import helpers.RestUtils
import io.restassured.RestAssured
import io.restassured.http.Method
import models.User
import models.UserReqRes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import static org.junit.jupiter.api.Assertions.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestFour{
    RestUtils requests
    @BeforeAll()
    void init() {
        def conf = ConfigReader.loadConfig()
        RestAssured.baseURI = conf.uri.rest
        requests = new RestUtils()
    }
    /**
     * https://reqres.in/
     */
    @Test
    void "get all users from reqres"() {
        def res = requests.sendWithQueryParams("api/users","",Method.GET,['per_page':20])
        assertTrue res.data.size == 12

        List<UserReqRes> users = res.data
        assertTrue users.size == 12
        println users*.first_name
    }
    /**
     * http://localhost:3000/users
     */
    @Test
    void "add user from json file"() {
        String user_txt = new FileLoader("data/user-payload.json")

        def res = requests.send('users',user_txt,Method.POST)
        println res
    }
    @Test
    void "add user and deserialize the response as POGO"(){
        String user_txt = new FileLoader("data/user-placeholders.json")
        def body = requests.resolveVarsInString(user_txt,['name':'Khaled','age':46,'city':'Giza'])
        User user = requests.send('users',body,Method.POST)
        println user.toString()
    }
    /**
     * http://reqres.in/api/users
     */
    @Test
    void "get single user from reqres by id"(){
        def res = requests.send("api/users/{id}","",Method.GET,"1")
        UserReqRes user = res.data
        println user.toString()
    }
    /**
     * http://localhost:3000/users
     */
    @Test
    void "add user from POGO"(){
        User user = new User('',"Arabi",22, "Cairo")
        def body = requests.trimPOGO(user)
        User r_user = requests.send("users",body, Method.POST)
        println r_user.toString()
    }
    @Test
    void "add user from plain json"(){
        String payload = "{\"name\":\"Hagar\",\"age\":23,\"city\": \"Paris\"}"
        User user = requests.send("users",payload, Method.POST)
        println user.toString()
    }
    @Test
    void "modify user from json"(){
        def body = "{\"name\": \"Hassan\",\"age\": 20,\"city\": \"Giza\"}"
        User user = requests.send("users/{id}",body,Method.PUT,1)
        println user.toString()
    }
    @Test
    void "modify user from POGO"(){
        User user = new User(name: 'Rashwan',age: 30,city: 'Dubai')
        User r_user = requests.send("users/{id}",user,Method.PUT,3)
        println r_user.toString()
    }
    @Test
    void "update user details from json"(){
        String payload = "{\"name\": \"Walid\", \"age\": 35}"
        User user = requests.send("users/{id}",payload,Method.PATCH,"oJlIT-h")
        println user.toString()
    }
    @Test
    void "update user details from POGO"(){
        User user = new User(name: 'Shadi')
        def body = requests.trimPOGO(user,'age')
        User r_user = requests.send("users/{id}",body,Method.PATCH,"xyopcDW")
        println r_user.toString()
    }
    @Test
    void "delete user"(){
        def res = requests.send("users/{id}","",Method.DELETE,"s4A66ii")
        println res
    }
}
