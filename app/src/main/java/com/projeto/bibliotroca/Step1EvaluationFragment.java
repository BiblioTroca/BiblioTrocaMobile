package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Step1EvaluationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_1_evaluation_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.btnHistoricNextArrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmEvaluationModalFragment modal = new ConfirmEvaluationModalFragment();
                modal.show(getChildFragmentManager(), "confirmEvaluationModal");
            }
        });

        return view;
    }
}




