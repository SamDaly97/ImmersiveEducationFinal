package com.example.immersiveeducationfinal.Interface;

import com.example.immersiveeducationfinal.Model.CurrentQuestion;

public interface IQuestion {
    CurrentQuestion getSelectedAnswer();
    void showCorrectAnswer();
    void disableAnswer();
    void resetQuestion();
}
