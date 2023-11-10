package com.projeto.bibliotroca;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.bibliotroca.fragments.selected_review.Step1EvaluationFragment;
import com.projeto.bibliotroca.fragments.selected_review.Step2EvaluationFragment;

public class TradeDetailsActivity extends AppCompatActivity {

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.trade_details_layout);

        controllerEvaluationStep(1);
    }

    public void controllerEvaluationStep(int step) {
        if (step == 1) {
            showCurrentEvaluationStep(new Step1EvaluationFragment());
        } else {
            showCurrentEvaluationStep(new Step2EvaluationFragment());
        }
    }

    public void showCurrentEvaluationStep(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentEvaluation = fragmentManager.beginTransaction();
        fragmentEvaluation.replace(R.id.cardEvaluation, fragment);
        fragmentEvaluation.commit();
    }
}
