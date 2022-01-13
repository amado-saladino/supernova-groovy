package helpers

import groovy.json.JsonSlurper

class JsonReader {
    static Iterator<Object> provider(file, key) {
        def json = new JsonSlurper().parse(new File(file))
        json[key].iterator()
    }
}
