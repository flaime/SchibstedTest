package Schibsted.test.filesearch.controller;

import Schibsted.test.filesearch.model.File;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DirectoryFileSearchControllerTest {

    @Test
    void searchSingleFile() {

        final File som_text_in_file = new File("file number one.txt", "som text in file");
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList(som_text_in_file));

        final List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("in");

//        final DirectoryFileSearchController.SearchResult searchResult = new DirectoryFileSearchController.SearchResult(100, "file number one.txt");

        assertEquals(100, search.get(0).getMatch());
        assertEquals("file number one.txt", search.get(0).getFileName());


    }

    @Test
    void searchDuplicatedWords() {

        final File som_text_in_file = new File("file number one.txt", "som text in text file\ntext");
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList(som_text_in_file));

        final List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("text in nonexisting");


        assertEquals(67, search.get(0).getMatch());
        assertEquals("file number one.txt", search.get(0).getFileName());


    }

    @Test
    void searchMultipleFile() {

        final File som_text_in_file = new File("file number one.txt", "som text in file");
        final File other_text = new File("file number two.txt", "som other text in file");
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList(som_text_in_file, other_text));

        final List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("in other");

        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(50,"file number one.txt")));
        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(100,"file number two.txt")));


    }

    @Test
    void searchNonMatch() {

        final File som_text_in_file = new File("file number one.txt", "som text in file");
        final File other_text = new File("file number two.txt", "som other text in file");
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList(som_text_in_file, other_text));

        final List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("kalas kul");

        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(0,"file number one.txt")));
        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(0,"file number two.txt")));


    }

    @Test
    void searchMultilineFile() {
        final File som_text_in_file = new File("file number one.txt", "som text in file\nother hello maven text in\neven more in third row");
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList(som_text_in_file));

        List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("third");

        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(100,"file number one.txt")));

        search = directoryFileSearchController.search("hello maven");
        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(100,"file number one.txt")));

        search = directoryFileSearchController.search("hello nonexisting");
        assertThat(search, hasItem(new DirectoryFileSearchController.SearchResult(50,"file number one.txt")));


    }

    @Test
    void searchWhiteoutFiles() {
        final DirectoryFileSearchController directoryFileSearchController = new DirectoryFileSearchController(Arrays.asList());

        final List<DirectoryFileSearchController.SearchResult> search = directoryFileSearchController.search("kalas kul");

        assertThat(search, empty());


    }
}