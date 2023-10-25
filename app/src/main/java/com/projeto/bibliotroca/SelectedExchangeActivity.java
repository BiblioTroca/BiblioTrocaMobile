package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class SelectedExchangeActivity extends AppCompatActivity {
    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.selected_exchange_layout);
    }

    public void handleCheckedRadioItem(View view) {
        ConstraintLayout radioItemAccept = findViewById(R.id.selectableFrameAccept);
        ConstraintLayout radioItemRecuse = findViewById(R.id.selectableFrameRecuse);
        View radioCircleAccept = findViewById(R.id.radioCircleAccept);
        View radioCircleRecuse = findViewById(R.id.radioCircleRecuse);

        boolean isCheckedItem = view.isSelected();

        if (view == radioItemAccept) {
            radioItemAccept.setSelected(!isCheckedItem);
            radioCircleAccept.setSelected(!isCheckedItem);

            radioItemRecuse.setSelected(isCheckedItem);
            radioCircleRecuse.setSelected(isCheckedItem);
        } else {
            radioItemAccept.setSelected(isCheckedItem);
            radioCircleAccept.setSelected(isCheckedItem);

            radioItemRecuse.setSelected(!isCheckedItem);
            radioCircleRecuse.setSelected(!isCheckedItem);
        }
    }
}
