package tech.phixlab.nota;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import tech.phixlab.nota.data.DataSource;
import tech.phixlab.nota.model.Note;

public class NewNote extends AppCompatActivity {
    Note notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fabSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    private void saveNote(){

        // Creating new note
        notes = new Note();

        // note title from edit
        // note body from edit
        notes.setDateTime(formatDate(new Date()) + " " + formatTime(new Date()));


        // Initializing db with access using current context
        DataSource ds = new DataSource(NewNote.this);

        // open db for writing
        ds.openDb();


        if (ds.insertNote(notes)) {
            // get last inserted id
            int newId = ds.getLastNoteId();

            // set the note id
            notes.setId(newId);
            Toast.makeText(this, "Note Saved!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Error did not save!!",Toast.LENGTH_LONG).show();
        }
        ds.closeDb();

    }



    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("E, LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}