package Schibsted.test.filesearch.util;

import Schibsted.test.filesearch.model.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    public static List<File> readFilesFromDirectory(Path directory) {
        final ArrayList<File> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(directory)) {
            files.addAll(paths
                    .filter(Files::isRegularFile)
                    .map(path -> new File(path.getFileName().toString(), getContent(path)))
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            //TODO implement error handling
            e.printStackTrace();
        }
        return files;
    }

    private static String getContent(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8")).stream().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            //TODO implement error handling
            e.printStackTrace();
            return "";
        }
    }

}
