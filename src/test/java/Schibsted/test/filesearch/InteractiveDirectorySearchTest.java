package Schibsted.test.filesearch;

import Schibsted.test.filesearch.view.InteractiveDirectorySearch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class InteractiveDirectorySearchTest {

    private InteractiveDirectorySearch interactiveDirectorySearch;

//    private InputStream inputStream = new Input

    @BeforeEach
    void setUp() {
//        interactiveDirectorySearch = new InteractiveDirectorySearch()
    }

    @AfterEach
    void tearDown() {
//        String initialString = "./";
//        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
//
//        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {
//            interactiveDirectorySearch = new InteractiveDirectorySearch(Paths.get("./"),targetStream,ps);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    @Test
    void runSimpleFile() {
        String initialString =
                "nonexisting\n" +
                "som\n" +
                "exit";

        String data = testRun(initialString, "src/test/resources/testFile");
        final String[] rows = data.split("\n");

        System.out.println(data);
        assertEquals("1 file read in directory src/test/resources/testFile", rows[0]);
        assertEquals("Search> no matches found", rows[1]);
        assertEquals("Search> test file one : 100%", rows[2]);

    }

    @Test
    void runMultipleFiles() {
        String initialString =
                "nonexisting\n" +
                        "som\n" +
                        "exit";

        String data = testRun(initialString, "src/test/resources/testFiles");
        final String[] rows = data.split("\n");

        System.out.println(data);
        assertEquals("2 files read in directory src/test/resources/testFiles", rows[0]);
        assertEquals("Search> no matches found", rows[1]);
        assertEquals("Search> test file one : 100%", rows[2]);
        assertEquals("test file two : 100%", rows[3]);

    }

    @Test
    void runMultipleFilesPartialMatch() {
        String initialString =
                "nonexisting\n" +
                        "number one\n" +
                        "exit";

        String data = testRun(initialString, "src/test/resources/testFiles");
        final String[] rows = data.split("\n");

        System.out.println(data);
        assertEquals("2 files read in directory src/test/resources/testFiles", rows[0]);
        assertEquals("Search> no matches found", rows[1]);
        assertEquals("Search> test file one : 100%", rows[2]);
        assertEquals("test file two : 50%", rows[3]);

    }


    @Test
    void runMoreThan10Files() {
        String initialString =
                "nonexisting\n" +
                        "number one\n" +
                        "exit";

        String data = testRun(initialString, "src/test/resources/testFilesTen");
        final String[] rows = data.split("\n");

        System.out.println(data);
        assertEquals("11 files read in directory src/test/resources/testFilesTen", rows[0]);
        assertEquals("Search> no matches found", rows[1]);
        assertEquals("Search> test file A : 100%", rows[2]);
        assertEquals("test file B : 50%", rows[3]);
        assertEquals("test file C : 50%", rows[4]);
        assertEquals("test file D : 50%", rows[5]);
        assertEquals("test file E : 50%", rows[6]);
        assertEquals("test file F : 50%", rows[7]);
        assertEquals("test file G : 50%", rows[8]);
        assertEquals("test file H : 50%", rows[9]);
        assertEquals("test file I : 50%", rows[10]);
        assertEquals("test file J : 50%", rows[11]);
        assertEquals("Search> ", rows[12]);

    }

    private String testRun(String initialString, String directory) {
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {

            interactiveDirectorySearch = new InteractiveDirectorySearch(Paths.get(directory),targetStream,ps);
            interactiveDirectorySearch.run();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail();
        }

        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
}