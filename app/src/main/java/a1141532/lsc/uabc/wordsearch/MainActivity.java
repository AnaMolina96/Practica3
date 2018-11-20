package a1141532.lsc.uabc.wordsearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static TextView textViewNameWordSearch;
    private static List<Button> editWords;
    public static int size;
    private static Context context;
    private static Random random;
    private static String alphabet="abcdefghijklemnopqrstuvwxyz";
    private static GridLayout gridLayoutWordSearch;
    private static android.support.v7.widget.GridLayout gridWords;
    private boolean toggleSoup;
    String words_puzzle = "", puzzleName = "", size_puzzle ="";
    //int vertical = 14, horizontal = 14;
    private static List<Word> words = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toggleSoup = true;

        context = this.getBaseContext();
        random = new Random();
        setContentView(R.layout.activity_main);
        editWords = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        puzzleName = preferences.getString("key_name_puzzle", "");
        size_puzzle = preferences.getString("key_size_puzzle", "");
        words_puzzle = preferences.getString("key_words_puzzle", "");

        size = Integer.parseInt(size_puzzle);

        textViewNameWordSearch = findViewById(R.id.textViewNameWordSearch);

        gridLayoutWordSearch = findViewById(R.id.gridLayoutWordSearch);
        gridLayoutWordSearch.setColumnCount(size);

        gridWords = findViewById(R.id.gridWords);

        Log.d("AAA","Size: " + size_puzzle);
        //Toast.makeText(getApplicationContext(),"Size" + size_puzzle,Toast.LENGTH_LONG).show();

        fillMatriz(size);

        textViewNameWordSearch.setText(puzzleName);

    }

    public void fillMatriz(int size){
        for(int i = 0; i < size; i++){
            for(int k = 0; k < size; k++){
                TextView txt = new TextView(this);
                txt.setPadding(7,0,7,0);
                txt.setTextSize(20);
                txt.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                txt.setText(String.valueOf(alphabet.charAt(random.nextInt(alphabet.length()))));
                gridLayoutWordSearch.addView(txt, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    public void paintWordSearch(){
        Log.i("PAINT", "hi");
        TextView txt;
        if(toggleSoup){
            //paint to white all
            for(Word w: words){
                for(int index : w.getIndexes()){
                    txt = (TextView) gridLayoutWordSearch.getChildAt(index);
                    txt.setBackgroundColor(0x00000000);
                    txt.setHighlightColor(0x00000000);

                }
            }
        }else{
            //highlight
            for(Word w: words){
                for(int index : w.getIndexes()){
                    txt = (TextView) gridLayoutWordSearch.getChildAt(index);
                    txt.setTextColor(R.color.white);
                    txt.setHighlightColor(R.color.white);
                    txt.setBackgroundColor(R.color.white);

                }
            }
        }
        toggleSoup = !toggleSoup;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_show_puzzle:
               startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                //startActivityForResult(intent,SETTINGS_ACTIVITY);
                return true;
            case R.id.look_words:
                    paintWordSearch();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public static void addWord(Word word, int index){
        switch (word.getOrientation()){
            case Word.ORIENTATION_REVERSE:
                String word_reverse="";
                for(int x=word.getWord().length()-1;x>=0;x--){
                    word_reverse = word_reverse + word.getWord().charAt(x);
                }
                word.setWord(word_reverse);
                break;
            case Word.ORIENTATION_NORMAL:
                break;
        }
        beforeDeleteWord(index);
        words.remove(index);
        words.add(index,word);
        onAddWord(word);
    }

    public static void addWord(Word word){
        switch (word.getOrientation()){
            case Word.ORIENTATION_REVERSE:
                String word_reverse="";
                for(int x=word.getWord().length()-1;x>=0;x--){
                    word_reverse = word_reverse + word.getWord().charAt(x);
                }
                word.setWord(word_reverse);
                break;
            case Word.ORIENTATION_NORMAL:
                break;
        }
        words.add(word);
        onAddWord(word);
    }

    @SuppressLint("ResourceAsColor")
    private static void onAddWord(Word word){
        int[] indexes = getAvailableWordIndexes(word);
        printArray(indexes);
        List<View> textViews = new ArrayList<>();

        // update edit buttons
        gridWords.removeAllViews();
        for(Word w: words){
            Button btn = new Button(context);
            btn.setText(w.getWord());
            btn.setOnClickListener((v) -> {
                Intent intent = new Intent(context, EditWordActivity.class);
                intent.putExtra("index", words.indexOf(w));
                intent.putExtra("wordname", w.getWord());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
            gridWords.addView(btn);
        }

        switch (word.getOrientation()) {
            case Word.ORIENTATION_REVERSE:
                for(int i = word.getWord().length()-1; i>=0; i--){
                    char letter = word.getWord().charAt(i);
                    int index = indexes[i];
                    word.addIndex(index);
                    TextView txt = new TextView(context);
                    txt.setPadding(7, 0, 7, 0);
                    txt.setTextSize(18);
                    txt.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    txt.setText(String.valueOf(letter));
                    txt.setBackgroundColor(R.color.green);
                    txt.setTextColor(R.color.design_default_color_primary_dark);
                    textViews.add(txt);
                }
                break;
            case Word.ORIENTATION_NORMAL:
                for(int i = 0; i<word.getWord().length(); i++){
                    char letter = word.getWord().charAt(i);
                    int index = indexes[i];
                    word.addIndex(index);
                    TextView txt = new TextView(context);
                    txt.setPadding(7, 0, 7, 0);
                    txt.setTextSize(18);
                    txt.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    txt.setText(String.valueOf(letter));
                    txt.setBackgroundColor(R.color.green);
                    txt.setTextColor(R.color.design_default_color_primary_dark);
                    textViews.add(txt);
                }
                break;
        }

        for(int i = 0; i<textViews.size(); i++){
            gridLayoutWordSearch.removeViewAt(word.getIndexes().get(i));
            gridLayoutWordSearch.addView(textViews.get(i), word.getIndexes().get(i));
        }
    }

    private static void beforeDeleteWord(int index){
        Word word = words.get(index);
        gridWords.removeViewAt(index);
        for(int i: word.getIndexes()){
            gridLayoutWordSearch.removeViewAt(i);
            TextView t = new TextView(context);
            t.setPadding(7, 0, 7, 0);
            t.setTextSize(18);
            t.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            char letter = alphabet.charAt(random.nextInt(alphabet.length()));
            t.setText(String.valueOf(letter));
            gridLayoutWordSearch.addView(t, i);
        }
    }


    private static int[] getAvailableWordIndexes(Word word){
        List<int[]> indexes = new ArrayList<>();
        int wordSize = word.getWord().length();
        int[] aux = new int[wordSize];
        int max = Math.abs(wordSize - MainActivity.size);
        int counter = 0;
        boolean isValid = true;
        switch(word.getMode()){
            case Word.POSITION_HORIZONTAL:
                for(int row = 0;row<MainActivity.size;row++){
                    for(int col=0; col<MainActivity.size;col++) {
                       for(int val = 0; val<wordSize;val++){
                           if( (col+val) < MainActivity.size){
                               int index = pointToIndex(row, col + val);
                               isValid = isValid && !indexIsUsed(index);
                               if(isValid){
                                   aux[val] = index;
                               }
                           }else{
                               isValid = false;
                           }
                       }
                       if(isValid){
                           indexes.add(aux);
                           aux = new int[wordSize];
                       }
                       isValid = true;
                    }
                }
                break;
            case Word.POSITION_VERTICAL:
                for(int col = 0;col<MainActivity.size;col++){
                    for(int row=0; row<MainActivity.size;row++) {
                        for(int val = 0; val<wordSize;val++){
                            if( (row+val) < MainActivity.size){
                                int index = pointToIndex(row+val, col);
                                isValid = isValid && !indexIsUsed(index);
                                if(isValid){
                                    aux[val] = index;
                                }
                            }else{
                                isValid = false;
                            }
                        }
                        if(isValid){
                            indexes.add(aux);
                            aux = new int[wordSize];
                        }
                        isValid = true;
                    }
                }
                break;
            case Word.POSITION_DIAGONAL:
                for(int row = 0;row<MainActivity.size;row++){
                    for(int col=0; col<MainActivity.size;col++) {
                        for(int val = 0; val<wordSize;val++){
                            if( (col+val) < MainActivity.size && (row+val) < MainActivity.size){
                                int index = pointToIndex(row + val, col + val);
                                isValid = isValid && !indexIsUsed(index);
                                if(isValid){
                                    aux[val] = index;
                                }
                            }else{
                                isValid = false;
                            }
                        }
                        if(isValid){
                            indexes.add(aux);
                            aux = new int[wordSize];
                        }
                        isValid = true;
                    }
                }
                break;
        } //end switch
        int i = random.nextInt(indexes.size());
        return indexes.get(i);

    }

    private static void printArray(int[] array){
        String val = "";
        for(int i: array){
            val += i + " ";
        }
        Log.i("INDEX",val);
    }

    private static int pointToIndex(int row, int col){
        return (row * size) + col;
    }

    private static boolean indexIsUsed(int index){
        boolean isUsed = false;
        for(Word w: words){
            isUsed = w.hasIndex(index);
            if(isUsed){
                return isUsed;
            }
        }
        return isUsed;
    }



}
