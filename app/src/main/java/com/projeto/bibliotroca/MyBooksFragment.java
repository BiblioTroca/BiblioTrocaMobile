package com.projeto.bibliotroca;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyBooksFragment extends Fragment {

    MyBooksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.books_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycleList = view.findViewById(R.id.recicleviewMLivros);
        recycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyBooksAdapter(getContext());
        recycleList.setAdapter(adapter);

        ConstraintLayout book_card = view.findViewById(R.id.Card_Book_item);

        ImageButton btnAddBook = view.findViewById(R.id.btn_addbook);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegLivro = new Intent(getContext(),RegisterBookActivity.class);
                startActivity(RegLivro);
            }
        });
    }
}
