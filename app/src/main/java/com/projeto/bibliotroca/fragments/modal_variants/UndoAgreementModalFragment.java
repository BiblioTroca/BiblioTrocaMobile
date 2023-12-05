package com.projeto.bibliotroca.fragments.modal_variants;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.projeto.bibliotroca.NavigationActivity;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.TransactionService;

public class UndoAgreementModalFragment extends DialogFragment {


    //private TransactionService transactionService;
   // private TransactionDTO transaction;
    Button btnConfirmUndoAgreement;

    public UndoAgreementModalFragment() {
        super(R.layout.undo_agreement_modal_fragment);
    }

  //  public UndoAgreementModalFragment(TransactionDTO transaction, TransactionService transactionService) {
   //     this.transaction = transaction;
  //      this.transactionService = transactionService;
  //  }


    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null && getDialog().getWindow() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int dialogWidth = displayMetrics.widthPixels - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()); // Subtrai 24dp de cada lado
            int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnConfirmUndoAgreement = view.findViewById(R.id.btnUpdateStatus);
        btnConfirmUndoAgreement.setOnClickListener(event -> {
            Log.d("TESTE", "Botao de confirmar");
         //   updateStatus("Cancelada");
            dismiss();
           // Log.d("Step2BuyerFragment", "onClick: botao aceitar " + transaction.getStatus());

        });

        ImageView btnCloseModal = view.findViewById(R.id.btnCloseModal);
        btnCloseModal.setOnClickListener(event -> {
            dismiss();
    });
}

   /* private void updateStatus(String newStatus) {
        TransactionService transactionService = new TransactionService();
        Log.d("Step2BuyerFragment", "updateStatus: " + this.transaction.getId() + " " + newStatus);
        Log.d("UpdateMetodo", "Situação: " + transactionService);

        this.transaction.setStatus(newStatus);

        transactionService.updateTransactionById(this.transaction.getId(), newStatus);
        navigateToExchangeScreen();

    }*/




    private void navigateToExchangeScreen() {
        Intent exchangeIntent = new Intent(requireContext(), NavigationActivity.class);
        startActivity(exchangeIntent);

    }
}
