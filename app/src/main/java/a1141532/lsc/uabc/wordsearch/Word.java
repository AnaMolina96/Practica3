package a1141532.lsc.uabc.wordsearch;

import java.util.List;
import java.util.ArrayList;

public class Word {

    public static final int POSITION_HORIZONTAL = 1;
    public static final int POSITION_VERTICAL = 2;
    public static final int POSITION_DIAGONAL = 3;

    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_REVERSE = 2;


    private String word;
    private int mode;
    private int orientation;
    private List<Integer> indexes;

    public Word(){
        indexes = new ArrayList<>();
    }

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

    public void addIndex(Integer ... index){
        for(Integer i: index){
            indexes.add(i);
        }
    }

    public boolean hasIndex(Integer index){
        return indexes.contains(index);
    }

    public List<Integer> getIndexes(){
        return indexes;
    }

    public void clearIndexes(){
        indexes.clear();
    }
}
