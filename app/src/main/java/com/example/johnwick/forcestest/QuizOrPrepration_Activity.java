package com.example.johnwick.forcestest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QuizOrPrepration_Activity extends AppCompatActivity {
    CardView quizCard, preprationCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_or_prepration_);

        quizCard = findViewById(R.id.quiz_card_quiz);
        preprationCard = findViewById(R.id.quiz_card_prepration);

        preprationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizOrPrepration_Activity.this, SubjectActivity.class);
                intent.putExtra("forceName", getIntent().getStringExtra("buttonName"));
                startActivity(intent);
            }
        });
    }
}
