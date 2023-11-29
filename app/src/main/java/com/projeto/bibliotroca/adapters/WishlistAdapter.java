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
import com.projeto.bibliotroca.models.WishlistDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistItemView> {
    Context context;

    private FragmentManager fragmentManager;

    private final LayoutInflater inflater;

    List<WishlistDTO> wishlist;

    public WishlistAdapter(Context context, FragmentManager fragmentManager, List<WishlistDTO> wishlist) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.wishlist_item, parent, false);
        return new WishlistItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistItemView itemView, int position) {
        itemView.btnWishOptions.setOnClickListener(view -> showPopupMenu(view, position));

        WishlistDTO wish = wishlist.get(position);

        itemView.txtWishTitleCard.setText(wish.getName());
        itemView.txtWishAuthorCard.setText(wish.getAuthor());
        itemView.txtWishCategoryCard.setText(wish.getCategory());
        itemView.txtWishDateCard.setText(wish.getCreatedAt(Instant.now()
                .atZone(ZoneId.of("GMT-3")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_edit) {
                WishlistDTO clickedWish = wishlist.get(position);

                Intent editWish = new Intent(context, EditWishlistActivity.class);
                editWish.putExtra("wish_Id", clickedWish.getId());
                context.startActivity(editWish);
                return true;
            } else if (itemId == R.id.menu_delete) {
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