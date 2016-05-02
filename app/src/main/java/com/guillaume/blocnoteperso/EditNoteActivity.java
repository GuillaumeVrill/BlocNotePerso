package com.guillaume.blocnoteperso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EditNoteActivity extends Activity {

    TextView titreNote;
    EditText texte;

    public String read(String fpath){
        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;
    }

    public Boolean write(String fpath, String fcontent){
        try {
            File file = new File(fpath);

            if (!file.exists()) {
                Toast.makeText(EditNoteActivity.this, "ERROR: Fichier introuvable", Toast.LENGTH_SHORT).show();
            }
            else {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(fcontent);
                bw.close();
                Toast.makeText(EditNoteActivity.this, "Fichier sauvegardé!", Toast.LENGTH_SHORT).show();
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        if(intent != null){
            String fName = intent.getStringExtra(SearchNoteActivity.EXTRA_MESSAGE);

            //Titre du fichier:
            titreNote = (TextView)findViewById(R.id.nomFichier);
            titreNote.setText(fName);

            //Récupération du contenu du fichier:
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "personnalNotes" + File.separator + fName);
            String content = read(file.toString());

            //On place le contenu dans le texte:
            texte = (EditText)findViewById(R.id.editText);
            texte.setText(content);
        }

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupération du contenu:
                texte = (EditText)findViewById(R.id.editText);
                String content = texte.getText().toString();

                //Sauvegarde du contenu dans le fichier texte:
                titreNote = (TextView)findViewById(R.id.nomFichier);
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "personnalNotes" + File.separator + titreNote.getText().toString());
                write(file.toString(), content);
                //Rafraichissement de l'activity principale:

                //Fermeture de l'activity et retour à l'activity précédente:
                EditNoteActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
