import helpers.YAMLReader
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class TestYAML {
    @ParameterizedTest
    @MethodSource("yaml_containers")
    void "use YAML file as data source"(container) {
        println("${container.name} - ${container.image}")
    }

    static yaml_containers() {
        YAMLReader.provider('data/pod.yml','spec.containers')
    }
}
