package com.projeto.bibliotroca.fragments.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.RegisterBookActivity;
import com.projeto.bibliotroca.adapters.MyBooksAdapter;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.services.BookService;

import java.util.ArrayList;
import java.util.List;

public class MyBooksFragment extends Fragment {

    MyBooksAdapter adapter;
    private List<BookCompleteDTO> livros = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.books_fragment,container,false);

        ImageButton btnAddBook = rootView.findViewById(R.id.btn_addbook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBookIntent = new Intent(getContext(), RegisterBookActivity.class);
                startActivity(addBookIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycleList = view.findViewById(R.id.recicleviewMLivros);
        recycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        BookService bookService = new BookService();
        bookService.getListBookFull(livros);

        TextView txtAmountBooks =view.findViewById(R.id.txtBookAmount);

        String amountBooks= "";
        if (livros.size()>0){
            amountBooks = "I " + livros.size() + " itens";
        }
        txtAmountBooks.setText(amountBooks);



        adapter = new MyBooksAdapter(getContext(),getParentFragmentManager(),livros);
        recycleList.setAdapter(adapter);

    }
}
