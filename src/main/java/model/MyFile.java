package model;

import model.Line;

import java.util.List;

public class MyFile{

    private String text;
    private List<Line> lines;


    public MyFile(String file, List<Line> lines) {
        this.text = file;
        this.lines = lines;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
