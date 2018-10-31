package a1141532.lsc.uabc.wordsearch;

public class Word {

    public static final int POSITION_HORIZONTAL = 1;
    public static final int POSITION_VERTICAL = 2;
    public static final int POSITION_DIAGONAL = 3;

    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_REVERSE = 2;


    private String word;
    private int mode;
    private int orientation;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
