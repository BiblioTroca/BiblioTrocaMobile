package com.projeto.bibliotroca.fragments.modal_variants;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.projeto.bibliotroca.R;

public class DeleteAccountModalFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return inflater.inflate(R.layout.delete_account_modal_fragment, container, false);
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
