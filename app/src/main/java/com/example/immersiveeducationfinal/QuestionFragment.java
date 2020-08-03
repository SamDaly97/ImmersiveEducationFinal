package com.example.immersiveeducationfinal;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immersiveeducationfinal.Common.Common;
import com.example.immersiveeducationfinal.Interface.IQuestion;
import com.example.immersiveeducationfinal.Model.CurrentQuestion;
import com.example.immersiveeducationfinal.Model.Question;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;


public class QuestionFragment extends Fragment implements IQuestion {

    TextView txt_question_text;
    CheckBox ckbA,ckbB,ckbC,ckbD;
    FrameLayout layout_image;
    ProgressBar progressBar;

    Question question;
    int questionIndex=-1;


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView =  inflater.inflate(R.layout.fragment_question, container, false);

        //Get Question
        questionIndex = getArguments().getInt("index",-1);
        question = Common.questionList.get(questionIndex);

        if(question != null) {
            layout_image = (FrameLayout) itemView.findViewById(R.id.layout_image);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);
            if(question.isImageQuestion())
            {
                ImageView img_question = (ImageView)itemView.findViewById(R.id.img_question);
                Picasso.get().load(question.getQuestionImage()).into(img_question, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                layout_image.setVisibility(View.GONE);

            //View
            txt_question_text = (TextView) itemView.findViewById(R.id.txt_question_text);
            txt_question_text.setText(question.getQuestionText());

            ckbA = (CheckBox) itemView.findViewById(R.id.ckbA);
            ckbA.setText(question.getAnswerA());
            ckbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        Common.selected_values.add(ckbA.getText().toString());
                    else
                        Common.selected_values.remove(ckbA.getText().toString());
                }
            });

            ckbB = (CheckBox) itemView.findViewById(R.id.ckbB);
            ckbB.setText(question.getAnswerB());
            ckbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        Common.selected_values.add(ckbB.getText().toString());
                    else
                        Common.selected_values.remove(ckbB.getText().toString());
                }
            });

            ckbC = (CheckBox) itemView.findViewById(R.id.ckbC);
            ckbC.setText(question.getAnswerC());
            ckbC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        Common.selected_values.add(ckbC.getText().toString());
                    else
                        Common.selected_values.remove(ckbC.getText().toString());
                }
            });

            ckbD = (CheckBox) itemView.findViewById(R.id.ckbD);
            ckbD.setText(question.getAnswerD());
            ckbD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        Common.selected_values.add(ckbD.getText().toString());
                    else
                        Common.selected_values.remove(ckbD.getText().toString());
                }
            });

        }

        return itemView;
    }

    @Override
    public CurrentQuestion getSelectedAnswer() {
        //If Question is no answer
        if(Common.answerSheetList.get(questionIndex).getType() == Common.ANSWER_TYPE.NO_ANSWER) {

            //This function will return state of answer
            // Right , wrong or unanswered
            CurrentQuestion currentQuestion = new CurrentQuestion(questionIndex, Common.ANSWER_TYPE.NO_ANSWER); // Default no answer
            StringBuilder result = new StringBuilder();
            if (Common.selected_values.size() == 1) {
                Object[] arrayAnswer = Common.selected_values.toArray();
                for (int i = 0; i < arrayAnswer.length; i++)
                    if (i < arrayAnswer.length - 1)
                        result.append(new StringBuilder(((String) arrayAnswer[i]).substring(0, 1)).append(","));// Take first letter of Answer: Ex : arr[0] = A. NewYork , we will take letter 'A'
                    else
                        result.append(new StringBuilder((String) arrayAnswer[i]).substring(0, 1)); // Too
            }

            if (question != null) {
                if (!TextUtils.isEmpty(result)) {
                    if (result.toString().equals(question.getCorrectAnswer()))
                        currentQuestion.setType(Common.ANSWER_TYPE.RIGHT_ANSWER);
                    else
                        currentQuestion.setType(Common.ANSWER_TYPE.WRONG_ANSWER);
                } else
                    currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
            } else {
                Toast.makeText(getContext(), "Cannot get question", Toast.LENGTH_SHORT).show();
                currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
            }
            Common.selected_values.clear();
            return currentQuestion;
        }
        else
            return Common.answerSheetList.get(questionIndex);
    }


    @Override
    public void showCorrectAnswer() {
        String[] correctAnswer = question.getCorrectAnswer().split(",");
        for(String answer:correctAnswer)
            if (answer.equals("A")) {
                ckbA.setTypeface(null, Typeface.BOLD);
                ckbA.setTextColor(Color.RED);
            } else if (answer.equals("B")) {
                ckbB.setTypeface(null, Typeface.BOLD);
                ckbB.setTextColor(Color.RED);
            } else if (answer.equals("C")) {
                ckbC.setTypeface(null, Typeface.BOLD);
                ckbC.setTextColor(Color.RED);
            } else if (answer.equals("D")) {
                ckbD.setTypeface(null, Typeface.BOLD);
                ckbD.setTextColor(Color.RED);
            }

    }

    @Override
    public void disableAnswer() {

        ckbA.setEnabled(false);
        ckbB.setEnabled(false);
        ckbC.setEnabled(false);
        ckbD.setEnabled(false);

    }

    @Override
    public void resetQuestion() {
        for(CurrentQuestion item:Common.answerSheetList)
            item.setType(Common.ANSWER_TYPE.NO_ANSWER);

        //Enable Checkbox
        ckbA.setEnabled(true);
        ckbB.setEnabled(true);
        ckbC.setEnabled(true);
        ckbD.setEnabled(true);

        //Remove all selected
        ckbA.setChecked(false);
        ckbB.setChecked(false);
        ckbC.setChecked(false);
        ckbD.setChecked(false);

        //Remove all bold on text
        ckbA.setTypeface(null,Typeface.NORMAL);
        ckbA.setTextColor(Color.BLACK);
        ckbB.setTypeface(null,Typeface.NORMAL);
        ckbB.setTextColor(Color.BLACK);
        ckbC.setTypeface(null,Typeface.NORMAL);
        ckbC.setTextColor(Color.BLACK);
        ckbD.setTypeface(null,Typeface.NORMAL);
        ckbD.setTextColor(Color.BLACK);

    }
}
