package com.example.johnwick.forcestest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.johnwick.forcestest.Adapters.AdapterSubject;
import com.example.johnwick.forcestest.Models.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ArrayList<Subject> subjectList = new ArrayList<>() ;
    RecyclerView recyclerView;
    String forceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        recyclerView = findViewById(R.id.subject_recycler);

        forceName = getIntent().getStringExtra("forceName");


        firestore = FirebaseFirestore.getInstance();


        //Toast.makeText(this, buttonName, Toast.LENGTH_LONG).show();


        firestore.collection(forceName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getId();
                                String url = document.get("url").toString();
                                subjectList.add(new Subject(name, url));


                            }
                            printsubjects() ;
                        } else {

                        }
                    }
                });
    }

    void printsubjects(){
        recyclerView.setAdapter(new AdapterSubject(subjectList, this, forceName));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }
}
