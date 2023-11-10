package com.projeto.bibliotroca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksItemView> {

    private Context context;
    private LayoutInflater inflater;

    public MyBooksAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyBooksItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.mybook_item, parent, false);
        return new MyBooksItemView(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull MyBooksItemView holder, int position) {
        holder.btnOptions.setOnClickListener(this::showPopupMenu);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_edit) {


                return true;

            }
            else if (itemId == R.id.menu_delete) {

                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }
}

