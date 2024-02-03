package org.osuji.justaplanequiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import org.osuji.justaplanequiz.Models.QuestionModel;
import org.osuji.justaplanequiz.R;
import org.osuji.justaplanequiz.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;
    Button btnNext;
    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName.equals("SET-1")) {
            setQuestionsForSetOne();
        } else if (setName.equals("SET-2")) {
            setQuestionsForSetTwo();
        }
        // Add more sets here as needed

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        playAnimation(binding.question,0,list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                }

                timer.start();
                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);

                enableOption(true);
                position++;

                if (position == list.size()) {
                    Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count = 0;
                playAnimation(binding.question, 0, list.get(position).getQuestion());
            }
        });
    }

    private void resetTimer() {

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivity.this, SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if (value == 0 && count < 4) {
                            String option = "";

                            if (count == 0) {
                                option = list.get(position).getOptionA();
                            } else if (count == 1) {
                                option = list.get(position).getOptionB();
                            } else if (count == 2) {
                                option = list.get(position).getOptionC();
                            } else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }


                            playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if (value == 0) {

                            try {

                                ((TextView) view).setText(data);
                                binding.totalQuestion.setText(position+1 +"/"+list.size());

                            } catch (Exception e) {

                                ((Button) view).setText(data);

                            }
                            view.setTag(data);
                            playAnimation(view, 1, data);

                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setEnabled(enable);

            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selectedOption) {
        if (timer != null) {
            timer.cancel();
        }
        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            score++;
            selectedOption.setBackgroundResource(R.drawable.correct_answer);
        } else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answer);

            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.correct_answer);
        }
    }

    private void setQuestionsForSetOne() {
        list.add(new QuestionModel("What does the acronym 'ATC' stand for in aviation jargon?",
                "A. Air Traffic Control", "B. Airplane Taxiing Crew", "C. Aviation Training Center", "D. Airborne Tactical Command", "A. Air Traffic Control"));

        list.add(new QuestionModel("During pre-flight checks, what does the term 'preflight inspection' refer to?",
                "A. Inspecting the flight instruments", "B. Checking the aircraft before each flight", "C. Reviewing the weather forecast", "D. Analyzing the fuel consumption", "B. Checking the aircraft before each flight"));

        list.add(new QuestionModel("What is the primary purpose of the VOR system in aviation?",
                "A. To measure altitude", "B. To aid in navigation", "C. To control engine thrust", "D. To communicate with ground personnel", "B. To aid in navigation"));

        list.add(new QuestionModel("What does the 'transponder' do in an aircraft?",
                "A. Transmits aircraft location", "B. Controls engine power", "C. Activates emergency beacons", "D. Filters cabin air", "A. Transmits aircraft location"));

        list.add(new QuestionModel("In the event of an engine failure during flight, what is the standard protocol?",
                "A. Attempt to restart the engine", "B. Deploy the parachute", "C. Initiate a controlled glide", "D. Contact air traffic control", "C. Initiate a controlled glide"));
    }

    private void setQuestionsForSetTwo() {
        list.add(new QuestionModel("What is the purpose of the 'rudder' on an aircraft?",
                "A. To control pitch", "B. To control roll", "C. To control yaw", "D. To control speed", "C"));

        list.add(new QuestionModel("What does the term 'stall speed' refer to in aviation?",
                "A. The speed at which the aircraft is grounded", "B. The maximum speed an aircraft can achieve", "C. The minimum speed required to maintain flight", "D. The speed at which the aircraft takes off", "C"));

        list.add(new QuestionModel("Which weather phenomenon is associated with severe turbulence?",
                "A. Cumulus clouds", "B. Cirrus clouds", "C. Thunderstorms", "D. Stratus clouds", "C"));

        list.add(new QuestionModel("What does the acronym 'IFR' stand for in aviation?",
                "A. In-Flight Reporting", "B. International Flight Rules", "C. Instrument Flight Rules", "D. Intercontinental Flight Regulations", "C"));

        list.add(new QuestionModel("What is the primary function of an aircraft's 'ailerons'?",
                "A. To control pitch", "B. To control roll", "C. To control yaw", "D. To control speed", "B"));
    }
}