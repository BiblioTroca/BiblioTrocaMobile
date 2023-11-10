package com.projeto.bibliotroca.fragments.wishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.WishlistActivity;
import com.projeto.bibliotroca.adapters.WishlistAdapter;

public class WishlistFragment extends Fragment {
    WishlistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView =  inflater.inflate(R.layout.wishlist_fragment, container, false);

        ImageButton btnAddWish = rootView.findViewById(R.id.btnAddWish);
        btnAddWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addWishIntent = new Intent(getContext(), WishlistActivity.class);
                startActivity(addWishIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycleList = view.findViewById(R.id.recycleListWishlist);
        recycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WishlistAdapter(getContext(), getParentFragmentManager());
        recycleList.setAdapter(adapter);

    }   
}
