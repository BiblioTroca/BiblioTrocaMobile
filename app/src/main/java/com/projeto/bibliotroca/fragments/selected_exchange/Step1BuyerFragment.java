package com.projeto.bibliotroca.fragments.selected_exchange;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.fragments.modal_variants.UndoAgreementModalFragment;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.TransactionService;

public class Step1BuyerFragment extends Fragment {
    private TransactionService transactionService;

    private TransactionDTO transaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.step_1_buyer_fragment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String transactionId = args.getString("transaction_id", String.valueOf(-1)); // -1 é um valor padrão caso não encontre o ID
            this.transactionService = new TransactionService();
            this.transaction = transactionService.getTransactionById(transactionId);
            Log.d("TESTE", "onViewCreated: " + transactionId);
        }

        View radioItemRecuse = view.findViewById(R.id.radioCircleRecuse);
        radioItemRecuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRadioRecuseSelected = radioItemRecuse.isSelected();
                updateButtonState(view, isRadioRecuseSelected);
            }
        });
        //boolean isRadioRecuseSelected = radioItemRecuse.isSelected();
        //updateButtonState(view, isRadioRecuseSelected);

        return view;
    }

    private void updateButtonState(View view, boolean isRadioRecuseSelected) {
        Button btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);

        if (isRadioRecuseSelected) {
            btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UndoAgreementModalFragment modal = new UndoAgreementModalFragment();
                    modal.show(getChildFragmentManager(), "undoAgreementModal");
                }
            });
        } else {
            btnUpdateStatus.setOnClickListener(null);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
