package com.example.johnwick.forcestest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnwick.forcestest.Models.Question;
import com.example.johnwick.forcestest.Models.Subject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import static android.graphics.Color.parseColor;

public class QuesionShowActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ArrayList<Question>  questionList = new ArrayList<>();
    int questionNo = 0 ;

    String subjectName ;
    String forceName ;

    TextView totalText, statementText, option1Text , option2Text, option3Text, option4Text ;
    Button nextBtn, previousBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesion_show);

        totalText = findViewById(R.id.question_text_total);
        statementText = findViewById(R.id.question_questionStatement);
        option1Text = findViewById(R.id.question_text_option1);
        option2Text = findViewById(R.id.question_text_option2);
        option3Text = findViewById(R.id.question_text_option3);
        option4Text = findViewById(R.id.question_text_option4);
        nextBtn = findViewById(R.id.question_btn_next);
        previousBtn = findViewById(R.id.question_btn_previous);

        subjectName = getIntent().getStringExtra("subjectName");
        forceName = getIntent().getStringExtra("forceName");
        firestore = FirebaseFirestore.getInstance();

        loadFirstQuestion();






        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionNo<(questionList.size()-1)){
                    printQuestion(++questionNo);

                }
                else{
                    Toast.makeText(QuesionShowActivity.this, "Questions End", Toast.LENGTH_LONG).show();
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionNo>0){
                    printQuestion(--questionNo);

                }
                else{
                    Toast.makeText(QuesionShowActivity.this, "No more back", Toast.LENGTH_LONG).show();
                }
            }
        });









    }

    void printQuestion(int index){
        Question question = questionList.get(index);
        totalText.setText((index+1) + "/" + questionList.size());
        statementText.setText(question.getStatement());
        option1Text.setText(question.getOption1());
        option2Text.setText(question.getOption2());
        option3Text.setText(question.getOption3());
        option4Text.setText(question.getOption4());
    }

    void loadFirstQuestion(){
        CollectionReference temp = firestore.collection(forceName).document(subjectName).collection("questions");

        temp.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                questionList.clear();



                for(QueryDocumentSnapshot document : queryDocumentSnapshots) {

                    String statement = "", option1 = "", option2 = "", option3 = "", option4 = "", correct = "";

                    String s = document.getId();
                    if (document.contains("statement")) {
                        statement = document.get("statement").toString();
                    }


                    if (document.contains("o1")) {
                        option1 = document.get("o1").toString();
                    }

                    if (document.contains("o2")) {
                        option2 = document.get("o2").toString();
                    }

                    if (document.contains("o3")) {
                        option3 = document.get("o3").toString();
                    }

                    if (document.contains("o4")) {
                        option4 = document.get("o4").toString();
                    }

                    if (document.contains("c")) {
                        correct = document.get("c").toString();
                    }

                    if(!(statement.equals(""))) {
                        questionList.add(new Question(statement, option1, option2, option3, option4, correct));
                    }
                }



                    Collections.shuffle(questionList);
                    printQuestion(0) ;
            }
        });
    }
}
