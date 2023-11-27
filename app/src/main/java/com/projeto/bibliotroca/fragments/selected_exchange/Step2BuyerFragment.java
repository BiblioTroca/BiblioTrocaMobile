package com.projeto.bibliotroca.fragments.selected_exchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.fragments.modal_variants.ConfirmEvaluationModalFragment;
import com.projeto.bibliotroca.models.TransactionDTO;

public class Step2BuyerFragment extends Fragment {

    com.projeto.bibliotroca.services.TransactionService TransactionService;
    private TransactionDTO transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.step_2_buyer_fragment, container, false);

        Button btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);
        View radioItemAccept = view.findViewById(R.id.radioCircleAccept);

        radioItemAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRadioAcceptSelected = radioItemAccept.isSelected();
                updateButtonState(view, isRadioAcceptSelected);
            }
        });
        return view;
    }

    private void updateButtonState(View view, boolean isRadioAcceptSelected) {
        Button btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);

        if (isRadioAcceptSelected) {
            btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmEvaluationModalFragment modal = new ConfirmEvaluationModalFragment();
                    modal.show(getChildFragmentManager(), "confirmEvaluationModal");
                    updateStatus("Concluída");
                  // Log.d("Step2BuyerFragment", "onClick: botao aceitar " + transaction.getStatus());
                }
            });
        } else {
            btnUpdateStatus.setOnClickListener(null);
        }
    }

    private void updateStatus(String newStatus) {
        if (transaction != null) {
            TransactionService.updateTransactionById(transaction.getId(), newStatus);
            transaction.setStatus(newStatus);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
