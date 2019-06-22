package Schibsted.test.filesearch.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class directory {

    private List<File> files;

    public directory(List<File> files) {
        this.files = files;
    }

    public directory(Path direcotry) {

        try (Stream<Path> paths = Files.walk(direcotry)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int numberOfFiles(){
        return files.size();
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
