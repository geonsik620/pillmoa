package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicationHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistory;
    private Button buttonSelectAll;
    private Button buttonDelete;
    private SearchHistoryAdapter adapter;
    private List<String> searchHistoryList;
    private ImageView backButton;
    private ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_history);

        // 뒤로가기 버튼 설정
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 홈 버튼 설정
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MedicationHistoryActivity.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // ID 연결
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        buttonSelectAll = findViewById(R.id.buttonSelectAll);
        buttonDelete = findViewById(R.id.buttonDelete);

        // 검색 이력 더미 데이터
        searchHistoryList = new ArrayList<>();
        searchHistoryList.add("의약품: 타이레놀 500mg");
        searchHistoryList.add("의약품: 아스피린 100mg");

        // 어댑터 설정
        adapter = new SearchHistoryAdapter(this, searchHistoryList);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        // 전체 선택 버튼 클릭 리스너
        buttonSelectAll.setOnClickListener(v -> adapter.selectAllItems());

        // 삭제 버튼 클릭 리스너
        buttonDelete.setOnClickListener(v -> adapter.deleteSelectedItems());
    }
}
