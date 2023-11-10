package com.projeto.bibliotroca.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.fragments.modal_variants.DeleteBookModalFragment;
import com.projeto.bibliotroca.EditWishlistActivity;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.fragments.wishlist.WishlistItemView;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistItemView> {
    private final Context context;

    private FragmentManager fragmentManager;

    private final LayoutInflater inflater;

    public WishlistAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;

    }

    @NonNull
    @Override
    public WishlistItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.wishlist_item, parent, false);
        return new WishlistItemView(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull WishlistItemView holder, int position) {
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

                Intent editIntent = new Intent(context, EditWishlistActivity.class);
                context.startActivity(editIntent);
                return true;

            }
            else if (itemId == R.id.menu_delete) {
                DeleteBookModalFragment modal = new DeleteBookModalFragment();
                modal.show(fragmentManager, "deleteAccountModal");

                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }
}