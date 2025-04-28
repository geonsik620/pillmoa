package com.example.capston_project_text1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private List<String> searchHistoryList;
    private boolean[] selectedItems;  // 각 항목의 선택 상태 저장
    private Context context;  // Context 변수 추가

    // 생성자에서 Context를 받음
    public SearchHistoryAdapter(Context context, List<String> searchHistoryList) {
        this.context = context;
        this.searchHistoryList = searchHistoryList;
        this.selectedItems = new boolean[searchHistoryList.size()];  // 선택 상태 초기화
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view, this);  // ViewHolder에 어댑터를 전달
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = searchHistoryList.get(position);
        holder.textView.setText(item);
        holder.itemView.setBackgroundColor(selectedItems[position] ? 0xFFE0E0E0 : 0xFFFFFFFF);
    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }

    // 전체 선택 기능
    public void selectAllItems() {
        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = true;
        }
        notifyDataSetChanged();
    }

    // 선택된 항목 삭제 기능
    public void deleteSelectedItems() {
        for (int i = selectedItems.length - 1; i >= 0; i--) {
            if (selectedItems[i]) {
                searchHistoryList.remove(i);
            }
        }
        // 선택 상태 초기화
        selectedItems = new boolean[searchHistoryList.size()];
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        SearchHistoryAdapter adapter;  //

        public ViewHolder(View itemView, SearchHistoryAdapter adapter) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            this.adapter = adapter;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        adapter.selectedItems[position] = !adapter.selectedItems[position];
                        adapter.notifyItemChanged(position);
                    }
                }
            });
        }
    }
}
