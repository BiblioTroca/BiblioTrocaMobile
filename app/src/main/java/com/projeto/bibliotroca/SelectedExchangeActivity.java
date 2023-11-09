package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.bibliotroca.fragments.selected_exchange.Step1BuyerFragment;
import com.projeto.bibliotroca.fragments.selected_exchange.Step1SellerFragment;
import com.projeto.bibliotroca.fragments.selected_exchange.Step2BuyerFragment;
import com.projeto.bibliotroca.fragments.selected_exchange.Step2SellerFragment;

public class SelectedExchangeActivity extends AppCompatActivity {
    public enum UserType {
        SELLER, BUYER
    }

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildStyles();
        setContentView(R.layout.selected_exchange_layout);

        ImageView btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        controllerTransactionStep(UserType.BUYER, 1);
    }

    public void handleCheckedRadioItem(View view) {
        ConstraintLayout radioItemAccept = findViewById(R.id.selectableFrameAccept);
        ConstraintLayout radioItemRecuse = findViewById(R.id.selectableFrameRecuse);
        View radioCircleAccept = findViewById(R.id.radioCircleAccept);
        View radioCircleRecuse = findViewById(R.id.radioCircleRecuse);

        view.setSelected(!view.isSelected());

        boolean isExistsRadioAccept = radioItemAccept != null && radioCircleAccept != null;
        boolean isExistsRadioRecuse = radioItemRecuse != null && radioCircleRecuse != null;

        if (isExistsRadioAccept) {
            if (view == radioItemAccept) {
                radioItemAccept.setSelected(true);
                radioCircleAccept.setSelected(true);

                if(isExistsRadioRecuse) {
                    radioItemRecuse.setSelected(false);
                    radioCircleRecuse.setSelected(false);
                };

                return;
            }

            radioItemAccept.setSelected(false);
            radioCircleAccept.setSelected(false);

            if(isExistsRadioRecuse) {
                radioItemRecuse.setSelected(true);
                radioCircleRecuse.setSelected(true);
            }
        }

        if (isExistsRadioRecuse) {
            radioItemRecuse.setSelected(true);
            radioCircleRecuse.setSelected(true);
        }
    }

    public void controllerTransactionStep(UserType userType, int step) {
        switch(userType) {
            case SELLER:
                if (step == 1) {
                    showCurrentTransactionStep(new Step1SellerFragment());
                } else {
                    showCurrentTransactionStep(new Step2SellerFragment());
                }
                break;

            case BUYER:
                if (step == 1) {
                    showCurrentTransactionStep(new Step1BuyerFragment());
                } else {
                    showCurrentTransactionStep(new Step2BuyerFragment());
                }
                break;
        }
    }

    public void showCurrentTransactionStep(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cardUpdateStatus, fragment);
        fragmentTransaction.commit();
    }

}
