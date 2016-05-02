package com.guillaume.blocnoteperso;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SearchNoteActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.guillaume.blocnoteperso.MESSAGE";

    TableLayout table;
    TableRow trAdd;
    EditText newFileName;
    Button btnAdd;

    private int id_fichier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_note);

        //Changement de couleur de la barre de titre:
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bck_special_color3)));

        id_fichier = -1;

        // Creation du dossier pour le stockage des notes:
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "personnalNotes");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if(!success){
            Toast.makeText(this, "ERROR: Can't create folder!", Toast.LENGTH_SHORT).show();
        }

        // Recherche des fichiers texte contenu dans le dossier:
        File f[] = folder.listFiles();

        /* Creation du TableLayout pour accueillir les lignes de l'application (scrollable) */
        table = (TableLayout) findViewById(R.id.noteTable);

        //Création des TableRow (des lignes) contenant les fichiers textes de l'application:
        for(int i=0; i<f.length; i++){
            /* Creation d'une ligne de tableau et inflation de cette ligne avec un layout customisé: */
            final TableRow tr = (TableRow) LayoutInflater.from(this).inflate(R.layout.note_table_layout, null);
            /* Définition du contenu de la ligne: */
            ((TextView) tr.findViewById(R.id.txt_row)).setText(f[i].getName());
            tr.setId(i);

            final File file = f[i];
            //LISTENER:
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EDITION AVEC INFOS ACTUELLES
                    Intent intent = new Intent(SearchNoteActivity.this, EditNoteActivity.class);
                    String fileName = file.getName();
                    intent.putExtra(EXTRA_MESSAGE, fileName);
                    startActivity(intent);
                }
            });
            if((tr.getId()%2) != 0){
                tr.setBackgroundColor(Color.argb(100, 140, 230, 110));
            }
            else{
                tr.setBackgroundColor(Color.WHITE);
            }
            table.addView(tr);
            //J'ajoute un menu contextuel aux lignes pour proposer la suppression des fichiers:
            tr.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.menu_select_item, menu);
                    id_fichier = v.getId();
                }
            });
        }

        //Ajout d'une ligne permettant de créer un nouveau fichier text:
        trAdd = (TableRow) LayoutInflater.from(this).inflate(R.layout.note_add_table_layout, null);
        table.addView(trAdd);

        //AJOUT DES LISTENERS:
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Création d'un nouveau document si le nom est saisi:
                newFileName = (EditText) findViewById(R.id.newFileName);
                String fileName = newFileName.getText().toString();
                if (fileName.length() > 0) {
                    //Ajout de l'extension:
                    fileName += ".txt";
                    //Création du fichier:
                    File newFile = new File(Environment.getExternalStorageDirectory() + File.separator + "personnalNotes" + File.separator + fileName);
                    try {
                        if (!newFile.exists()) {
                            newFile.createNewFile();
                            Intent intent = new Intent(SearchNoteActivity.this, EditNoteActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, fileName);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SearchNoteActivity.this, "Ce fichier existe déjà!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SearchNoteActivity.this, "Saisissez le nom du fichier à créer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRestart(){
        super.onRestart();
        //Rechargement de l'activity:
        Intent i = new Intent(SearchNoteActivity.this, SearchNoteActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (id_fichier >= 0) {
            switch (item.getItemId()) {
                case R.id.itemDelete:
                    //SUPPRESSION DU FICHIER:
                    File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "personnalNotes");
                    File f[] = folder.listFiles();
                    for(int i=0; i<f.length; i++){
                        if(i == id_fichier){
                            f[i].delete();
                            id_fichier = -1;
                            onRestart();
                        }
                        else{ continue; }
                    }
                    return true;

                default:
                    return super.onContextItemSelected(item);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_note, menu);
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
