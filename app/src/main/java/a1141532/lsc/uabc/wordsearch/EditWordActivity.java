package a1141532.lsc.uabc.wordsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {

    private Integer index;
    private String wordname;
    private static EditText txtWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                index = null;
            }else{
                index = extras.getInt("index");
                wordname = extras.getString("wordname");
            }
        }else{
            String aux = (String) savedInstanceState.getSerializable("index");
            wordname = (String) savedInstanceState.getSerializable("wordname");
            index = Integer.parseInt(aux);
        }

        txtWord = (EditText) findViewById(R.id.txtEditWordName);
        Button saveChanges = (Button) findViewById(R.id.btnEditSaveChanges);
        RadioButton radioHor = (RadioButton) findViewById(R.id.radioEditHorizontal);
        RadioButton radioVer = (RadioButton) findViewById(R.id.radioEditVertical);
        RadioButton radioDiag = (RadioButton) findViewById(R.id.radioEditDiagonal);

        RadioButton radioNormal = (RadioButton) findViewById(R.id.radioEditNormal);
        RadioButton radioRev = (RadioButton) findViewById(R.id.radioEditReverse);

        txtWord.setText(wordname);

        saveChanges.setOnClickListener((v) ->{
            Word word = new Word();
            word.setWord(txtWord.getText().toString().trim());
            if(word.getWord().length() > MainActivity.size){
                Toast.makeText(this,"Word is too large", Toast.LENGTH_SHORT).show();
                return;
            }

            if(radioHor.isChecked()){
                word.setMode(Word.POSITION_HORIZONTAL);
            }else if(radioVer.isChecked()){
                word.setMode(Word.POSITION_VERTICAL);
            }else if(radioDiag.isChecked()){
                word.setMode(Word.POSITION_DIAGONAL);
            }

            if(radioNormal.isChecked()){
                word.setOrientation(Word.ORIENTATION_NORMAL);
            }else if(radioDiag.isChecked()){
                word.setOrientation(Word.ORIENTATION_REVERSE);
            }

            MainActivity.addWord(word, index);
            finish();
        });


    }
}
