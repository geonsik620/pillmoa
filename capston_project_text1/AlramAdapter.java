package com.example.capston_project_text1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlramAdapter extends RecyclerView.Adapter<AlramAdapter.AlarmViewHolder> {

    private List<String> alarmList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public AlramAdapter(List<String> alarmList, OnItemClickListener listener) {
        this.alarmList = alarmList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alram_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        String alarmData = alarmList.get(position);
        holder.alarmText.setText(alarmData);

        holder.editButton.setOnClickListener(v -> listener.onEditClick(position));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView alarmText;
        ImageButton editButton;
        ImageButton deleteButton;
        Switch alarmSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmText = itemView.findViewById(R.id.alarmText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
        }
    }
}
