package com.example.undefined.firebaseui_firestoreex;

import android.hardware.camera2.TotalCaptureResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class New_Note extends AppCompatActivity {

    private EditText editTextTitle, editTextDes;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add New Note");

        editTextTitle = findViewById(R.id.editTextTitleId);
        editTextDes = findViewById(R.id.editTextDesId);
        numberPicker = findViewById(R.id.numberPickerId);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String des = editTextDes.getText().toString();
        int priority = numberPicker.getValue();

        if(title.trim().isEmpty() || des.trim().isEmpty()){
            Toast.makeText(this,"Insert a title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference noteRef = FirebaseFirestore.getInstance().collection("Notebook");
        noteRef.add(new Note(title,des,priority));
        Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show();
        finish();
    }
}
