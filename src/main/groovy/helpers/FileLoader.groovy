package helpers

import java.nio.file.Files
import java.nio.file.Paths

class FileLoader {
    private String contents
    FileLoader(String file) {
        try {
            this.contents = new String(Files.readAllBytes(Paths.get(file)))
        } catch (IOException e) {
            printf("The file '%s' does not exist%n", file)
            e.printStackTrace()
        }
    }
    String toString() {
        return contents
    }
}
