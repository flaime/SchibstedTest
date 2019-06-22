package Schibsted.test.filesearch.view;

import Schibsted.test.filesearch.controller.DirectoryFileSearchController;
import Schibsted.test.filesearch.util.FileReader;
import Schibsted.test.filesearch.model.File;

import java.io.*;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class InteractiveDirectorySearch {

    private final BufferedReader reader;
    private final Path directory;

    private final PrintStream out;
    private final InputStream input;


    public InteractiveDirectorySearch(Path directory, InputStream input, PrintStream out) {
        this.directory = directory;
        this.out = out;
        this.input = input;
        reader = new BufferedReader(new InputStreamReader(input));
    }

    public void run() {

        final List<File> files = FileReader.readFilesFromDirectory(directory);
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(files);

        print(files.size() + " file" + ((files.size() > 1) ? "s" : "") + " read in directory " + directory);

        mainLoop(directoryFileSearchController);
    }

    private void mainLoop( DirectoryFileSearchController directoryFileSearchController) {
        boolean run = true;
        while (run) {
            String name = readLine();

            if (name.equalsIgnoreCase("exit")) {
                run = false;
            } else {
                printSearchResult(directoryFileSearchController.search(name));
            }
        }
    }

    private void printSearchResult(List<DirectoryFileSearchController.SearchResult> searchResults) {
        searchResults.sort(Comparator
                .comparingInt(DirectoryFileSearchController.SearchResult::getMatch).reversed()
                .thenComparing(DirectoryFileSearchController.SearchResult::getFileName));


        if (isMatchFound(searchResults))
            searchResults.stream()
                    .filter(searchResult -> searchResult.getMatch() > 0)
                    .limit(10)
                    .forEach(searchResult -> print(searchResult.toString()));
        else
            print("no matches found");
    }

    private boolean isMatchFound(List<DirectoryFileSearchController.SearchResult> search) {
        return !search.stream()
                    .findFirst()
                    .filter(searchResult -> searchResult.getMatch() == 0)
                    .isPresent();
    }

    private void print(String name) {
        out.println(name);
    }

    private String readLine() {
        out.print("Search> ");

        try {
            return reader.readLine();
        } catch (IOException e) {
            out.println("Could not read input ignoring input.");
            e.printStackTrace();
            return "";
        }
    }
}
