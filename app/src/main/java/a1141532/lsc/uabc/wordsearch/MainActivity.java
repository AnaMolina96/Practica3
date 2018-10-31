package a1141532.lsc.uabc.wordsearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static TextView textViewNameWordSearch,textViewWordsToSearch;
    private static int size;
    private static Context context;
    private static Random random;
    private String alphabet="abcdefghijklemnopqrstuvwxyz";
    private static GridLayout gridLayoutWordSearch;
    String words_puzzle = "", puzzleName = "", size_puzzle ="";
    //int vertical = 14, horizontal = 14;
    private static List<Word> words = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this.getBaseContext();
        random = new Random();
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        puzzleName = preferences.getString("key_name_puzzle", "");
        size_puzzle = preferences.getString("key_size_puzzle", "");
        words_puzzle = preferences.getString("key_words_puzzle", "");

        size = Integer.parseInt(size_puzzle);

        textViewWordsToSearch = findViewById(R.id.textViewWordsToSearch);
        textViewNameWordSearch = findViewById(R.id.textViewNameWordSearch);

        gridLayoutWordSearch = findViewById(R.id.gridLayoutWordSearch);
        gridLayoutWordSearch.setColumnCount(size);

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
        TextView txt = new TextView(this);

        for(int i = 0; i < size; i++){
            for(int k = 0; k < size; k++){
                txt.setBackgroundColor(R.color.black);
                txt.setTextColor(R.color.white);
            }
        }
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
            default:
                return super.onOptionsItemSelected(item);
        }

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

        int row = random.nextInt(size - 4) + 2;
        int col = random.nextInt(size - 4) + 2;

        for(char c: word.getWord().toCharArray()){
            int toIndex = (row*size) + col;

            //deleting
            gridLayoutWordSearch.removeViewAt(toIndex);
            //adding
            TextView txt = new TextView(context);
            txt.setPadding(7,0,7,0);
            txt.setTextSize(18);
            txt.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            txt.setText(String.valueOf(c));
            txt.setBackgroundColor(R.color.green);
            txt.setTextColor(R.color.design_default_color_primary_dark);
            gridLayoutWordSearch.addView(txt,toIndex);

            //setting up next letter
            switch (word.getMode()){
                case Word.POSITION_HORIZONTAL:
                    col++;
                    break;
                case Word.POSITION_VERTICAL:
                    row++;
                    break;
                case Word.POSITION_DIAGONAL:
                    row++;
                    col++;
                    break;

            }
        }
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
        textViewWordsToSearch.append(word.getWord() + "\t");
    }

}
