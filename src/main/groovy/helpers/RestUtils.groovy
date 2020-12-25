package helpers

import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import io.restassured.response.Response
import io.restassured.http.Method
import io.restassured.http.ContentType
import static io.restassured.RestAssured.given

class RestUtils {
    JsonSlurper json

    RestUtils() {
        json = new JsonSlurper()
    }

    /**
     * Replace placeholders with assigned values
     * @param str containing the placeholders
     * @param vars assigned values
     * @return string with placeholders resolved
     */
    String resolveVarsInString(String str, Map vars) {
        for (e in vars) {
            String k = e.key
            String v = e.value
            String template = "\\{\\{$k\\}\\}"
            str = str.replaceAll(template, v)
        }
        str
    }

    /**
     * deserialize a response into object
     *
     * @param res instance of response
     * @return deserialized object of the response
     */
    def deserialize(Response res) {
        json.parseText(res.body.asString())
    }

    /**
     * trim null values from POGO
     * @param obj instance of POGO
     * @param fields field names to exclude
     * @return string JSON without nulls
     */
    def trimPOGO(obj,CharSequence... fields) {
        def generator = new JsonGenerator.Options()
                .excludeNulls()
                .excludeFieldsByName(fields)
                .build()

        generator.toJson(obj)
    }

    /**
     * Sends request with query parameters
     * @param url url
     * @param body request payload
     * @param method HTTP method
     * @param queryParams query parameters
     * @return Response object
     */
    def sendWithQueryParams(String url, String body, Method method, Map queryParams) {
        def res = given().queryParams(queryParams).body(body).request(method,url)
        deserialize(res)
    }

    /**
     * Send a request with path parameters
     * @param url request url
     * @param body payload to send, leave out if GET request
     * @param method HTTP method
     * @param pathParams Values to replace path parameters
     * @return Response object
     */
    def send(String url, body, Method method, Object... pathParams) {
        def res = given().body(body).contentType(ContentType.JSON).request(method,url,pathParams)
        deserialize(res)
    }
}
