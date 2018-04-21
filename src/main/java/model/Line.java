package model;

public class Line{

    private String line;
    private int lineLength;
    private int minWord;
    private int maxWord;
    private int AverageWord;

    public Line(String line, int lineLength, int minWord, int maxWord, int averageWord) {
        this.line = line;
        this.lineLength = lineLength;
        this.minWord = minWord;
        this.maxWord = maxWord;
        AverageWord = averageWord;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public int getMinWord() {
        return minWord;
    }

    public void setMinWord(int minWord) {
        this.minWord = minWord;
    }

    public int getMaxWord() {
        return maxWord;
    }

    public void setMaxWord(int maxWord) {
        this.maxWord = maxWord;
    }

    public int getAverageWord() {
        return AverageWord;
    }

    public void setAverageWord(int averageWord) {
        AverageWord = averageWord;
    }
}
