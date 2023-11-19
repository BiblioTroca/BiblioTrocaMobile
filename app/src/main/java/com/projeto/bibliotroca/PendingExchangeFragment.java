package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class PendingExchangeFragment extends Fragment {

    PendingExchangeAdapter adapter;

    List<TransactionDTO> transactions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.trades_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycleList = view.findViewById(R.id.recicleVexchange);
        recycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PendingExchangeAdapter(getContext(), transactions);
        recycleList.setAdapter(adapter);


        TransactionService transactionService = new TransactionService();
        transactionService.getListTransaction(transactions);

        TextView txtTradeAmountItems = view.findViewById(R.id.txtTradeAmountItems);

        String amountItems = "";
        if (transactions.size() > 0) {
            amountItems = "I " + transactions.size() + " itens";
        }
        txtTradeAmountItems.setText(amountItems);
        }
    }

