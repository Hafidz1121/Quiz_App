package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionText, questionText;
    Button opsiA, opsiB, opsiC, opsiD, submitBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
        }

        totalQuestionText = findViewById(R.id.total_question);
        questionText = findViewById(R.id.question);
        opsiA = findViewById(R.id.opsi_a);
        opsiB = findViewById(R.id.opsi_b);
        opsiC = findViewById(R.id.opsi_c);
        opsiD = findViewById(R.id.opsi_d);
        submitBtn = findViewById(R.id.submit_button);

        opsiA.setOnClickListener(this);
        opsiB.setOnClickListener(this);
        opsiC.setOnClickListener(this);
        opsiD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionText.setText("Total pertanyaan : " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        opsiA.setBackgroundColor(ContextCompat.getColor(this, R.color.opsiKosong));
        opsiB.setBackgroundColor(ContextCompat.getColor(this, R.color.opsiKosong));
        opsiC.setBackgroundColor(ContextCompat.getColor(this, R.color.opsiKosong));
        opsiD.setBackgroundColor(ContextCompat.getColor(this, R.color.opsiKosong));

        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.submit_button) {
            if (selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex])) {
                score++;
            }

            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.opsiDipilih));
        }
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }


        questionText.setText(QuestionAnswer.question[currentQuestionIndex]);
        opsiA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        opsiB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        opsiC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        opsiD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        String passStatus = "";

        if (score > totalQuestion * 0.60) {
            passStatus = "Sukses";
        } else {
            passStatus = "Gagal";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Nilai Anda Adalah " + score + " Dari " + totalQuestion)
                .setPositiveButton("Ulangi Lagi", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}