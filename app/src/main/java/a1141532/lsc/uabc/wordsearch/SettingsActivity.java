package a1141532.lsc.uabc.wordsearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Estas dos lineas de abajo nos ayuda a tener nuestra barra de menu donde podrémos visualizarlo
        // arriba de nuestro Fragment donde esta nuestra configuración de la aplicación.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PuzzleNameFragment()).commit();

    }

    // Con este método de tipo PreferenceFragment es un Fragmento donde nos guarda todos los
    // datos que nos proporciona el usuario y lo guarda en memoria, este sirve como un settings
    // en nuestra aplicación.

    ////
    //
    //
    //
    //
    ///




    public static class PuzzleNameFragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Aqui se agrega nuestro archivo de preferences.xml cuando se llama al
            // método PuzzleNameFragment(), aqui unicamente guarda en un string los
            // datos que ingreso el usuario
            addPreferencesFromResource(R.xml.preferences);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String puzzleName = preferences.getString("key_name_puzzle", "");
            String size_puzzle = preferences.getString("key_size_puzzle", "");
            String words_puzzle = preferences.getString("key_words_puzzle", "");


        }
    }

    //Este método nos redirige a nuestro MainActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
