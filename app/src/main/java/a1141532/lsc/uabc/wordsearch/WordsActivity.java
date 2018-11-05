package a1141532.lsc.uabc.wordsearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.prefs.Preferences;

public class WordsActivity extends AppCompatActivity implements View.OnClickListener {

    Button addButton;
    EditText wordToAdd;
    RadioButton position_vertical,position_horizontal,position_diagonal,
            orientation_normal,orientation_reverse;
    RadioGroup orientationRadioGroup,modeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.add_word);

        addButton = (Button) findViewById(R.id.addWordButton);
        wordToAdd = (EditText) findViewById(R.id.editTextWord);
        orientationRadioGroup = (RadioGroup) findViewById(R.id.orientationRadioGroup);
        position_diagonal = (RadioButton) findViewById(R.id.position_diagonal);
        position_horizontal = (RadioButton) findViewById(R.id.position_horizontal);
        position_vertical = (RadioButton) findViewById(R.id.position_vertical);
        orientation_normal = (RadioButton) findViewById(R.id.orientation_normal);
        orientation_reverse = (RadioButton) findViewById(R.id.orientation_reverse);
        addButton.setOnClickListener(this);
    }

       // addWordButtonOnClick.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String wordName = wordToAdd.getText().toString();
                String orientation="";
                String position = "";

                Word word = new Word();

               if(position_vertical.isChecked()){
                   position = "vertical";
               }if(position_horizontal.isChecked()){
                    position = "horizontal";
               }if(position_diagonal.isChecked()){
                    position = "diagonal";
               }

               if(orientation_normal.isChecked()){
                   orientation = "normal";
               }if(orientation_reverse.isChecked()){
                   orientation = "reverse";
                }

                Log.i("orientation","orientacion: "+orientation+"");
                switch(orientation){
                    case "normal":
                        word.setOrientation(Word.ORIENTATION_NORMAL);
                        break;
                    case "reverse":
                        word.setOrientation(Word.ORIENTATION_REVERSE);
                        break;
                    default:
                        return;
                }
                Log.i("position","posicion: "+position+"");
                switch (position){
                    case "horizontal":
                        word.setMode(Word.POSITION_HORIZONTAL);
                        break;
                    case "vertical":
                        word.setMode(Word.POSITION_VERTICAL);
                        break;
                    case "diagonal":
                        word.setMode(Word.POSITION_DIAGONAL);
                        break;
                    default:
                        return;
                }

                if(!wordName.trim().isEmpty()){
                   Toast.makeText(getApplicationContext(),""+wordName,Toast.LENGTH_LONG);
                    word.setWord(wordName);
                    MainActivity.addWord(word);
                    Log.i("info","Letra: " + wordName+ " orientacion " + orientation+ " position: " + position);
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }

            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            startActivity(new Intent(WordsActivity.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}

