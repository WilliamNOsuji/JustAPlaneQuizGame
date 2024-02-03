package org.osuji.justaplanequiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import org.osuji.justaplanequiz.Models.QuestionModel;
import org.osuji.justaplanequiz.R;
import org.osuji.justaplanequiz.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private  int score = 0;
    CountDownTimer timer;
    Button btnNext;
    ActivityQuestionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        String setName = getIntent().getStringExtra("set");

        switch(setName) {
            case "SET-1":
                setOne();
                break;
            case "SET-2":
                setTwo();
                break;
            case "SET-3":
                setThree();
                break;
            case "SET-4":
                setFour();
                break;
            case "SET-5":
                setFive();
                break;
            case "SET-6":
                setSix();
                break;
            case "SET-7":
                setSeven();
                break;
            case "SET-8":
                setHeight();
                break;
            case "SET-9":
                setNine();
                break;
            case "SET-10":
                setTen();
                break;
        }

        for(int i = 0; i < 4; i++){
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);

                enableOption(true);
                position ++;

                if(position == list.size()){
                    Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                }
            }
        });
    }

    private void enableOption(boolean enable) {
        for(int i=0; i<4;i++){
            binding.optionContainer.getChildAt(i).setEnabled(enable);
        }

        if(enable){
            binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
        }
    }

    private void checkAnswer(Button selectedOption) {
        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
            score++;
            selectedOption.setBackgroundResource(R.drawable.correct_answer);
        }
        else{
            selectedOption.setBackgroundResource(R.drawable.wrong_answer);

            Button correctOption = (Button) binding.optionContainer.findViewById(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.correct_answer);
        }
    }

    private void setTen() {
    }

    private void setNine() {
    }

    private void setHeight() {
    }

    private void setSeven() {
    }

    private void setSix() {
    }

    private void setFive() {
    }

    private void setFour() {
    }

    private void setThree() {
    }

    private void setTwo() {

        list.add(new QuestionModel("How many planes are their ?"
                , "25", "30", "26", "40", "25"));

        list.add(new QuestionModel("What is Air ?"
                , "solid", "liquid", "gas", "floride", "gas"));

        list.add(new QuestionModel("How many planes are their ?"
                , "25", "30", "26", "40", "25"));

        list.add(new QuestionModel("What is Air ?"
                , "solid", "liquid", "gas", "floride", "gas"));

        list.add(new QuestionModel("How many planes are their ?"
                , "25", "30", "26", "40", "25"));

        list.add(new QuestionModel("What is Air ?"
                , "solid", "liquid", "gas", "floride", "gas"));
    }

    private void setOne() {
        list.add(new QuestionModel("How many planes are their ?"
                , "25", "30", "26", "40", "25"));

        list.add(new QuestionModel("What is Air ?"
                , "solid", "liquid", "gas", "floride", "gas"));

        list.add(new QuestionModel("How many planes are their ?"
                , "25", "30", "26", "40", "25"));

        list.add(new QuestionModel("What is Air ?"
                , "solid", "liquid", "gas", "floride", "gas"));
    }
}