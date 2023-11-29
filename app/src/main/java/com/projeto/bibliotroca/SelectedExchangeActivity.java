package com.projeto.bibliotroca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.TransactionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class SelectedExchangeActivity extends AppCompatActivity {

    TextView txtBookName;
    TextView txtStatus;
    TextView txtAverageRating;
    TextView txtAvaliationsNumber;
    TextView txtCreateAt;
    TextView txtBuyerName;
    TextView txtDateCreateSelected;
    TextView txtCategory;
    TextView txtAuthor;
    TextView txtLanguage;
    TextView txtYear;
    TextView txtPublishingCompany;
    TextView txtState;
    TextView txtDescription;
    Button btnWhatsapp;
    TransactionDTO transactionToShow;
    ImageView btnArrowBack;

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

        initializeUIComponents();

        try {
            showTransactionDetails();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        btnArrowBack = findViewById(R.id.btnArrowBack);
        btnArrowBack.setOnClickListener(event -> finish());

        controllerTransactionStep(UserType.SELLER, 1);

        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnWhatsapp.setOnClickListener(event -> {
            String whatsappURL = "https://api.whatsapp.com/send?phone=55";

            Intent openWhatsapp = new Intent(Intent.ACTION_VIEW);
            openWhatsapp.setData(Uri.parse(whatsappURL));

            startActivity(openWhatsapp);
        });
    }

    private void showTransactionDetails() throws ParseException {
        Intent receivedIntentFromItem = getIntent();
        if (receivedIntentFromItem != null) {
            String id = receivedIntentFromItem.getStringExtra("transactionId");
            TransactionService transactionService = new TransactionService();
            transactionToShow = transactionService.getTransactionById(id);
            setTransactionIn(transactionToShow);
            Log.d("SelectedExchangeActivity", "Método funcionando");
            Log.d("TESTE", "Tran: " + transactionToShow);
        }
    }

    public void initializeUIComponents(){
        txtBookName = findViewById(R.id.txtBookNameSelected);
        Log.d("Components", "Funcionando");
    }


    private void setTransactionIn(TransactionDTO transaction) throws ParseException {


        if (transaction == null) {
            Log.d("SelectedExchangeActivity", "Transaction is null");
            return;
        } else {

            // Primeiro Card

            // Calculo de dias
         /*   String transactionCreateDate = transaction.getCreatedAt(); // Data da transação
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate transactionDate = LocalDate.parse(transactionCreateDate, formatter);
            LocalDate currentDate = LocalDate.now(); // Data atual
            long daysBetween = ChronoUnit.DAYS.between(transactionDate, currentDate); // Conversão para dias

            String createAt = "Solicitada há " + daysBetween + "dias"; // Arruma formato + calculo*/

            txtBookName.setText(transaction.getBookDetails().getName());
            txtStatus = findViewById(R.id.txtStatusSelected);
            txtStatus.setText(transaction.getStatus());
            txtCreateAt = findViewById(R.id.txtDateCreateSelected);
          //  txtCreateAt.setText(createAt);

            // Segundo Card
           /* SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            Date date = outputFormat.parse(transaction.getCreatedAt().toString());
            String formattedDate = outputFormat.format(date);*/

            String buyerName = transaction.getBuyer().getFirstName() + " " + transaction.getBuyer().getLastName();
            String averageRating = String.valueOf(transaction.getBuyer().getAverageRating());
            String avaliationsNumber = "(" + String.valueOf(transaction.getBuyer().getAvaliationsNumber()) + ")";
           // String dateCreateSelected = "Solicitada em " + formattedDate;

            txtBuyerName = findViewById(R.id.txtNameBuyerSelected);
            txtBuyerName.setText(buyerName);
            txtAverageRating = findViewById(R.id.txtAverageRatingSelected);
            txtAverageRating.setText(averageRating);
            txtAvaliationsNumber = findViewById(R.id.txtAvaliationsNumberSelected);
            txtAvaliationsNumber.setText(avaliationsNumber);
          //  txtDateCreateSelected = findViewById(R.id.txtDateCreateSelected);
          //  txtDateCreateSelected.setText(dateCreateSelected);

            // Terceiro Card
            txtCategory = findViewById(R.id.txtCategorySelected);
            txtCategory.setText(transaction.getBookDetails().getCategory());
            txtAuthor = findViewById(R.id.txtAuthorSelected);
            txtAuthor.setText(transaction.getBookDetails().getAuthor());
            txtLanguage = findViewById(R.id.txtLanguageSelected);
            txtLanguage.setText(transaction.getBookDetails().getLanguage());
            txtPublishingCompany = findViewById(R.id.txtPuslishingCompanySelected);
            txtPublishingCompany.setText(transaction.getBookDetails().getPublishingCompany());
            txtDescription = findViewById(R.id.txtDescriptionSelected);
            txtDescription.setText(transaction.getBookDetails().getDescription());
            txtYear = findViewById(R.id.txtYearSelected);
            txtYear.setText(transaction.getBookDetails().getYear());
            txtState = findViewById(R.id.txtStateSelected);
            txtState.setText(transaction.getBookDetails().getState());
        }
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
                }

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
