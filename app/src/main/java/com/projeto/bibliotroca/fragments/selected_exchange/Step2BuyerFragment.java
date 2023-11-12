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

public class Step2BuyerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.step_2_buyer_fragment, container, false);

        Button btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus);

        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmEvaluationModalFragment modal = new ConfirmEvaluationModalFragment();
                modal.show(getChildFragmentManager(), "confirmEvaluationModal");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
