package Schibsted.test.filesearch.controller;

import Schibsted.test.filesearch.model.File;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DirectoryFileSearchController {

    List<File> files;

    public DirectoryFileSearchController(List<File> files) {
        this.files = files;
    }

    public List<SearchResult> search(String searchQuery) {

        final List<SearchResult> searchResults = new LinkedList<>();

        final String[] searchQueries = searchQuery.split(" ");
        files.forEach(file -> {
            final int percent = percentOfWordsInFile(searchQueries, file);
            searchResults.add(new SearchResult(percent, file.getFileName()));
        });
        return searchResults;
    }

    private int percentOfWordsInFile(String[] searchQueries, File file) {
        final double count = numberOfWordsInFile(searchQueries, file);
        return calculatePercentageAndRound(searchQueries.length, count);
    }

    private int calculatePercentageAndRound(int searchQueries, double count) {
        return (int) Math.round(count / searchQueries * 100);
    }

    private long numberOfWordsInFile(String[] searchQueries, File file) {
        return Arrays.stream(searchQueries).filter(query -> file.getText().contains(query)).count();
    }

    public static class SearchResult {

        private int match;
        private String fileName;

        public SearchResult(int match, String fileName) {
            this.match = match;
            this.fileName = fileName;
        }

        public int getMatch() {
            return match;
        }

        public void setMatch(int match) {
            this.match = match;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SearchResult)) return false;
            SearchResult that = (SearchResult) o;
            return getMatch() == that.getMatch() &&
                    Objects.equals(getFileName(), that.getFileName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getMatch(), getFileName());
        }

        @Override
        public String toString() {
            return fileName.split("\\.")[0] + " : " + match + "%";
        }
    }
}
