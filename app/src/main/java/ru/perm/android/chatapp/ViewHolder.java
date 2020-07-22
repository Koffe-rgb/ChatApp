package ru.perm.android.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView message_item;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        message_item = itemView.findViewById(R.id.message_item);
    }
}
