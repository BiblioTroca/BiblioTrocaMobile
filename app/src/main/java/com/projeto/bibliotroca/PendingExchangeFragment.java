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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

        TextView txtTradeAmountItems = view.findViewById(R.id.txtTradeAmountItems);

        TransactionService transactionService = new TransactionService();
        CompletableFuture<List<TransactionDTO>> futureTransactions = transactionService.getListTransaction();
        futureTransactions.thenAccept(transactions -> {
            List<TransactionDTO> pendingTransactions = transactions.stream()
                    .filter(transaction -> transaction.getStatus().equalsIgnoreCase("pendente"))
                    .collect(Collectors.toList());

            this.transactions.addAll(pendingTransactions);

            getActivity().runOnUiThread(() -> {
                String amountItems = "I " + this.transactions.size() + " itens";
                txtTradeAmountItems.setText(amountItems);
                adapter.notifyDataSetChanged(); // atualiza a lista
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }
    }

