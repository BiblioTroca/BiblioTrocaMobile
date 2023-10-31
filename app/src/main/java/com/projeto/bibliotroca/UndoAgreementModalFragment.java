package com.projeto.bibliotroca;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UndoAgreementModalFragment extends DialogFragment {

    public UndoAgreementModalFragment() {
        super(R.layout.undo_agreement_modal_fragment);
    }

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

        ImageView btnCloseModal = view.findViewById(R.id.btnCloseModal);
        btnCloseModal.setOnClickListener(event -> {
            dismiss();
        });
    }

}
