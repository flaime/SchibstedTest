package Schibsted.test.filesearch.model;

public class File {

    private String fileName;
    private String text;

    public File(String fileName, String text) {
        this.fileName = fileName;
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
