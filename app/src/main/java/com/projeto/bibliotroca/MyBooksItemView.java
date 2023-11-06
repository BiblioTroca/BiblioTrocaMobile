package com.projeto.bibliotroca;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyBooksItemView extends RecyclerView.ViewHolder {

    public View btnOptions;
    public MyBooksItemView(@NonNull View itemView) {
        super(itemView);
        btnOptions = itemView.findViewById(R.id.btn_options3);

    }


}
