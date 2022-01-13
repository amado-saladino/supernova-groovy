package helpers

import groovy.yaml.YamlSlurper

class ConfigReader {
    private static ConfigReader INSTANCE = null
    private final YamlSlurper yml = new YamlSlurper()
    private static configs

    private ConfigReader() {  }

    private static ConfigReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigReader()
        }
        INSTANCE
    }

    static loadConfig(String path='config.yaml') {
        getInstance().readConfigFile(path)
    }

    private readConfigFile(final String path) {
        BufferedReader buffer = new BufferedReader(new FileReader("src/main/resources/$path"))
        yml.parse(buffer)
    }
}
