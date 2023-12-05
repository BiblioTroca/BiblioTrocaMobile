package com.projeto.bibliotroca.fragments.selected_exchange;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projeto.bibliotroca.NavigationActivity;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.SelectedExchangeActivity;
import com.projeto.bibliotroca.fragments.modal_variants.UndoAgreementModalFragment;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.TransactionService;

public class Step1SellerFragment extends Fragment {

    private TransactionService transactionService;
    private TransactionDTO transaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.step_1_seller_fragment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String transactionId = args.getString("transaction_id", String.valueOf(-1)); // -1 é um valor padrão caso não encontre o ID
            this.transactionService = new TransactionService();
            this.transaction = transactionService.getTransactionById(transactionId);
        }

        Button btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);
        View radioItemRecuse = view.findViewById(R.id.radioCircleRecuse);
        View radioItemAccept = view.findViewById(R.id.radioCircleAccept);

        radioItemRecuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRadioRecuseSelected = radioItemRecuse.isSelected();
                updateButtonStateForSellerFragment(btnUpdateStatus, isRadioRecuseSelected, false);
            }
        });
        radioItemAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRadioAcceptSelected = radioItemAccept.isSelected();
                updateButtonStateForSellerFragment(btnUpdateStatus,false, isRadioAcceptSelected);
            }
        });

        return view;
    }


    private void updateButtonStateForSellerFragment(Button btnUpdateStatus, boolean isRadioRecuseSelected, boolean isRadioAcceptSelected) {
        if (isRadioRecuseSelected) {
            btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Step1SellerFragment", "onClick: botao recuse");

                    UndoAgreementModalFragment modal = new UndoAgreementModalFragment( );
                   // UndoAgreementModalFragment modal = new UndoAgreementModalFragment(transaction, transactionService);

                    modal.show(getChildFragmentManager(), "undoAgreementModal");
                }
            });

        } else if (isRadioAcceptSelected) {
            btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Step1SellerFragment", "onClick: TESTANDO");
                    transaction.setStatus("PENDENTE");
                    updateStatus("PENDENTE");
                    Log.d("Step2BuyerFragment", "onClick: botao aceitar " + transaction.getStatus());
                    if (getActivity() instanceof SelectedExchangeActivity) {
                        ((SelectedExchangeActivity) getActivity()).controllerTransactionStep(SelectedExchangeActivity.UserType.SELLER, 2);
                        ((SelectedExchangeActivity) getActivity()).controllerTransactionStep(SelectedExchangeActivity.UserType.BUYER, 2);
                    }
                }
            });
        } else {
            btnUpdateStatus.setOnClickListener(null);
        }
    }

    private void updateStatus(String newStatus) {
        TransactionService transactionService = new TransactionService();
        Log.d("Step2BuyerFragment", "updateStatus: " + this.transaction.getId() + " " + newStatus);
        Log.d("UpdateMetodo", "Situação: " + transactionService);

        this.transaction.setStatus(newStatus);

        transactionService.updateTransactionById(this.transaction.getId(), newStatus);
        navigateToExchangeScreen();
    }
    private void navigateToExchangeScreen() {
        Intent exchangeIntent = new Intent(requireContext(), NavigationActivity.class);
        startActivity(exchangeIntent);

    }
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
