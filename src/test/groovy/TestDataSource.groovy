import helpers.ExcelReader
import helpers.JsonReader
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class TestDataSource {
    @ParameterizedTest
    @MethodSource("json_data_users")
    void "use json file as data source"(user) {
        println("test method...")
        println "${user['name']}\t${user['email']}\t${user['password']}"
        println "${user.address.city} - ${user.address.country}"
    }

    static json_data_users() {
        JsonReader.provider('data/data.json','users')
    }
    //-----------------------------------------------------
    @ParameterizedTest
    @MethodSource("excel_data_users")
    void "use excel sheet as data source"(user){
        println "${user.Name}\t${user.Phone}\t${user.Email}"
    }

    static excel_data_users(){
        ExcelReader.provider('data/users.xlsx','users')
    }
}
