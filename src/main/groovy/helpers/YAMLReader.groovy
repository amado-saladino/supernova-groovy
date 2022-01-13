package helpers

import groovy.yaml.YamlSlurper

class YAMLReader {
    private static final YamlSlurper yml = new YamlSlurper()

    static Iterator<Object> provider(file, expression) {
        BufferedReader buffer = new BufferedReader(new FileReader(file))
        def yaml = yml.parse(buffer)
        Eval.x(yaml, "x.${expression}").iterator()
    }
}
