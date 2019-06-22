package com.example.undefined.firestoreex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NOTE = "note";

    private EditText titleEditText;
    private EditText noteEditText, tagsEditText;
    private Button saveButton, loadButton, updateButton, deleteTitle, deleteNote, addNote, loadsNote, paginationNote;
    private TextView titleText, noteText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();   //Creating firestore
    private CollectionReference collectionReference = db.collection("NoteBook");
    private DocumentReference noteRefernce = db.collection("NoteBook").document("myFirstNote"); //firestore database location
    //    private DocumentReference noteRefernce=db.document("Notebook/myFirstNote");
//    private ListenerRegistration noteListener;  //for listener remove
    private DocumentSnapshot lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEditText = findViewById(R.id.titleEditTextId);
        noteEditText = findViewById(R.id.noteEditTextId);
        saveButton = findViewById(R.id.saveButtonId);
        loadButton = findViewById(R.id.loadButtonId);
        updateButton = findViewById(R.id.updateButtonId);
        titleText = findViewById(R.id.titleTextViewId);
        noteText = findViewById(R.id.noteTextViewId);
        deleteTitle = findViewById(R.id.deleteTitleButtonId);
        deleteNote = findViewById(R.id.deleteNoteButtonId);
        addNote = findViewById(R.id.addButtonId);
        loadsNote = findViewById(R.id.loadsButtonId);
        paginationNote = findViewById(R.id.paginationNoteButtonId);
        tagsEditText = findViewById(R.id.tagsEditTextId);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                titleEditText.setText("");
                noteEditText.setText("");
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNote();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });
        deleteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTitle();
            }
        });
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        loadsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadsNote();
            }
        });
        paginationNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginationNote();
            }
        });

        //      executeBatchedWrite();
        //      executetransaction();
        //      updateArray();
    }

    @Override
    protected void onStart() {
        super.onStart();
/*        // noteListener = noteRefernce.addSnapshotListener(new EventListener<DocumentSnapshot>() {  // when something changed instant listener
        noteRefernce.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "Error While Loading", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
*//*                    String title = documentSnapshot.getString(KEY_TITLE);
                    String note = documentSnapshot.getString(KEY_NOTE);*//*
                    //Map<String,Object> notes=documentSnapshot.getData();
                    Note noteM = documentSnapshot.toObject(Note.class);
                    String title = noteM.getTitle();
                    String note = noteM.getNote();
                    titleText.setText("Title : " + title);
                    noteText.setText("Note : " + note);
                } else {
                    titleText.setText("");
                    noteText.setText("");
                }
            }

        });*/
/*        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String dataTitle = "";
                String dataNote = "";
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Note note = snapshot.toObject(Note.class);
                    note.setId(snapshot.getId());
                    String titleText = note.getTitle();
                    String noteText = note.getNote();
                    String id = note.getId();

                    dataTitle += " " + " ID : " + id + " " + titleText + "\n";
                    dataNote += " " + noteText + " \n";
                }
                titleText.setText(dataTitle);
                noteText.setText(dataNote);
            }
        });*/

        //// For Document Changes
/*        collectionReference.orderBy("title").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String id = documentSnapshot.getId();
                    int oldIndex = dc.getOldIndex();
                    int newIndex = dc.getNewIndex();

                    switch (dc.getType()) {
                        case ADDED: {
                            noteText.append("\nAdded : " + id + " \nOld Index : " + dc.getOldIndex() + "\nNew Index : " + dc.getNewIndex() + "\n");
                            break;
                        }
                        case MODIFIED: {
                            noteText.append("\nModified : " + id + " \nOld Index : " + dc.getOldIndex() + "\nNew Index : " + dc.getNewIndex() + "\n");
                            break;
                        }
                        case REMOVED: {
                            noteText.append("\nRemoved : " + id + " \nOld Index : " + dc.getOldIndex() + "\nNew Index : " + dc.getNewIndex() + "\n");
                            break;
                        }
                    }
                }
            }
        });*/


    }

/*
    @Override
    protected void onStop() {
        super.onStop();
        noteListener.remove();
    }
*/

    public void saveNote() {
        String title = titleEditText.getText().toString();
        String note = noteEditText.getText().toString();
        String tagInput = tagsEditText.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*");
        List<String> tags = Arrays.asList(tagArray);
/*        Map<String, Object> noteM = new HashMap<>(); // Storing data by Container
        noteM.put(KEY_TITLE, title);
        noteM.put(KEY_NOTE, note);*/

        Note noteM = new Note(title, note,tags); // store data on firestore as object
        // db.collection("NoteBook").document("myFirstNote").set(noteMap)
        noteRefernce.set(noteM)  //on click data store
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }


    public void loadNote() {

        noteRefernce.get()  //data retrive from firestore
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
/*                            String title = documentSnapshot.getString(KEY_TITLE);
                            String note = documentSnapshot.getString(KEY_NOTE);*/
                            //Map<String,Object> notes=documentSnapshot.getData();

                            Note noteM = documentSnapshot.toObject(Note.class); //get Data from firestore as object
                            String title = noteM.getTitle();
                            String note = noteM.getNote();

                            titleText.setText("Title : " + title);
                            noteText.setText("Note : " + note);
                        } else {
                            Toast.makeText(MainActivity.this, "Document doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }

    public void addNote() {
        String title = titleEditText.getText().toString();
        String note = noteEditText.getText().toString();
        String tagInput = tagsEditText.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*");
        List<String> tags = Arrays.asList(tagArray);

/*        Map<String, Object> noteM = new HashMap<>(); // Storing data by Container
        noteM.put(KEY_TITLE, title);
        noteM.put(KEY_NOTE, note);*/

        Note noteM = new Note(title, note, tags); // store data on firestore as object
        collectionReference.add(noteM);  //it will add object as a note in this collection, we can also use listener here
      //  collectionReference.document("CsDArwVQRqIiNdm5z6vp").collection("sub collection").add(noteM); //how to add sub collection
    }

    public void loadsNote() {

        //for or,not operation there has no scope in firestore but we can do this in this method
/*
        Task task1 = collectionReference.whereLessThan("title", "johny")
                .orderBy("title")
                .get();
        Task task2 = collectionReference.whereGreaterThan("title", "johny")
                .orderBy("title")
                .get();
        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2);  //merge two query
        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                String dataTitle = "";
                String dataNote = "";
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                        Note note = snapshots.toObject(Note.class);
                        note.setId(snapshots.getId());

                        String title = note.getTitle();
                        String noteText = note.getNote();
                        String id = note.getId();

                        dataTitle += " " + " ID : " + id + " " + title + "\n";
                        dataNote += " " + noteText + "\n";

                    }
                }
                titleText.setText(dataTitle);
                noteText.setText(dataNote);
            }
        });
*/

        //pa

/*        collectionReference.whereGreaterThanOrEqualTo("title", "johny") // for simple quesry
                // .whereEqualTo("note","test")
                //  .limit()
                .orderBy("title")
                .orderBy("note")
                .get()
                //    collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String dataTitle = "";
                        String dataNote = "";
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            Note note = snapshots.toObject(Note.class);
                            note.setId(snapshots.getId());

                            String title = note.getTitle();
                            String noteText = note.getNote();
                            String id = note.getId();

                            dataTitle += " " + " ID : " + id + " " + title + "\n";
                            dataNote += " " + noteText + "\n";

                        }
                        titleText.setText(dataTitle);
                        noteText.setText(dataNote);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("failure", e.toString());
                    }
                });*/

        //Load note method for loading array data
        //collectionReference.document("CsDArwVQRqIiNdm5z6vp").collection("sub collection").get()  // retrive data from subcollection
        collectionReference.whereArrayContains("tags","t").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    String data="";
                                    for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots){
                                        Note note=queryDocumentSnapshot.toObject(Note.class);
                                        note.setId(queryDocumentSnapshot.getId());
                                        String documentId=note.getId();
                                        data+=" ID: "+documentId;
                                        for(String tag : note.getTags()){
                                            data+="\n-" +tag;
                                        }
                                        data+="\n\n";
                                    }
                                    noteText.setText(data);
                                }
                            });

    }

    public void updateNote() {
        String note = noteEditText.getText().toString();
        Map<String, Object> noteMap = new HashMap<>();
        noteMap.put(KEY_NOTE, note);
        // noteRefernce.set(noteMap,SetOptions.merge()); //merge will create new instance if not exist one
        ////noteRefernce.update(noteMap); // update will not create new document instance
        noteRefernce.update(KEY_NOTE, note); // can put value direct
    }

    public void deleteTitle() {
        Map<String, Object> noteMap = new HashMap<>();
        noteMap.put(KEY_TITLE, FieldValue.delete());
        noteRefernce.update(noteMap);
        // noteRefernce.update(KEY_TITLE,FieldValue.delete());
    }

    public void deleteNote() {
        noteRefernce.delete();
    }

    public void paginationNote() {
        Query query;
        if (lastResult == null) {
            query = collectionReference.orderBy("title")
                    .limit(3);
        } else {
            query = collectionReference.orderBy("title")
                    .startAfter(lastResult)
                    .limit(3);
        }
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String dataTitle = "";
                String dataNote = "";
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    Note note = snapshots.toObject(Note.class);
                    note.setId(snapshots.getId());

                    String title = note.getTitle();
                    String noteText = note.getNote();
                    String id = note.getId();

                    dataTitle += " " + " ID : " + id + "\n Title :" + title + "\n";
                    dataNote += " Note : " + noteText + "\n";

                }
                if (queryDocumentSnapshots.size() > 0) {
                    dataTitle += "_____________________________\n\n";
                    dataNote += "_____________________________\n\n";
                    lastResult = queryDocumentSnapshots.getDocuments()
                            .get(queryDocumentSnapshots.size() - 1);
                }
                titleText.append(dataTitle);
                noteText.append(dataNote);
            }
        });

    }

    public void executeBatchedWrite() {
        WriteBatch batch = db.batch();
        DocumentReference doc1 = collectionReference.document("New Document");
      //  batch.set(doc1, new Note("new title", "new test"));

        DocumentReference doc2 = collectionReference.document("Not Existing Document");
        //  batch.update(doc2,"title","update");

        DocumentReference doc3 = collectionReference.document("4rSAWoLqqJovCq6grdM8");
        batch.delete(doc3);

        batch.commit().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                titleText.setText(e.toString());
            }
        });


    }

    public void executetransaction() {
        db.runTransaction(new Transaction.Function<Void>() {
            @android.support.annotation.Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference exampleNoteRef = collectionReference.document("New Document");
                DocumentSnapshot exampleNoteSnapshot = transaction.get(exampleNoteRef);
                String newNote = exampleNoteSnapshot.getString("note") + " update";
                transaction.update(exampleNoteRef, "note", newNote);
                return null;
            }
        });
    }

    private void updateArray(){
        collectionReference.document("CsDArwVQRqIiNdm5z6vp")
                .update("tags",FieldValue.arrayUnion("new tag"));
                //.update("tags",FieldValue.arrayRemove("t"));
    }
}
