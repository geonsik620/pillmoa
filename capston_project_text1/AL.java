package com.example.capston_project_text1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AL extends AppCompatActivity {
    Button btnMH, btnMN;
    ImageView btnB, btnH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.alram);

        // Apply Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find Buttons
        btnMN = findViewById(R.id.btnMN); // 복약 알림 관리 버튼
        btnMH = findViewById(R.id.btnMH); // 복약 이력 버튼
        btnB = findViewById(R.id.btnB);   // 뒤로가기 버튼
        btnH = findViewById(R.id.btnH);   // 홈 버튼

        // 복약 알림 관리 버튼 클릭 시
        btnMN.setOnClickListener(v -> {
            Intent intent = new Intent(AL.this, MN.class);
            startActivity(intent);
        });

        // 복약 이력 버튼 클릭 시
        btnMH.setOnClickListener(v -> {
            Intent intent = new Intent(AL.this, MH.class);
            startActivity(intent);
        });

        // 뒤로가기 버튼 클릭 시
        btnB.setOnClickListener(v -> onBackPressed()); // 뒤로가기 기능

        // 홈 버튼 클릭 시
        btnH.setOnClickListener(v -> {
            Intent intent = new Intent(AL.this, Main.class); // 메인 액티비티로 이동
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // 기존 스택을 정리하고 메인 화면으로 이동
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });
    }
}

