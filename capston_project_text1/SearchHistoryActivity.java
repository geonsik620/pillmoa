package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        // 의약품 검색 이력 버튼
        Button buttonMedicationHistory = findViewById(R.id.buttonMedicationHistory);
        buttonMedicationHistory.setOnClickListener(v -> {
            // 의약품 검색 이력 화면으로 이동
            Intent intent = new Intent(SearchHistoryActivity.this, MedicationHistoryActivity.class);
            startActivity(intent);
        });

        // 처방전 검색 이력 버튼
        Button buttonPrescriptionHistory = findViewById(R.id.buttonPrescriptionHistory);
        buttonPrescriptionHistory.setOnClickListener(v -> {
            // 처방전 검색 이력 화면으로 이동
            Intent intent = new Intent(SearchHistoryActivity.this, PrescriptionHistoryActivity.class);
            startActivity(intent);
        });

        // 뒤로가기 버튼
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        // 홈 버튼
        ImageView homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            // Main 화면으로 이동
            Intent intent = new Intent(SearchHistoryActivity.this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        // Back 버튼 클릭 시 이전 화면으로 이동
        super.onBackPressed();
    }
}
